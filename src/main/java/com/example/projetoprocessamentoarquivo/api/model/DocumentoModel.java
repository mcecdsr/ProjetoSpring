package com.example.projetoprocessamentoarquivo.api.model;

import lombok.Data;

@Data

public class DocumentoModel {


    private Long id;

    private String nome;

    private String data;

    private String formato;

    private OperadorDocumentoModel operador;

}
