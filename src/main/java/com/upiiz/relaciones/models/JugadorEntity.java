package com.upiiz.relaciones.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jugador")
public class JugadorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private int edad;

    @NotNull
    private String nacionalidad;

    @NotNull
    private String posicion;

    // Muchos jugadores tienen un equipo
    @ManyToOne(targetEntity = EquipoEntity.class)
    private EquipoEntity equipoEntity;
}
