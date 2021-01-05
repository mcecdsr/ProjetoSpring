package com.example.projetoprocessamentoarquivo.domain.model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tb_documento")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false,name = "c_nome" , length = 130)
    private String nome;

    @Column (name = "c_app_captura" , length = 130)
    private String appCaptura;

    @Column (nullable = false,name = "c_data")
    private String data;

    @Column (name = "c_formato")
    private String formato;

    @Column (nullable = false,name = "c_valor_arquivo")
    private String valorArquivo;

    @ManyToOne
    @JoinColumn(name = "operador_id", nullable = false)
    private Operador operador;

}
