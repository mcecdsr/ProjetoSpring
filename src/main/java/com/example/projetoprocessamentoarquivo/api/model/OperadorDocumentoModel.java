package com.example.projetoprocessamentoarquivo.api.model;

import lombok.Data;

import java.util.List;

@Data
public class OperadorDocumentoModel {


    private String tipo;

    private List<AppCapturaResumoModel> appCapturas ;
}
