package com.example.projetoprocessamentoarquivo.service;

import com.example.projetoprocessamentoarquivo.api.model.input.OperadorInput;
import com.example.projetoprocessamentoarquivo.api.model.input.UsuarioIdInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import com.example.projetoprocessamentoarquivo.domain.repository.OperadorRepository;
import com.example.projetoprocessamentoarquivo.domain.repository.UsuarioRepository;
import com.example.projetoprocessamentoarquivo.domain.service.OperadorService;
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
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
public class OperadorServiceTest {

    @InjectMocks
    private OperadorService operadorService;
    @InjectMocks
    private UsuarioService usuarioService;
    @Mock
    private OperadorRepository operadorRepository;
    @Mock
    private UsuarioRepository usuarioRepository;


    private Operador operador;
    private List<Operador> operadores;
    private OperadorInput operadorInput;
    private OperadorInput operadorAdd;
    private UsuarioIdInput usuarioIdInput;
    private Usuario usuario;


    @Before
    public void init() {
        usuarioIdInput = new UsuarioIdInput();
        usuarioIdInput.setId(1L);
        operador = new Operador();
        operador.setId(1L);
        operador.setTipo("ADMIN");
        operadores = new ArrayList<Operador>();
        operadores.add(new Operador());
        operadorInput = new OperadorInput();
        operadorInput.setTipo("OPERADORMOBILE");
        operadorInput.setUsuario(usuarioIdInput);
        operadorAdd = new OperadorInput();
        operadorAdd.setTipo("ADMIN");
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNomeUsuario("Ceci");
        usuario.setSenha("1234");
        usuario.setOperador(operador);


    }

    @Test
    public void buscarOperadorPorCodigoOk () {
        // cenario
        given(operadorRepository.findById(anyLong()))
                .willReturn(Optional.of(operador));

        // execucao
        var result = operadorService.listaPorId(1L);

        // validação
        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result.getId()));

        verify(operadorRepository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void buscarOperadorPorCodigoNaoOk() {

        // cenario
        given(operadorRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = operadorService.listaPorId(1L);

        // validaçao
        verify(operadorRepository, times(1))
                .findById(anyLong());
    }

    @Test
    public void buscarOperadorComSucesso () {
        // cenario
        given(operadorRepository.findAll())
                .willReturn(operadores);

        // execucao
        var result = operadorService.listar();

        // validação
        assertNotNull(result);

        verify(operadorRepository, times(1)).findAll();
    }


    @Test
    public void buscarOperadorSemSucesso () {
        // cenario
        given(operadorRepository.findAll())
                .willReturn(null);

        // execucao
        var result = operadorService.listar();

        // validação
        assertNull(result);

        verify(operadorRepository, times(1)).findAll();
    }


    @Test
    public void deletarOperadorPorCodigoOk() {
        // cenario
        given(operadorRepository.existsById(anyLong()))
                .willReturn(Boolean.TRUE);

        doNothing().when(operadorRepository).deleteById(anyLong());

        // execucao
        operadorService.deletar(1L);

        //validação
        verify(operadorRepository, times(1))
                .existsById(anyLong());
        verify(operadorRepository, times(1))
                .deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deletarOperadorPorCodigoNaoOk() {
        // cenario
        given(operadorRepository.existsById(anyLong()))
                .willReturn(Boolean.FALSE);

        doNothing().when(operadorRepository).deleteById(anyLong());

        // execucao
        operadorService.deletar(1L);

        //validação
        verify(operadorRepository, times(1))
                .existsById(anyLong());
        verify(operadorRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    public void atualizarOperadorPorCodigoOk () {
        // cenario
        given(operadorRepository.findById(anyLong()))
                .willReturn(Optional.of(operador));

        given(usuarioRepository.findById(anyLong()))
                .willReturn(Optional.of(usuario));

        // execucao
        operadorService.setUsuarioService(usuarioService);
        usuarioService.setUsuarioRepository(usuarioRepository);
        var result = operadorService.atualizar(1L,operadorInput);

        // validação

        verify(operadorRepository, times(1)).findById(anyLong());
        verify(operadorRepository, times(1)).save(operador);
    }

    @Test(expected = NotFoundException.class)
    public void atualizarOperadorPorCodigoNaoOk () {
        // cenario
        given(operadorRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = operadorService.atualizar(1L,operadorInput);

        // validação

        verify(operadorRepository, times(1)).findById(anyLong());
        verify(operadorRepository, times(1)).save(operador);
    }

    @Test
    public void incluirOperadorOk () {


        // cenario
        given(operadorRepository.save(operador))
                .willReturn(operador);

        // execucao
        var result = operadorRepository.save(operador);


        // validação
        assertEquals(operador.getTipo(), operadorAdd.getTipo());


        verify(operadorRepository, times(1)).save(operador);
    }


    @Test
    public void incluirOperadorNaoOk () {

        // cenario
        given(operadorRepository.save(operador))
                .willReturn(null);

        // execucao
        var result = operadorRepository.save(operador);


        // validação
        assertNull(result);

        verify(operadorRepository, times(1)).save(operador);
    }










}
