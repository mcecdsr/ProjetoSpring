package com.example.projetoprocessamentoarquivo.domain.service;



import com.example.projetoprocessamentoarquivo.api.model.input.UsuarioInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import com.example.projetoprocessamentoarquivo.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario listaPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Usuario nao encontrato!"));

    }

    public Usuario adicionar(UsuarioInput usuarioInput) {
        Usuario usuario = new Usuario();
        usuario.setNomeUsuario(usuarioInput.getNomeUsuario());
        usuario.setSenha(usuarioInput.getSenha());
        return usuarioRepository.save(usuario);
    }

    public void deletar(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
        } else {
            throw new NotFoundException("Id nao encontrado para deletar!");
        }
    }

    public Usuario atualizar(Long id, UsuarioInput usuarioInput){
        Usuario usuario = listaPorId(id);
        usuario.setNomeUsuario(usuarioInput.getNomeUsuario());
        usuario.setSenha(usuarioInput.getSenha());
        return usuarioRepository.save(usuario);

    }

    public void setUsuarioRepository(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }
}
