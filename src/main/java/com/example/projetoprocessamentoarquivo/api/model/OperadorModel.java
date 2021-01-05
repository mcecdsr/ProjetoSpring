package com.example.projetoprocessamentoarquivo.api.model;

import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import lombok.Data;

import java.util.List;


@Data
public class OperadorModel {

    private Long id;

    private String tipo;

    private UsuarioModel usuario;

    private List<AppCapturaResumoModel> appCapturas ;


}
