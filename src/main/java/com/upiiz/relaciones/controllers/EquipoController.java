package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.models.CustomResponse;
import com.upiiz.relaciones.models.EquipoEntity;
import com.upiiz.relaciones.services.EquipoService;
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
@RequestMapping("api/v1/equipo")
public class EquipoController {
    @Autowired
    EquipoService equipoService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<EquipoEntity>>> getElements() {
        List<EquipoEntity> listaElementos = new ArrayList<>();
        Link allElementosLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            listaElementos = equipoService.getEquipos();
            if (!listaElementos.isEmpty()) {
                CustomResponse<List<EquipoEntity>> response = new CustomResponse<>(1, "Equipos encontrados", listaElementos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Equipos no encontrados", listaElementos, links));
            }
        } catch (Exception e) {
            CustomResponse<List<EquipoEntity>> response = new CustomResponse<>(0, "Error interno en el servidor", listaElementos, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<EquipoEntity>> getElementById(@PathVariable("id") Long id) {
        Optional<EquipoEntity> elemento = null;
        CustomResponse<EquipoEntity> response = null;
        Link allElementosLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            elemento = equipoService.getEquipoById(id);
            if (elemento.isPresent()) {
                response = new CustomResponse<>(1, "Equipo encontrado", elemento.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Equipo no encontrados", null, links));
            }
        } catch (Exception e) {
            response = new CustomResponse<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<EquipoEntity>> createELement(@RequestBody EquipoEntity elemento) {
        Link allElementsLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            EquipoEntity equipoEntity = equipoService.createEquipo(elemento);
            if (equipoEntity != null) {
                CustomResponse<EquipoEntity> response = new CustomResponse<>(1, "Equipo creado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                CustomResponse<EquipoEntity> response = new CustomResponse<>(0, "Error interno de servidor", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            CustomResponse<EquipoEntity> response = new CustomResponse<>(500, "Error interno de servidor: Verifique sus datos", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<EquipoEntity>> deleteElement(@PathVariable Long id) {
        Optional<EquipoEntity> element = null;
        CustomResponse<EquipoEntity> response = null;
        Link allElementsLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element = equipoService.getEquipoById(id);
            if (element.isPresent()) {
                equipoService.deleteEquipoById(id);
                response = new CustomResponse<>(1, "Equipo eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<EquipoEntity>> updateClient(@RequestBody EquipoEntity element, @PathVariable Long id) {
        Link allElementsLink = linkTo(EquipoController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element.setId(id);
            if (equipoService.getEquipoById(id).isPresent()) {
                EquipoEntity equipoEntity = equipoService.createEquipo(element);
                CustomResponse<EquipoEntity> response = new CustomResponse<>(1, "Equipo actualizado", equipoEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<EquipoEntity> response = new CustomResponse<>(0, "Equipo no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<EquipoEntity> response = new CustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}