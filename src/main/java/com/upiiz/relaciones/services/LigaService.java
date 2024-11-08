package com.upiiz.relaciones.services;

import com.upiiz.relaciones.models.JugadorEntity;
import com.upiiz.relaciones.models.LigaEntity;
import com.upiiz.relaciones.repositories.JugadorRepository;
import com.upiiz.relaciones.repositories.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {

    @Autowired
    LigaRepository ligaRepository;

    public List<LigaEntity> getLigas() {
        return ligaRepository.findAll();
    }

    public Optional<LigaEntity> getLigaById(Long id) {
        return ligaRepository.findById(id);
    }

    public LigaEntity createLiga(LigaEntity liga) {
        return ligaRepository.save(liga);
    }

    public Optional<LigaEntity> updateLiga(Long id, LigaEntity liga) {
        return ligaRepository.findById(id).map(ligaExistente -> {
            // Aquí se actualizan solo los campos necesarios
            ligaExistente.setNombre(liga.getNombre());
            ligaExistente.setPais(liga.getPais());
            ligaExistente.setPresidente(liga.getPresidente());
            // Agrega más setters si necesitas actualizar otros campos

            return ligaRepository.save(ligaExistente);
        });
    }

    public void deleteLigaById(Long id) {
        ligaRepository.deleteById(id);
    }
}
