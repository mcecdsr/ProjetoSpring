package com.example.projetoprocessamentoarquivo.domain.repository;

import com.example.projetoprocessamentoarquivo.domain.model.AppCaptura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppCapturaRepository extends JpaRepository<AppCaptura,Long> {
}
