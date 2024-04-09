package com.daniellsantiago.spaceprobe.application.usecases;

import com.daniellsantiago.spaceprobe.application.dto.LandSpaceProbeDTO;
import com.daniellsantiago.spaceprobe.domain.entities.Planet;
import com.daniellsantiago.spaceprobe.domain.entities.Position;
import com.daniellsantiago.spaceprobe.domain.entities.SpaceProbe;
import com.daniellsantiago.spaceprobe.domain.exception.BusinessException;
import com.daniellsantiago.spaceprobe.domain.repository.PlanetRepository;
import com.daniellsantiago.spaceprobe.domain.repository.SpaceProbeRepository;
import com.daniellsantiago.spaceprobe.lib.ApplicationService;
import com.daniellsantiago.spaceprobe.lib.exception.DomainException;
import com.daniellsantiago.spaceprobe.lib.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LandSpaceProbeUseCase implements ApplicationService<LandSpaceProbeDTO, Void> {
    private final PlanetRepository planetRepository;
    private final SpaceProbeRepository spaceProbeRepository;

    public LandSpaceProbeUseCase(PlanetRepository planetRepository, SpaceProbeRepository spaceProbeRepository) {
        this.planetRepository = planetRepository;
        this.spaceProbeRepository = spaceProbeRepository;
    }

    @Override
    @Transactional
    public Void execute(LandSpaceProbeDTO payload) {
        Planet planet = planetRepository.findById(payload.getPlanetId()).orElseThrow(
            () -> new NotFoundException("Planet " + payload.getPlanetId() + " not found")
        );
        SpaceProbe spaceProbe = spaceProbeRepository.findById(payload.getSpaceProbeId()).orElseThrow(
            () -> new NotFoundException("SpaceProbe " + payload.getSpaceProbeId() + " not found")
        );
        try {
            spaceProbe.landOnPlanet(planet, new Position(payload.getLandingCoordinates(), payload.getSpaceLandingDirection()));
        } catch (BusinessException ex) {
            throw new DomainException(ex.getMessage());
        }
        spaceProbeRepository.save(spaceProbe);
        return null;
    }
}
