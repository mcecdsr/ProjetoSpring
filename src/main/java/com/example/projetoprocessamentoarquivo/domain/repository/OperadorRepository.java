package com.example.projetoprocessamentoarquivo.domain.repository;

import com.example.projetoprocessamentoarquivo.domain.model.Operador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperadorRepository extends JpaRepository<Operador, Long> {
}
