package com.example.projetoprocessamentoarquivo.api.controller;

import com.example.projetoprocessamentoarquivo.api.model.UsuarioModel;
import com.example.projetoprocessamentoarquivo.api.model.input.UsuarioInput;
import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import com.example.projetoprocessamentoarquivo.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioModel> listarTodos(){
        List<Usuario> usuarios = usuarioService.listar();
        List<UsuarioModel> usuarioModels = new ArrayList<>();

        for (Usuario usuario : usuarios) {
            UsuarioModel usuarioModel = new UsuarioModel();
            mapear(usuario, usuarioModel);
            usuarioModels.add(usuarioModel);
        }
        return usuarioModels;
    }


    @GetMapping("/{id}")
    public UsuarioModel listarPorId(@PathVariable Long id){
        Usuario usuario = usuarioService.listaPorId(id);
        UsuarioModel usuarioModel = new UsuarioModel();
        mapear(usuario, usuarioModel);
        return usuarioModel;

    }

    @PostMapping
    public ResponseEntity<UsuarioModel> salvarUsuario (@RequestBody UsuarioInput usuarioInput){
        Usuario usuario = usuarioService.adicionar(usuarioInput);
        UsuarioModel usuarioModel = new UsuarioModel();
        mapear(usuario, usuarioModel);
        return ResponseEntity.status(201).body(usuarioModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioModel> atualizarUsuario
            (@PathVariable Long id , @RequestBody UsuarioInput usuarioInput){
        Usuario usuario = usuarioService.atualizar(id, usuarioInput);
        UsuarioModel usuarioModel = new UsuarioModel();
        mapear(usuario, usuarioModel);
        return ResponseEntity.status(200).body(usuarioModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deletarUsuario (@PathVariable Long id){
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    private void mapear(Usuario usuario, UsuarioModel usuarioModel) {
        usuarioModel.setId(usuario.getId());
        usuarioModel.setNomeUsuario(usuario.getNomeUsuario());
    }



}
