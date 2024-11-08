package com.upiiz.relaciones.controllers;

import com.upiiz.relaciones.models.CustomResponse;
import com.upiiz.relaciones.models.JugadorEntity;
import com.upiiz.relaciones.services.JugadorService;
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
@RequestMapping("api/v1/jugador")
public class JugadorController {
    @Autowired
    JugadorService jugadorService;

    @GetMapping
    public ResponseEntity<CustomResponse<List<JugadorEntity>>> getElements() {
        List<JugadorEntity> listaElementos = new ArrayList<>();
        Link allElementosLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            listaElementos = jugadorService.getJugadores();
            if (!listaElementos.isEmpty()) {
                CustomResponse<List<JugadorEntity>> response = new CustomResponse<>(1, "Jugadores encontrados", listaElementos, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Entranadores no encontrados", listaElementos, links));
            }
        } catch (Exception e) {
            CustomResponse<List<JugadorEntity>> response = new CustomResponse<>(0, "Error interno en el servidor", listaElementos, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<JugadorEntity>> getElementById(@PathVariable("id") Long id) {
        Optional<JugadorEntity> elemento = null;
        CustomResponse<JugadorEntity> response = null;
        Link allElementosLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allElementosLink);

        try {
            elemento = jugadorService.getJugadorById(id);
            if (elemento.isPresent()) {
                response = new CustomResponse<>(1, "Jugador encontrado", elemento.get(), links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CustomResponse<>(0, "Jugador no encontrados", null, links));
            }
        } catch (Exception e) {
            response = new CustomResponse<>(0, "Error interno de servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<CustomResponse<JugadorEntity>> createELement(@RequestBody JugadorEntity elemento) {
        Link allElementsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            JugadorEntity jugadorEntity = jugadorService.createJugador(elemento);
            if (jugadorEntity != null) {
                CustomResponse<JugadorEntity> response = new CustomResponse<>(1, "Jugador creado", jugadorEntity, links);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                CustomResponse<JugadorEntity> response = new CustomResponse<>(0, "Error interno de servidor", null, links);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            CustomResponse<JugadorEntity> response = new CustomResponse<>(500, "Error interno de servidor: Verifique sus datos", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse<JugadorEntity>> deleteElement(@PathVariable Long id) {
        Optional<JugadorEntity> element = null;
        CustomResponse<JugadorEntity> response = null;
        Link allElementsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element = jugadorService.getJugadorById(id);
            if (element.isPresent()) {
                jugadorService.deleteJugadorById(id);
                response = new CustomResponse<>(1, "Jugador eliminado", null, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                response = new CustomResponse<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            response = new CustomResponse<>(500, "Error interno de servidor", null, links
            );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomResponse<JugadorEntity>> updateClient(@RequestBody JugadorEntity element, @PathVariable Long id) {
        Link allElementsLink = linkTo(JugadorController.class).withSelfRel();
        List<Link> links = List.of(allElementsLink);
        try {
            element.setId(id);
            if (jugadorService.getJugadorById(id).isPresent()) {
                JugadorEntity jugadorEntity = jugadorService.createJugador(element);
                CustomResponse<JugadorEntity> response = new CustomResponse<>(1, "Jugador actualizado", jugadorEntity, links);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                CustomResponse<JugadorEntity> response = new CustomResponse<>(0, "Jugador no encontrado", null, links);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            CustomResponse<JugadorEntity> response = new CustomResponse<>(500, "Error interno del servidor", null, links);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}