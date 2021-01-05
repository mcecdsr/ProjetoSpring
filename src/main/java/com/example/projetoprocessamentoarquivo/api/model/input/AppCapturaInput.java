package com.example.projetoprocessamentoarquivo.api.model.input;

import lombok.Data;

@Data
public class AppCapturaInput {

    private String nome;

    private String formato;

    private OperadorIdInput operador;


}
