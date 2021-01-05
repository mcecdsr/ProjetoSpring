package com.example.projetoprocessamentoarquivo.api.model;

import lombok.Data;

import javax.persistence.*;

@Data
public class AppCapturaModel {

    private Long id;

    private String nome;

    private String formato;

    private OperadorResumoModel operador;
}
