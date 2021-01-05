package com.example.projetoprocessamentoarquivo.domain.service;


import com.example.projetoprocessamentoarquivo.api.model.input.AppCapturaInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.model.AppCaptura;
import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import com.example.projetoprocessamentoarquivo.domain.repository.AppCapturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppCapturaService {

    @Autowired
    private AppCapturaRepository appCapturaRepository;
    @Autowired
    private OperadorService operadorService;

    public List<AppCaptura> listar() {
        return appCapturaRepository.findAll();
    }

    public AppCaptura listaPorId(Long id) {
        return appCapturaRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Aplicativo nao encontrato!"));

    }

    @Transactional
    public AppCaptura adicionar(AppCapturaInput appCapturaInput) {
        AppCaptura appCaptura = new AppCaptura();
        appCaptura.setNome(appCapturaInput.getNome());
        appCaptura.setFormato(appCapturaInput.getFormato());
        Operador operador = operadorService.listaPorId(appCapturaInput.getOperador().getId());
        appCaptura.setOperador(operador);
        return appCapturaRepository.save(appCaptura);
    }

    @Transactional
    public void deletar(Long id) {
        if (appCapturaRepository.existsById(id)) {
            appCapturaRepository.deleteById(id);
        } else {
            throw new NotFoundException("Id nao encontrado para deletar!");
        }
    }

    @Transactional
    public AppCaptura atualizar(Long id, AppCapturaInput appCapturaInput){
        AppCaptura appCaptura = listaPorId(id);
        appCaptura.setNome(appCapturaInput.getNome());
        appCaptura.setFormato(appCapturaInput.getFormato());
        Operador operador = operadorService.listaPorId(appCapturaInput.getOperador().getId());
        appCaptura.setOperador(operador);
        return appCapturaRepository.save(appCaptura);

    }


    public void setOperadorService(OperadorService operadorService) {
        this.operadorService = operadorService;
    }
}
