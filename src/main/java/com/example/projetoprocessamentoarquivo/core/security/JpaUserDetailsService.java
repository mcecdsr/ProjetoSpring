package com.example.projetoprocessamentoarquivo.core.security;

import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import com.example.projetoprocessamentoarquivo.domain.repository.OperadorRepository;
import com.example.projetoprocessamentoarquivo.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class JpaUserDetailsService implements UserDetailsService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomeUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario nao encontrado!"));
        return new AuthUser(usuario,getAuthoritties(usuario));
    }


    private Collection<? extends GrantedAuthority> getAuthoritties(Usuario usuario) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(usuario.getOperador().getTipo().toUpperCase()));
        return grantedAuthorities;

    }
}
