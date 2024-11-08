package com.upiiz.relaciones.services;

import com.upiiz.relaciones.models.CompetenciaEntity;
import com.upiiz.relaciones.repositories.CompetenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompetenciaService {

    @Autowired
    CompetenciaRepository competenciaRepository;

    public List<CompetenciaEntity> getCompetencias() {
        return competenciaRepository.findAll();
    }

    public Optional<CompetenciaEntity> getCompetenciaById(Long id) {
        return competenciaRepository.findById(id);
    }

    public CompetenciaEntity createCompetencia(CompetenciaEntity entrenador) {
        return competenciaRepository.save(entrenador);
    }

    public Optional<CompetenciaEntity> updateCompetencia(Long id, CompetenciaEntity competencia) {
        return competenciaRepository.findById(id).map(competenciaExistente -> {
            // Aquí se actualizan solo los campos necesarios
            competenciaExistente.setNombre(competencia.getNombre());
            competenciaExistente.setPremio(competencia.getPremio());
            competenciaExistente.setInicio(competencia.getInicio());
            competenciaExistente.setFin(competencia.getFin());

            // Agrega más setters si necesitas actualizar otros campos

            return competenciaRepository.save(competenciaExistente);
        });
    }

    public void deleteEntrenadorById(Long id) {
        competenciaRepository.deleteById(id);
    }
}
