package com.example.projetoprocessamentoarquivo.domain.model;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table (name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "c_nome_usuario" , length = 130)
    private String nomeUsuario;

    @Column (name = "c_senha" , length = 130)
    private String senha;

    @OneToOne (mappedBy = "usuario")
    private Operador operador;


}
