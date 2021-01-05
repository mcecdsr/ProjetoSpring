package com.example.projetoprocessamentoarquivo.domain.repository;


import com.example.projetoprocessamentoarquivo.domain.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepository extends JpaRepository<Documento, Long> , JpaSpecificationExecutor<Documento> {

}
