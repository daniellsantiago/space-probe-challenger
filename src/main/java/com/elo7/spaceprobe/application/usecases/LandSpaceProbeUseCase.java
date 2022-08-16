package com.elo7.spaceprobe.application.usecases;

import com.elo7.spaceprobe.application.dto.LandSpaceProbeDTO;
import com.elo7.spaceprobe.domain.*;
import com.elo7.spaceprobe.lib.ApplicationService;
import com.elo7.spaceprobe.lib.exception.DomainException;
import com.elo7.spaceprobe.lib.exception.NotFoundException;
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
