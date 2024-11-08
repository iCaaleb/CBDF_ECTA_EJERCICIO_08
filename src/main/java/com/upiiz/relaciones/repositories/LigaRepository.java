package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.models.LigaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigaRepository extends JpaRepository<LigaEntity, Long> {
}
