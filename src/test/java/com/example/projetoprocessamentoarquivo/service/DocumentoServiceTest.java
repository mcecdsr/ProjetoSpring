package com.example.projetoprocessamentoarquivo.service;

import com.example.projetoprocessamentoarquivo.api.model.input.DocumentoInput;
import com.example.projetoprocessamentoarquivo.api.model.input.OperadorIdInput;
import com.example.projetoprocessamentoarquivo.domain.expection.NotFoundException;
import com.example.projetoprocessamentoarquivo.domain.filter.DocumentoFilter;
import com.example.projetoprocessamentoarquivo.domain.model.Documento;
import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import com.example.projetoprocessamentoarquivo.domain.repository.DocumentoRepository;
import com.example.projetoprocessamentoarquivo.domain.repository.OperadorRepository;
import com.example.projetoprocessamentoarquivo.domain.service.DocumentoService;
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
public class DocumentoServiceTest {


    @InjectMocks
    private DocumentoService documentoService;
    @InjectMocks
    private OperadorService operadorService;
    @Mock
    private DocumentoRepository documentoRepository;
    @Mock
    private OperadorRepository operadorRepository;


    private Documento documento;
    private List<Documento> documentos;
    private DocumentoInput documentoInput;
    private DocumentoInput documentoAdd;
    private OperadorIdInput operadorIdInput;
    private Operador operador;
    private DocumentoFilter documentoFilter;

    @Before
    public void init() {
        documentoFilter = new DocumentoFilter();
        documentoFilter.setData("17/02");
        documentoFilter.setFormato("txt");
        documentoFilter.setAppCaptura("mobile");
        documentoFilter.setOperadorId(1L);
        operadorIdInput = new OperadorIdInput();
        operadorIdInput.setId(1L);
        documento = new Documento();
        documento.setId(1L);
        documento.setFormato("txt");
        documento.setNome("teste");
        documento.setData("17-02");
        documentos = new ArrayList<Documento>();
        documentos.add(new Documento());
        documentoInput = new DocumentoInput();
        documentoInput.setFormato("doc");
        documentoInput.setNome("teste1");
        documentoInput.setData("18-02");
        documentoAdd = new DocumentoInput();
        documentoAdd.setFormato("txt");
        documentoAdd.setNome("teste");
        documentoAdd.setData("17-02");
        operador = new Operador();
        operador.setTipo("ADMIN");
        documentoInput.setOperador(operadorIdInput);

    }

    @Test
    public void buscarDocumentoPorCodigoOk () {
        // cenario
        given(documentoRepository.findById(anyLong()))
                .willReturn(Optional.of(documento));

        // execucao
        var result = documentoService.listaPorId(1L);

        // validação
        assertNotNull(result);
        assertEquals(Optional.of(1L), Optional.of(result.getId()));

        verify(documentoRepository, times(1)).findById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void buscarDocumentoPorCodigoNaoOk() {

        // cenario
        given(documentoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = documentoService.listaPorId(1L);

        // validaçao
        verify(documentoRepository, times(1))
                .findById(anyLong());
    }

    @Test
    public void buscarDocumentoComSucesso () {
        // cenario
        given(documentoRepository.findAll())
                .willReturn(documentos);

        // execucao
        var result = documentoService.listar();

        // validação
        assertNotNull(result);

        verify(documentoRepository, times(1)).findAll();
    }


    @Test
    public void buscarDocumentoSemSucesso () {
        // cenario
        given(documentoRepository.findAll())
                .willReturn(null);

        // execucao
        var result = documentoService.listar();

        // validação
        assertNull(result);

        verify(documentoRepository, times(1)).findAll();
    }


    @Test
    public void deletarDocumentoPorCodigoOk() {
        // cenario
        given(documentoRepository.existsById(anyLong()))
                .willReturn(Boolean.TRUE);

        doNothing().when(documentoRepository).deleteById(anyLong());

        // execucao
        documentoService.deletar(1L);

        //validação
        verify(documentoRepository, times(1))
                .existsById(anyLong());
        verify(documentoRepository, times(1))
                .deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void deletarDocumentoPorCodigoNaoOk() {
        // cenario
        given(documentoRepository.existsById(anyLong()))
                .willReturn(Boolean.FALSE);

        doNothing().when(documentoRepository).deleteById(anyLong());

        // execucao
        documentoService.deletar(1L);

        //validação
        verify(documentoRepository, times(1))
                .existsById(anyLong());
        verify(documentoRepository, times(1))
                .deleteById(anyLong());
    }

    @Test
    public void atualizarDocumentoPorCodigoOk () {
        // cenario
        given(documentoRepository.findById(anyLong()))
                .willReturn(Optional.of(documento));


        given(operadorRepository.findById(anyLong()))
                .willReturn(Optional.of(operador));


        // execucao
        documentoService.setOperadorService(operadorService);
        operadorService.setOperadorRepository(operadorRepository);
        var result = documentoService.atualizar(1L,documentoInput);

        // validação

        verify(documentoRepository, times(1)).findById(anyLong());
        verify(documentoRepository, times(1)).save(documento);
    }

    @Test(expected = NotFoundException.class)
    public void atualizarDocumentoPorCodigoNaoOk () {
        // cenario
        given(documentoRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // execucao
        var result = documentoService.atualizar(1L,documentoInput);

        // validação

        verify(documentoRepository, times(1)).findById(anyLong());
        verify(documentoRepository, times(1)).save(documento);
    }

    @Test
    public void incluirDocumentoOk () {


        // cenario
        given(documentoRepository.save(documento))
                .willReturn(documento);

        // execucao
        var result = documentoRepository.save(documento);


        // validação
        assertEquals(documento.getNome(), documentoAdd.getNome());


        verify(documentoRepository, times(1)).save(documento);
    }


    @Test
    public void incluirDocumentoNaoOk () {

        // cenario
        given(documentoRepository.save(documento))
                .willReturn(null);

        // execucao
        var result = documentoRepository.save(documento);


        // validação
        assertNull(result);

        verify(documentoRepository, times(1)).save(documento);
    }

}
