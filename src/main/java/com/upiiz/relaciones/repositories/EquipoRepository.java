package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.models.EquipoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipoRepository extends JpaRepository<EquipoEntity, Long> {
}
