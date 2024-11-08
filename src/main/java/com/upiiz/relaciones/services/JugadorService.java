package com.upiiz.relaciones.services;

import com.upiiz.relaciones.models.JugadorEntity;
import com.upiiz.relaciones.repositories.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JugadorService {

    @Autowired
    JugadorRepository jugadorRepository;

    public List<JugadorEntity> getJugadores() {
        return jugadorRepository.findAll();
    }

    public Optional<JugadorEntity> getJugadorById(Long id) {
        return jugadorRepository.findById(id);
    }

    public JugadorEntity createJugador(JugadorEntity equipo) {
        return jugadorRepository.save(equipo);
    }

    public Optional<JugadorEntity> updateJugador(Long id, JugadorEntity jugador) {
        return jugadorRepository.findById(id).map(jugadorExistente -> {
            // Aquí se actualizan solo los campos necesarios
            jugadorExistente.setNombre(jugador.getNombre());
            jugadorExistente.setNacionalidad(jugador.getNacionalidad());
            jugadorExistente.setEdad(jugador.getEdad());
            jugadorExistente.setPosicion(jugador.getPosicion());
            // Agrega más setters si necesitas actualizar otros campos

            return jugadorRepository.save(jugadorExistente);
        });
    }

    public void deleteJugadorById(Long id) {
        jugadorRepository.deleteById(id);
    }
}
