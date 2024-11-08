package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.models.CompetenciaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompetenciaRepository extends JpaRepository<CompetenciaEntity, Long> {
}
