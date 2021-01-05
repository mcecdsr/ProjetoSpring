package com.example.projetoprocessamentoarquivo.api.controller;

import com.example.projetoprocessamentoarquivo.api.model.AppCapturaModel;
import com.example.projetoprocessamentoarquivo.api.model.OperadorResumoModel;
import com.example.projetoprocessamentoarquivo.api.model.input.AppCapturaInput;
import com.example.projetoprocessamentoarquivo.domain.model.AppCaptura;
import com.example.projetoprocessamentoarquivo.domain.service.AppCapturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/v1/aplicativodecaptura")
public class AppCapturaController {

    @Autowired
    private AppCapturaService appCapturaService;


    @GetMapping
    public List<AppCapturaModel> listarTodos(){
        List<AppCaptura> appCapturaes = appCapturaService.listar();
        List<AppCapturaModel> appCapturaModels = new ArrayList<>();

        for (AppCaptura appCaptura : appCapturaes) {
            AppCapturaModel appCapturaModel = new AppCapturaModel();
            mapear(appCaptura, appCapturaModel);
            appCapturaModels.add(appCapturaModel);
        }
        return appCapturaModels;
    }

    @GetMapping("/{id}")
    public AppCapturaModel listarPorId(@PathVariable Long id){
        AppCaptura appCaptura = appCapturaService.listaPorId(id);
        AppCapturaModel appCapturaModel = new AppCapturaModel();
        mapear(appCaptura, appCapturaModel);
        return appCapturaModel;

    }

    @PostMapping
    public ResponseEntity<AppCapturaModel> salvarAppCaptura (@RequestBody AppCapturaInput appCapturaInput){
        AppCaptura appCaptura = appCapturaService.adicionar(appCapturaInput);
        AppCapturaModel appCapturaModel = new AppCapturaModel();
        mapear(appCaptura, appCapturaModel);
        return ResponseEntity.status(201).body(appCapturaModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppCapturaModel> atualizarAppCaptura
            (@PathVariable Long id , @RequestBody AppCapturaInput appCapturaInput){
        AppCaptura appCaptura = appCapturaService.atualizar(id, appCapturaInput);
        AppCapturaModel appCapturaModel = new AppCapturaModel();
        mapear(appCaptura, appCapturaModel);
        return ResponseEntity.status(200).body(appCapturaModel);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deletarAppCaptura (@PathVariable Long id){
        appCapturaService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    private void mapear(AppCaptura appCaptura, AppCapturaModel appCapturaModel) {
        OperadorResumoModel operadorResumoModel = new OperadorResumoModel();
        appCapturaModel.setId(appCaptura.getId());
        appCapturaModel.setFormato(appCaptura.getFormato());
        appCapturaModel.setNome(appCaptura.getNome());
        operadorResumoModel.setId(appCaptura.getOperador().getId());
        operadorResumoModel.setTipo(appCaptura.getOperador().getTipo());
        appCapturaModel.setOperador(operadorResumoModel);
    }


}
