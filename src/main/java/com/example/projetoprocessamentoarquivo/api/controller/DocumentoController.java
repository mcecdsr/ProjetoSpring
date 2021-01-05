package com.example.projetoprocessamentoarquivo.api.controller;


import com.example.projetoprocessamentoarquivo.api.model.AppCapturaResumoModel;
import com.example.projetoprocessamentoarquivo.api.model.DocumentoModel;
import com.example.projetoprocessamentoarquivo.api.model.OperadorDocumentoModel;
import com.example.projetoprocessamentoarquivo.api.model.OperadorModel;
import com.example.projetoprocessamentoarquivo.api.model.input.DocumentoInput;
import com.example.projetoprocessamentoarquivo.domain.filter.DocumentoFilter;
import com.example.projetoprocessamentoarquivo.domain.model.AppCaptura;
import com.example.projetoprocessamentoarquivo.domain.model.Documento;
import com.example.projetoprocessamentoarquivo.domain.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/documentos")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;


    @GetMapping
    public List<DocumentoModel> listarTodos(){
        List<Documento> documentos = documentoService.listar();
        List<DocumentoModel> documentoModels = new ArrayList<>();

        for (Documento documento : documentos) {
            DocumentoModel documentoModel = new DocumentoModel();
            mapear(documento, documentoModel);
            documentoModels.add(documentoModel);
        }
        return documentoModels;
    }

    @GetMapping (path = "/configuracoesenvio")
    public List<DocumentoModel> pesquisar(DocumentoFilter documentoFilter){
        List<Documento> documentos = documentoService.pesquisar(documentoFilter);
        List<DocumentoModel> documentoModels = new ArrayList<>();

        for (Documento documento : documentos) {
            DocumentoModel documentoModel = new DocumentoModel();
            OperadorDocumentoModel operadorDocumentoModel = new OperadorDocumentoModel();
            operadorDocumentoModel.setTipo(documento.getOperador().getTipo());

            List<AppCapturaResumoModel> appCapturasResumoModel = new ArrayList<>();
            for (AppCaptura appCaptura : documento.getOperador().getAppCapturas()) {
                AppCapturaResumoModel appCapturaResumoModel = new AppCapturaResumoModel();
                appCapturaResumoModel.setFormato(appCaptura.getFormato());
                appCapturaResumoModel.setId(appCaptura.getId());
                appCapturaResumoModel.setNome(appCaptura.getNome());
                appCapturasResumoModel.add(appCapturaResumoModel);
            }

            operadorDocumentoModel.setAppCapturas(appCapturasResumoModel);
            documentoModel.setOperador(operadorDocumentoModel);
            mapear(documento, documentoModel);
            documentoModels.add(documentoModel);
        }
        return documentoModels;
    }


    @GetMapping("/{id}")
    public DocumentoModel listarPorId(@PathVariable Long id){
        Documento documento = documentoService.listaPorId(id);
        DocumentoModel documentoModel = new DocumentoModel();
        mapear(documento, documentoModel);
        return documentoModel;

    }

    @PostMapping
    public ResponseEntity<DocumentoModel> salvarDocumento (@RequestBody DocumentoInput documentoInput){
        Documento documento = documentoService.adicionar(documentoInput);
        DocumentoModel documentoModel = new DocumentoModel();
        mapear(documento, documentoModel);
        return ResponseEntity.status(201).body(documentoModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoModel> atualizarDocumento
            (@PathVariable Long id , @RequestBody DocumentoInput documentoInput){
        Documento documento = documentoService.atualizar(id, documentoInput);
        DocumentoModel documentoModel = new DocumentoModel();
        mapear(documento, documentoModel);
        return ResponseEntity.status(200).body(documentoModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarDocumento (@PathVariable Long id){
        documentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private void mapear(Documento documento, DocumentoModel documentoModel) {
        documentoModel.setId(documento.getId());
        documentoModel.setNome(documento.getNome());
        documentoModel.setFormato(documento.getFormato());
        documentoModel.setData(documento.getData());
    }


}
