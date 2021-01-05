package com.example.projetoprocessamentoarquivo.api.controller;

import com.example.projetoprocessamentoarquivo.api.model.input.DocumentoArquivoInput;
import com.example.projetoprocessamentoarquivo.api.model.input.DocumentoInput;
import com.example.projetoprocessamentoarquivo.api.model.input.OperadorIdInput;
import com.example.projetoprocessamentoarquivo.domain.service.DocumentoService;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/v1/documentos/arquivo")
public class DocumentoArquivoController {

    @Autowired
    private DocumentoService documentoService;

    private List<String> extensoesImagens = Arrays.asList(new String[]{"jpeg", "png", "pdf"});
    private final String TEXTO_DEFAULT = "Documento de teste teste será feito para validar se o processamento e a contagem de palavras está correto validar teste feito anteriormente do processamento";



    @PostMapping(path = "/mobile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = "text/csv")
    public ResponseEntity<?> receberArquivoMobile(DocumentoArquivoInput documentoArquivoInput){
        try {

            MultipartFile arquivo = documentoArquivoInput.getArquivo();
            arquivo.getOriginalFilename().split("\\.");
            var arquivoText1 = Path.of("/Users/Public", arquivo.getOriginalFilename());
            String linhaArmazenada = "";
            if(isExtensaoImagem(arquivo.getOriginalFilename())){
                linhaArmazenada = TEXTO_DEFAULT;
            }else{
                linhaArmazenada = lerArquivoInput(arquivoText1, linhaArmazenada);
            }
            Map<String, Integer> contagemDocumento = processarArquivoInput(linhaArmazenada);
            DocumentoInput documentoInput = new DocumentoInput();
            OperadorIdInput idInput = new OperadorIdInput();
            idInput.setId(2L);
            documentoInput.setOperador(idInput);
            incluiDocumento(documentoArquivoInput, arquivo, linhaArmazenada, documentoInput);
            return ResponseEntity
                    .ok()
                    .body(new InputStreamResource(retornaExcel(contagemDocumento)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/desktop", consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = "text/csv")
    public ResponseEntity<?> receberArquivoDesktop(DocumentoArquivoInput documentoArquivoInput){
        try {
            MultipartFile arquivo = documentoArquivoInput.getArquivo();
            arquivo.getOriginalFilename().split("\\.");
            var arquivoText1 = Path.of("/Users/Public", arquivo.getOriginalFilename());
            String linhaArmazenada = "";
            if(isExtensaoImagem(arquivo.getOriginalFilename())){
                linhaArmazenada = TEXTO_DEFAULT;
            }else{
                linhaArmazenada = lerArquivoInput(arquivoText1, linhaArmazenada);
            }
            Map<String, Integer> contagemDocumento = processarArquivoInput(linhaArmazenada);
            DocumentoInput documentoInput = new DocumentoInput();
            OperadorIdInput idInput = new OperadorIdInput();
            idInput.setId(3L);
            documentoInput.setOperador(idInput);
            incluiDocumento(documentoArquivoInput, arquivo, linhaArmazenada,documentoInput);
            return ResponseEntity
                    .ok()
                    .body(new InputStreamResource(retornaExcel(contagemDocumento)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping(path = "/web", consumes = MediaType.MULTIPART_FORM_DATA_VALUE , produces = "text/csv")
    public ResponseEntity<?> receberArquivoWeb(DocumentoArquivoInput documentoArquivoInput){
        try {
            MultipartFile arquivo = documentoArquivoInput.getArquivo();
            arquivo.getOriginalFilename().split("\\.");
            var arquivoText1 = Path.of("/Users/Public", arquivo.getOriginalFilename());
            String linhaArmazenada = "";
            if(isExtensaoImagem(arquivo.getOriginalFilename())){
                linhaArmazenada = TEXTO_DEFAULT;
            }else{
                linhaArmazenada = lerArquivoInput(arquivoText1, linhaArmazenada);
            }
            Map<String, Integer> contagemDocumento = processarArquivoInput(linhaArmazenada);
            DocumentoInput documentoInput = new DocumentoInput();
            OperadorIdInput idInput = new OperadorIdInput();
            idInput.setId(4L);
            documentoInput.setOperador(idInput);
            incluiDocumento(documentoArquivoInput, arquivo, linhaArmazenada, documentoInput);
            return ResponseEntity
                    .ok()
                    .body(new InputStreamResource(retornaExcel(contagemDocumento)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, Integer> processarArquivoInput(String linhaArmazenada) {
        List<String> documentos = Arrays.asList(linhaArmazenada.toLowerCase().split(" "));

        Map <String ,Integer> contagemDocumento = new HashMap<>();
        for (String palavra : documentos){

          int count = Collections.frequency(documentos, palavra);

          contagemDocumento.put(palavra,count);
      }
        return contagemDocumento;
    }

    private String lerArquivoInput(Path arquivoText1, String linhaArmazenada) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(arquivoText1.toString()));
        while(br.ready()){
            String linha = br.readLine();
            linhaArmazenada += linha + " ";

        }
        br.close();
        return linhaArmazenada;
    }

    private InputStream retornaExcel(Map <String ,Integer> contagemDocumento ) throws IOException {

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Documento");

        sheet.setDefaultColumnWidth(15);
        sheet.setDefaultRowHeight((short)400);
        int rownum = 0;
        int cellnum = 0;
        Cell cell;
        Row row;

        for (Map.Entry<String, Integer> palavra : contagemDocumento.entrySet()) {
            row = sheet.createRow(rownum++);
            cellnum = 0;

            cell = row.createCell(cellnum++);
            cell.setCellValue(palavra.getKey());

            cell = row.createCell(cellnum++);
            cell.setCellValue(palavra.getValue());

        }

        FileOutputStream out = new FileOutputStream(new File("/Users/Public/palavras.xls"));
        workbook.write(out);
        out.close();
        workbook.close();

        return Files.newInputStream( Path.of("/Users/Public/palavras.xls"));
    }

    private void incluiDocumento(DocumentoArquivoInput documentoArquivoInput,
                                 MultipartFile arquivo, String linhaArmazenada , DocumentoInput documentoInput) {
        List<String> nome = Arrays.asList(arquivo.getOriginalFilename().split("\\."));
        documentoInput.setNome(nome.get(0));
        documentoInput.setFormato(nome.get(1));
        documentoInput.setData(LocalDateTime.now().toString());
        documentoInput.setValorArquivo(linhaArmazenada);
        documentoInput.setAppCaptura(documentoArquivoInput.getAppCaptura());
        documentoService.adicionar(documentoInput);
    }

    private boolean isExtensaoImagem(String nomeArquivo) {
        List<String> nome = Arrays.asList(nomeArquivo.split("\\."));

        if(extensoesImagens.contains(nome.get(1))){
            return true;
        }
        return false;

    }



}
