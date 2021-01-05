package com.example.projetoprocessamentoarquivo.service;


import com.example.projetoprocessamentoarquivo.api.model.input.AppCapturaInput;
import com.example.projetoprocessamentoarquivo.api.model.input.OperadorIdInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.model.AppCaptura;
import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import com.example.projetoprocessamentoarquivo.domain.repository.AppCapturaRepository;
import com.example.projetoprocessamentoarquivo.domain.repository.OperadorRepository;
import com.example.projetoprocessamentoarquivo.domain.service.AppCapturaService;
import com.example.projetoprocessamentoarquivo.domain.service.OperadorService;
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
public class AppCapturaServiceTest {

    @InjectMocks
    private AppCapturaService appCapturaService;
    @InjectMocks
    private OperadorService operadorService;
    @Mock
    private AppCapturaRepository appCapturaRepository;
    @Mock
    private OperadorRepository operadorRepository;

    private AppCaptura appCaptura;
    private List<AppCaptura> appCapturas;
    private AppCapturaInput appCapturaIn;
    private AppCapturaInput appCapturaAdd;
    private Operador operador;
    private OperadorIdInput operadorIdInput;

    @Before
    public void init() {
        operadorIdInput = new OperadorIdInput();
        operadorIdInput.setId(1L);
        operador = new Operador();
        operador.setTipo("mobile");
        operador.setId(1L);
        appCaptura = new AppCaptura();
        appCaptura.setId(1L);
        appCaptura.setNome("Teste");
        appCaptura.setFormato("teste");
        appCaptura.setOperador(operador);
        appCapturas = new ArrayList<AppCaptura>();
        appCapturas.add(new AppCaptura());
        appCapturaIn = new AppCapturaInput();
        appCapturaIn.setFormato("texte");
        appCapturaIn.setOperador(operadorIdInput);
        appCapturaIn.setFormato("pdf");
        appCapturaAdd = new AppCapturaInput();
        appCapturaAdd.setNome("Teste");
        appCapturaAdd.setFormato("teste");


    }



    @Test
    public void buscarAppCapturaPorCodigoOk () {
        // cenario
        given(appCapturaRepository.findById(anyLong()))
                .willReturn(Optional.of(appCaptura));

        // execucao
        var result = appCapturaService.listaPorId(1L);

        // validação
        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result.getId()));

        verify(appCapturaRepository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void buscarAppCapturaPorCodigoNaoOk() {

        // cenario
        given(appCapturaRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = appCapturaService.listaPorId(1L);

        // validaçao
        verify(appCapturaRepository, times(1))
                .findById(anyLong());
    }

    @Test
    public void buscarAppCapturaComSucesso () {
        // cenario
        given(appCapturaRepository.findAll())
                .willReturn(appCapturas);

        // execucao
        var result = appCapturaService.listar();

        // validação
        assertNotNull(result);

        verify(appCapturaRepository, times(1)).findAll();
    }


    @Test
    public void buscarAppCapturaSemSucesso () {
        // cenario
        given(appCapturaRepository.findAll())
                .willReturn(null);

        // execucao
        var result = appCapturaService.listar();

        // validação
        assertNull(result);

        verify(appCapturaRepository, times(1)).findAll();
    }


    @Test
    public void deletarAppCapturaPorCodigoOk() {
        // cenario
        given(appCapturaRepository.existsById(anyLong()))
                .willReturn(Boolean.TRUE);

        doNothing().when(appCapturaRepository).deleteById(anyLong());

        // execucao
        appCapturaService.deletar(1L);

        //validação
        verify(appCapturaRepository, times(1))
                .existsById(anyLong());
        verify(appCapturaRepository, times(1))
                .deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deletarAppCapturaPorCodigoNaoOk() {
        // cenario
        given(appCapturaRepository.existsById(anyLong()))
                .willReturn(Boolean.FALSE);

        doNothing().when(appCapturaRepository).deleteById(anyLong());

        // execucao
        appCapturaService.deletar(1L);

        //validação
        verify(appCapturaRepository, times(1))
                .existsById(anyLong());
        verify(appCapturaRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    public void atualizarAppCapturaPorCodigoOk () {
        // cenario
        given(appCapturaRepository.findById(anyLong()))
                .willReturn(Optional.of(appCaptura));
        given(operadorRepository.findById(anyLong()))
                .willReturn(Optional.of(operador));


        // execucao
        appCapturaService.setOperadorService(operadorService);
        operadorService.setOperadorRepository(operadorRepository);

        // execucao
        var result = appCapturaService.atualizar(1L,appCapturaIn);

        // validação

        verify(appCapturaRepository, times(1)).findById(anyLong());
        verify(appCapturaRepository, times(1)).save(appCaptura);
    }

    @Test(expected = NotFoundException.class)
    public void atualizarAppCapturaPorCodigoNaoOk () {
        // cenario
        given(appCapturaRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = appCapturaService.atualizar(1L,appCapturaIn);

        // validação

        verify(appCapturaRepository, times(1)).findById(anyLong());
        verify(appCapturaRepository, times(1)).save(appCaptura);
    }

    @Test
    public void incluirAppCapturaOk () {


        // cenario
        given(appCapturaRepository.save(appCaptura))
                .willReturn(appCaptura);

        // execucao
        var result = appCapturaRepository.save(appCaptura);


        // validação
        assertEquals(appCaptura.getFormato(), appCapturaAdd.getFormato());


        verify(appCapturaRepository, times(1)).save(appCaptura);
    }


    @Test
    public void incluirAppCapturaNaoOk () {

        // cenario
        given(appCapturaRepository.save(appCaptura))
                .willReturn(null);

        // execucao
        var result = appCapturaRepository.save(appCaptura);


        // validação
        assertNull(result);

        verify(appCapturaRepository, times(1)).save(appCaptura);
    }

}


