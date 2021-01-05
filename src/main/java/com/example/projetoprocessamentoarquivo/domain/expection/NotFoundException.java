package com.example.projetoprocessamentoarquivo.domain.expection;

public class NotFoundException extends RuntimeException{

 public NotFoundException(String erro){
        super (erro);
        }
}
