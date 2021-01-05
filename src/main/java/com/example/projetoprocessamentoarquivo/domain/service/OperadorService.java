package com.example.projetoprocessamentoarquivo.domain.service;


import com.example.projetoprocessamentoarquivo.api.model.input.OperadorInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import com.example.projetoprocessamentoarquivo.domain.repository.OperadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperadorService {

    @Autowired
    private OperadorRepository operadorRepository;
    @Autowired
    private UsuarioService usuarioService;

    public List<Operador> listar() {
        return operadorRepository.findAll();
    }

    public Operador listaPorId(Long id) {
        return operadorRepository.findById(id).orElseThrow(()
                -> new NotFoundException("Operador nao encontrato!"));

    }

    public Operador adicionar(OperadorInput operadorInput) {
        Operador operador = new Operador();
        operador.setTipo(operadorInput.getTipo());
        Usuario usuario = usuarioService.listaPorId(operadorInput.getUsuario().getId());
        operador.setUsuario(usuario);
        return operadorRepository.save(operador);
    }

    public void deletar(Long id) {
        if (operadorRepository.existsById(id)) {
            operadorRepository.deleteById(id);
        } else {
            throw new NotFoundException("Id nao encontrado para deletar!");
        }
    }

    public Operador atualizar(Long id, OperadorInput operadorInput){
        Operador operador = listaPorId(id);
        operador.setTipo(operadorInput.getTipo());
        Usuario usuario = usuarioService.listaPorId(operadorInput.getUsuario().getId());
        operador.setUsuario(usuario);
        return operadorRepository.save(operador);

    }

    public void setUsuarioService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public void setOperadorRepository(OperadorRepository operadorRepository) {
        this.operadorRepository = operadorRepository;
    }
}
