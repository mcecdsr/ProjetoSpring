package com.example.projetoprocessamentoarquivo.domain.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table (name = "tb_app_captura")
public class AppCaptura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false,name = "c_nome" , length = 130)
    private String nome;

    @Column (nullable = false,name = "c_formato" , length = 130)
    private String formato;

    @ManyToOne
    @JoinColumn(name = "operador_id", nullable = false)
    private Operador operador;


}
