package com.example.projetoprocessamentoarquivo.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table (name = "tb_operador")
public class Operador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, name = "c_tipo" , length = 130)
    private String tipo;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany (mappedBy = "operador")
    private List<AppCaptura> appCapturas = new ArrayList<>(0);

    @OneToMany (mappedBy = "operador")
    private List<Documento> documentos = new ArrayList<>(0);

}
