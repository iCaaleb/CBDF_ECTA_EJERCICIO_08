package com.upiiz.relaciones.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "equipo")
public class EquipoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    // Creamos una relacion uno a uno
    @OneToOne(targetEntity = EntrenadorEntity.class)
    private EntrenadorEntity entrenadorEntity;

    // Muchos equipos tienen una liga
    @ManyToOne(targetEntity = LigaEntity.class)
    private LigaEntity ligaEntity;

    // Muchos Equipos tienen muchas competencias
    @ManyToMany(targetEntity = CompetenciaEntity.class, fetch = FetchType.LAZY)
    private List<CompetenciaEntity> competenciaEntityList;
}
