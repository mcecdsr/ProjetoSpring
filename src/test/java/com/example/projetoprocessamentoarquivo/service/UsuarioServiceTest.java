package com.example.projetoprocessamentoarquivo.service;


import com.example.projetoprocessamentoarquivo.api.model.input.UsuarioInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import com.example.projetoprocessamentoarquivo.domain.repository.UsuarioRepository;
import com.example.projetoprocessamentoarquivo.domain.service.UsuarioService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private UsuarioRepository usuarioRepository;

    private Usuario usuario;
    private List<Usuario> usuarios;
    private UsuarioInput usuarioIn;
    private UsuarioInput usuarioAdd;


    @Before
    public void init() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNomeUsuario("Ceci");
        usuario.setSenha("1234");
        usuarios = new ArrayList<Usuario>();
        usuarios.add(new Usuario());
        usuarioIn = new UsuarioInput();
        usuarioIn.setNomeUsuario("Fran");
        usuarioIn.setSenha("4321");
        usuarioAdd = new UsuarioInput();
        usuarioAdd.setNomeUsuario("Ceci");
        usuarioAdd.setSenha("1234");


    }

    @Test
    public void buscarUsuarioPorCodigoOk () {
        // cenario
        given(usuarioRepository.findById(anyLong()))
                .willReturn(Optional.of(usuario));

        // execucao
        var result = usuarioService.listaPorId(1L);

        // validação
        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result.getId()));

        verify(usuarioRepository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void buscarUsuarioPorCodigoNaoOk() {

        // cenario
        given(usuarioRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = usuarioService.listaPorId(1L);

        // validaçao
        verify(usuarioRepository, times(1))
                .findById(anyLong());
    }

    @Test
    public void buscarUsuarioComSucesso () {
        // cenario
        given(usuarioRepository.findAll())
                .willReturn(usuarios);

        // execucao
        var result = usuarioService.listar();

        // validação
        assertNotNull(result);

        verify(usuarioRepository, times(1)).findAll();
    }


    @Test
    public void buscarUsuarioSemSucesso () {
        // cenario
        given(usuarioRepository.findAll())
                .willReturn(null);

        // execucao
        var result = usuarioService.listar();

        // validação
        assertNull(result);

        verify(usuarioRepository, times(1)).findAll();
    }


    @Test
    public void deletarUsuarioPorCodigoOk() {
        // cenario
        given(usuarioRepository.existsById(anyLong()))
                .willReturn(Boolean.TRUE);

        doNothing().when(usuarioRepository).deleteById(anyLong());

        // execucao
        usuarioService.deletar(1L);

        //validação
        verify(usuarioRepository, times(1))
                .existsById(anyLong());
        verify(usuarioRepository, times(1))
                .deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deletarUsuarioPorCodigoNaoOk() {
        // cenario
        given(usuarioRepository.existsById(anyLong()))
                .willReturn(Boolean.FALSE);

        doNothing().when(usuarioRepository).deleteById(anyLong());

        // execucao
        usuarioService.deletar(1L);

        //validação
        verify(usuarioRepository, times(1))
                .existsById(anyLong());
        verify(usuarioRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    public void atualizarUsuarioPorCodigoOk () {
        // cenario
        given(usuarioRepository.findById(anyLong()))
                .willReturn(Optional.of(usuario));

        // execucao
        var result = usuarioService.atualizar(1L,usuarioIn);

        // validação

        verify(usuarioRepository, times(1)).findById(anyLong());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test(expected = NotFoundException.class)
    public void atualizarUsuarioPorCodigoNaoOk () {
        // cenario
        given(usuarioRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = usuarioService.atualizar(1L,usuarioIn);

        // validação

        verify(usuarioRepository, times(1)).findById(anyLong());
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    public void incluirUsuarioOk () {


        // cenario
        given(usuarioRepository.save(usuario))
                .willReturn(usuario);

        // execucao
        var result = usuarioRepository.save(usuario);


        // validação
        assertEquals(usuario.getNomeUsuario(), usuarioAdd.getNomeUsuario());


        verify(usuarioRepository, times(1)).save(usuario);
    }


    @Test
    public void incluirUsuarioNaoOk () {

        // cenario
        given(usuarioRepository.save(usuario))
                .willReturn(null);

        // execucao
        var result = usuarioRepository.save(usuario);


        // validação
        assertNull(result);

        verify(usuarioRepository, times(1)).save(usuario);
    }





}
