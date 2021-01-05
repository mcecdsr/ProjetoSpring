package com.example.projetoprocessamentoarquivo.domain.repository;

import com.example.projetoprocessamentoarquivo.domain.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public Optional<Usuario> findByNomeUsuario(String nome);
}
