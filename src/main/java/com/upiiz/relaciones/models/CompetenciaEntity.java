package com.upiiz.relaciones.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.transform.sax.SAXResult;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "competencia")
public class CompetenciaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String premio;

    @NotNull
    private String inicio;

    @NotNull
    private String fin;
}
