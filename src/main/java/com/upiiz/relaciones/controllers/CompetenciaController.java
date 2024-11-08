package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.models.CustomResponse;
import com.upiiz.relaciones.models.CompetenciaEntity;
import com.upiiz.relaciones.services.CompetenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v1/competencia")
public class CompetenciaController {
    @Autowired
    CompetenciaService competenciaService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<CompetenciaEntity>>> getElements() {
        List<CompetenciaEntity> listaElementos = new ArrayList<>();
        Link allElementosLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            listaElementos = competenciaService.getCompetencias();
            if (!listaElementos.isEmpty()) {
                CustomResponse<List<CompetenciaEntity>> response = new CustomResponse<>(1, "Competencias encontradas", listaElementos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Competencias no encontradas", listaElementos, links));
            }
        } catch (Exception e) {
            CustomResponse<List<CompetenciaEntity>> response = new CustomResponse<>(0, "Error interno en el servidor", listaElementos, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<CompetenciaEntity>> getElementById(@PathVariable("id") Long id) {
        Optional<CompetenciaEntity> elemento = null;
        CustomResponse<CompetenciaEntity> response = null;
        Link allElementosLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            elemento = competenciaService.getCompetenciaById(id);
            if (elemento.isPresent()) {
                response = new CustomResponse<>(1, "Competencia encontrada", elemento.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Competencia no encontrada", null, links));
            }
        } catch (Exception e) {
            response = new CustomResponse<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<CompetenciaEntity>> createELement(@RequestBody CompetenciaEntity elemento) {
        Link allElementsLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            CompetenciaEntity competenciaEntity = competenciaService.createCompetencia(elemento);
            if (competenciaEntity != null) {
                CustomResponse<CompetenciaEntity> response = new CustomResponse<>(1, "Competencia creada", competenciaEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                CustomResponse<CompetenciaEntity> response = new CustomResponse<>(0, "Error interno de servidor", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            CustomResponse<CompetenciaEntity> response = new CustomResponse<>(500, "Error interno de servidor: Verifique sus datos", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<CompetenciaEntity>> deleteElement(@PathVariable Long id) {
        Optional<CompetenciaEntity> element = null;
        CustomResponse<CompetenciaEntity> response = null;
        Link allElementsLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element = competenciaService.getCompetenciaById(id);
            if (element.isPresent()) {
                competenciaService.deleteEntrenadorById(id);
                response = new CustomResponse<>(1, "Competencia eliminada", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Competencia no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<CompetenciaEntity>> updateClient(@RequestBody CompetenciaEntity element, @PathVariable Long id) {
        Link allElementsLink = linkTo(CompetenciaController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element.setId(id);
            if (competenciaService.getCompetenciaById(id).isPresent()) {
                CompetenciaEntity competenciaEntity = competenciaService.createCompetencia(element);
                CustomResponse<CompetenciaEntity> response = new CustomResponse<>(1, "Competencia actualizada", competenciaEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<CompetenciaEntity> response = new CustomResponse<>(0, "Competencia no encontrada", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<CompetenciaEntity> response = new CustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}