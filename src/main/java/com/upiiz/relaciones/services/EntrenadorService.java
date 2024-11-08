package com.upiiz.relaciones.services;

import com.upiiz.relaciones.models.EntrenadorEntity;
import com.upiiz.relaciones.models.EquipoEntity;
import com.upiiz.relaciones.repositories.EntrenadorRepository;
import com.upiiz.relaciones.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EntrenadorService {

    @Autowired
    EntrenadorRepository entrenadorRepository;

    public List<EntrenadorEntity> getEntrenadores() {
        return entrenadorRepository.findAll();
    }

    public Optional<EntrenadorEntity> getEntrenadorById(Long id) {
        return entrenadorRepository.findById(id);
    }

    public EntrenadorEntity createEntrenador(EntrenadorEntity entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Optional<EntrenadorEntity> updateEntrenador(Long id, EntrenadorEntity entrenador) {
        return entrenadorRepository.findById(id).map(entrenadorExistente -> {
            // Aquí se actualizan solo los campos necesarios
            entrenadorExistente.setNombre(entrenador.getNombre());
            entrenadorExistente.setEdad(entrenador.getEdad());
            entrenadorExistente.setNacionalidad(entrenador.getNacionalidad());
            // Agrega más setters si necesitas actualizar otros campos

            return entrenadorRepository.save(entrenadorExistente);
        });
    }

    public void deleteEntrenadorById(Long id) {
        entrenadorRepository.deleteById(id);
    }
}
