package com.example.projetoprocessamentoarquivo.api.controller;

import com.example.projetoprocessamentoarquivo.api.model.AppCapturaResumoModel;
import com.example.projetoprocessamentoarquivo.api.model.OperadorModel;
import com.example.projetoprocessamentoarquivo.api.model.UsuarioModel;
import com.example.projetoprocessamentoarquivo.api.model.input.OperadorInput;
import com.example.projetoprocessamentoarquivo.domain.model.AppCaptura;
import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import com.example.projetoprocessamentoarquivo.domain.service.OperadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/operadores")
public class OperadorController {

    @Autowired
    private OperadorService operadorService;


    @GetMapping
    public List<OperadorModel> listarTodos(){
        List<Operador> operadores = operadorService.listar();
        List<OperadorModel> operadorModels = new ArrayList<>();

        for (Operador operador : operadores) {
            OperadorModel operadorModel = new OperadorModel();
            mapear(operador, operadorModel);
            operadorModels.add(operadorModel);
        }
        return operadorModels;
    }

    @GetMapping("/{id}")
    public OperadorModel listarPorId(@PathVariable Long id){
        Operador operador = operadorService.listaPorId(id);
        OperadorModel operadorModel = new OperadorModel();
        mapear(operador, operadorModel);
        return operadorModel;

    }

    @PostMapping
    public ResponseEntity<OperadorModel> salvarOperador (@RequestBody OperadorInput operadorInput){
        Operador operador = operadorService.adicionar(operadorInput);
        OperadorModel operadorModel = new OperadorModel();
        mapear(operador, operadorModel);
        return ResponseEntity.status(201).body(operadorModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperadorModel> atualizarOperador
            (@PathVariable Long id , @RequestBody OperadorInput operadorInput){
        Operador operador = operadorService.atualizar(id, operadorInput);
        OperadorModel operadorModel = new OperadorModel();
        mapear(operador, operadorModel);
        return ResponseEntity.status(200).body(operadorModel);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deletarOperador (@PathVariable Long id){
        operadorService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    private void mapear(Operador operador, OperadorModel operadorModel) {
        UsuarioModel usuarioModel = new UsuarioModel();
        operadorModel.setId(operador.getId());
        operadorModel.setTipo(operador.getTipo());
        usuarioModel.setId(operador.getUsuario().getId());
        usuarioModel.setNomeUsuario(operador.getUsuario().getNomeUsuario());
        operadorModel.setUsuario(usuarioModel);

        List<AppCapturaResumoModel> appCapturasResumoModel = new ArrayList<>();
        for (AppCaptura appCaptura : operador.getAppCapturas()) {
            AppCapturaResumoModel appCapturaResumoModel = new AppCapturaResumoModel();
            appCapturaResumoModel.setFormato(appCaptura.getFormato());
            appCapturaResumoModel.setId(appCaptura.getId());
            appCapturaResumoModel.setNome(appCaptura.getNome());
            appCapturasResumoModel.add(appCapturaResumoModel);
        }

        operadorModel.setAppCapturas(appCapturasResumoModel);
    }
}
