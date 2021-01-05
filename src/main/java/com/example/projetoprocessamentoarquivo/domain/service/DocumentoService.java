package com.example.projetoprocessamentoarquivo.domain.service;

import com.example.projetoprocessamentoarquivo.api.model.input.DocumentoInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.filter.DocumentoFilter;
import com.example.projetoprocessamentoarquivo.domain.model.Documento;
import com.example.projetoprocessamentoarquivo.domain.repository.DocumentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private OperadorService operadorService;

    public List<Documento> listar() {
        return documentoRepository.findAll();
    }


    public List<Documento> pesquisar(DocumentoFilter documentoFilter) {

        return documentoRepository.findAll(usandoFiltro(documentoFilter));
    }

    public Documento listaPorId(Long id) {
        return documentoRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Documento nao encontrato!"));

    }

    @Transactional
    public Documento adicionar(DocumentoInput documentoInput) {
        Documento documento = new Documento();
        documento.setNome(documentoInput.getNome());
        documento.setData(documentoInput.getData());
        documento.setAppCaptura(documentoInput.getAppCaptura());
        documento.setFormato(documentoInput.getFormato());
        documento.setValorArquivo(documentoInput.getValorArquivo());
        documento.setOperador(operadorService.listaPorId(documentoInput.getOperador().getId()));
        return documentoRepository.save(documento);
    }

    @Transactional
    public void deletar(Long id) {
        if (documentoRepository.existsById(id)) {
            documentoRepository.deleteById(id);
        } else {
            throw new NotFoundException("Id nao encontrado para deletar!");
        }
    }

    @Transactional
    public Documento atualizar(Long id, DocumentoInput documentoInput){
        Documento documento = listaPorId(id);
        documento.setNome(documentoInput.getNome());
        documento.setData(documentoInput.getData());
        documento.setAppCaptura(documentoInput.getAppCaptura());
        documento.setFormato(documentoInput.getFormato());
        documento.setValorArquivo(documentoInput.getValorArquivo());
        documento.setOperador(operadorService.listaPorId(documentoInput.getOperador().getId()));
        return documentoRepository.save(documento);

    }

    public static Specification<Documento> usandoFiltro(DocumentoFilter filtro) {
        return (root, query, builder) -> {
            if(Documento.class.equals(query.getResultType())) {
                root.fetch("operador");
            }

            var predicates = new ArrayList<Predicate>();

            if (filtro.getAppCaptura() != null) {
                predicates.add(builder.equal(root.get("appCaptura"), filtro.getAppCaptura()));
            }

            if (filtro.getData() != null) {
                predicates.add(builder.equal(root.get("data"), filtro.getData()));
            }

            if (filtro.getFormato() != null) {
                predicates.add(builder.equal(root.get("formato"), filtro.getFormato()));
            }

            if (filtro.getOperadorId() != null) {
                predicates.add(builder.equal(root.get("operador"), filtro.getOperadorId()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public void setOperadorService(OperadorService operadorService) {
        this.operadorService = operadorService;
    }
}
