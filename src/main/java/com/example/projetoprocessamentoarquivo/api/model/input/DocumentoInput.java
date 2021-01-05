package com.example.projetoprocessamentoarquivo.api.model.input;


import lombok.Data;



@Data
public class DocumentoInput {


    private String nome;

    private String appCaptura;

    private String data;

    private String formato;

    private String valorArquivo;

    private OperadorIdInput operador;

}
