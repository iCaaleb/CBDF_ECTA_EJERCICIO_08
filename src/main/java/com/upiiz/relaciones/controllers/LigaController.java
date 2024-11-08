package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.models.CustomResponse;
import com.upiiz.relaciones.models.LigaEntity;
import com.upiiz.relaciones.services.LigaService;
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
@RequestMapping("api/v1/liga")
public class LigaController {
    @Autowired
    LigaService ligaService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<LigaEntity>>> getElements() {
        List<LigaEntity> listaElementos = new ArrayList<>();
        Link allElementosLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            listaElementos = ligaService.getLigas();
            if (!listaElementos.isEmpty()) {
                CustomResponse<List<LigaEntity>> response = new CustomResponse<>(1, "Ligas encontradas", listaElementos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Ligas no encontradas", listaElementos, links));
            }
        } catch (Exception e) {
            CustomResponse<List<LigaEntity>> response = new CustomResponse<>(0, "Error interno en el servidor", listaElementos, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<LigaEntity>> getElementById(@PathVariable("id") Long id) {
        Optional<LigaEntity> elemento = null;
        CustomResponse<LigaEntity> response = null;
        Link allElementosLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            elemento = ligaService.getLigaById(id);
            if (elemento.isPresent()) {
                response = new CustomResponse<>(1, "Liga encontrado", elemento.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Liga no encontrados", null, links));
            }
        } catch (Exception e) {
            response = new CustomResponse<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<LigaEntity>> createELement(@RequestBody LigaEntity elemento) {
        Link allElementsLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            LigaEntity ligaEntity = ligaService.createLiga(elemento);
            if (ligaEntity != null) {
                CustomResponse<LigaEntity> response = new CustomResponse<>(1, "Liga creado", ligaEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                CustomResponse<LigaEntity> response = new CustomResponse<>(0, "Error interno de servidor", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            CustomResponse<LigaEntity> response = new CustomResponse<>(500, "Error interno de servidor: Verifique sus datos", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<LigaEntity>> deleteElement(@PathVariable Long id) {
        Optional<LigaEntity> element = null;
        CustomResponse<LigaEntity> response = null;
        Link allElementsLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element = ligaService.getLigaById(id);
            if (element.isPresent()) {
                ligaService.deleteLigaById(id);
                response = new CustomResponse<>(1, "Liga eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Liga no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<LigaEntity>> updateClient(@RequestBody LigaEntity element, @PathVariable Long id) {
        Link allElementsLink = linkTo(LigaController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element.setId(id);
            if (ligaService.getLigaById(id).isPresent()) {
                LigaEntity ligaEntity = ligaService.createLiga(element);
                CustomResponse<LigaEntity> response = new CustomResponse<>(1, "Liga actualizado", ligaEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<LigaEntity> response = new CustomResponse<>(0, "Liga no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<LigaEntity> response = new CustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}