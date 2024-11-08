package com.upiiz.relaciones.services;

import com.upiiz.relaciones.models.EquipoEntity;
import com.upiiz.relaciones.repositories.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EquipoService {

    @Autowired
    EquipoRepository equipoRepository;

    public List<EquipoEntity> getEquipos() {
        return equipoRepository.findAll();
    }

    public Optional<EquipoEntity> getEquipoById(Long id) {
        return equipoRepository.findById(id);
    }

    public EquipoEntity createEquipo(EquipoEntity equipo) {
        return equipoRepository.save(equipo);
    }

    public Optional<EquipoEntity> updateEquipo(EquipoEntity equipo) {
        return equipoRepository.findById(equipo.getId()).map(equipoExistente -> {
            // Aquí se actualizan solo los campos necesarios
            equipoExistente.setNombre(equipo.getNombre());
            // Agrega más setters si necesitas actualizar otros campos

            return equipoRepository.save(equipoExistente);
        });
    }

    public void deleteEquipoById(Long id) {
        equipoRepository.deleteById(id);
    }
}
