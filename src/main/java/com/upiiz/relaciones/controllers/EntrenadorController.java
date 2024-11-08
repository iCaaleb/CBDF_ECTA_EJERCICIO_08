package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.models.CustomResponse;
import com.upiiz.relaciones.models.EntrenadorEntity;
import com.upiiz.relaciones.services.EntrenadorService;
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
@RequestMapping("api/v1/entrenador")
public class EntrenadorController {
    @Autowired
    EntrenadorService entrenadorService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<EntrenadorEntity>>> getElements() {
        List<EntrenadorEntity> listaElementos = new ArrayList<>();
        Link allElementosLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            listaElementos = entrenadorService.getEntrenadores();
            if (!listaElementos.isEmpty()) {
                CustomResponse<List<EntrenadorEntity>> response = new CustomResponse<>(1, "Entrenadores encontrados", listaElementos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Entranadores no encontrados", listaElementos, links));
            }
        } catch (Exception e) {
            CustomResponse<List<EntrenadorEntity>> response = new CustomResponse<>(0, "Error interno en el servidor", listaElementos, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<EntrenadorEntity>> getElementById(@PathVariable("id") Long id) {
        Optional<EntrenadorEntity> elemento = null;
        CustomResponse<EntrenadorEntity> response = null;
        Link allElementosLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            elemento = entrenadorService.getEntrenadorById(id);
            if (elemento.isPresent()) {
                response = new CustomResponse<>(1, "Entrenador encontrado", elemento.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Entrenador no encontrados", null, links));
            }
        } catch (Exception e) {
            response = new CustomResponse<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<EntrenadorEntity>> createELement(@RequestBody EntrenadorEntity elemento) {
        Link allElementsLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            EntrenadorEntity entrenadorEntity = entrenadorService.createEntrenador(elemento);
            if (entrenadorEntity != null) {
                CustomResponse<EntrenadorEntity> response = new CustomResponse<>(1, "Entrenador creado", entrenadorEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                CustomResponse<EntrenadorEntity> response = new CustomResponse<>(0, "Error interno de servidor", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            CustomResponse<EntrenadorEntity> response = new CustomResponse<>(500, "Error interno de servidor: Verifique sus datos", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<EntrenadorEntity>> deleteElement(@PathVariable Long id) {
        Optional<EntrenadorEntity> element = null;
        CustomResponse<EntrenadorEntity> response = null;
        Link allElementsLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element = entrenadorService.getEntrenadorById(id);
            if (element.isPresent()) {
                entrenadorService.deleteEntrenadorById(id);
                response = new CustomResponse<>(1, "Entrenador eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<EntrenadorEntity>> updateClient(@RequestBody EntrenadorEntity element, @PathVariable Long id) {
        Link allElementsLink = linkTo(EntrenadorController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element.setId(id);
            if (entrenadorService.getEntrenadorById(id).isPresent()) {
                EntrenadorEntity entrenadorEntity = entrenadorService.createEntrenador(element);
                CustomResponse<EntrenadorEntity> response = new CustomResponse<>(1, "Entrenador actualizado", entrenadorEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<EntrenadorEntity> response = new CustomResponse<>(0, "Entrenador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<EntrenadorEntity> response = new CustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}