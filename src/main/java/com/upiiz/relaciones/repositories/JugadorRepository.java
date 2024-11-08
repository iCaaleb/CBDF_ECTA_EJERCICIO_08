package com.upiiz.relaciones.repositories;

import com.upiiz.relaciones.models.JugadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JugadorRepository extends JpaRepository<JugadorEntity, Long> {
}
