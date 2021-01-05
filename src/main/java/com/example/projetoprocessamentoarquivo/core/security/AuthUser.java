package com.example.projetoprocessamentoarquivo.core.security;


import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class AuthUser extends User {

    private Long userId;
    private String fullName;

    public AuthUser(Usuario usuario, Collection<? extends GrantedAuthority> authorities) {
        super(usuario.getNomeUsuario(), usuario.getSenha(), authorities);
        this.fullName = usuario.getNomeUsuario();
        this.userId = usuario.getId();
    }





}
