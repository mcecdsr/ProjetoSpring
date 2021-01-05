package com.example.projetoprocessamentoarquivo.api.model.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class DocumentoArquivoInput {

    private MultipartFile arquivo;
    private String appCaptura;
}
