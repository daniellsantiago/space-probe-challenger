package com.elo7.spaceprobe.application.usecases;

import com.elo7.spaceprobe.application.dto.LandSpaceProbeDTO;
import com.elo7.spaceprobe.domain.entities.Coordinates;
import com.elo7.spaceprobe.domain.entities.Direction;
import com.elo7.spaceprobe.domain.entities.Planet;
import com.elo7.spaceprobe.domain.entities.SpaceProbe;
import com.elo7.spaceprobe.domain.repository.PlanetRepository;
import com.elo7.spaceprobe.domain.repository.SpaceProbeRepository;
import com.elo7.spaceprobe.lib.exception.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LandSpaceProbeUseCaseTest {

    @InjectMocks
    private LandSpaceProbeUseCase landSpaceProbeUseCase;

    @Mock
    private PlanetRepository planetRepository;

    @Mock
    private SpaceProbeRepository spaceProbeRepository;

    @Test
    void Should_LandSpaceProbe_When_PlanetAndSpaceProbeAreStored() {
        // Given
        Coordinates coordinates = new Coordinates(3, 4);
        Planet planet = new Planet(UUID.randomUUID(), coordinates);
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        Coordinates landingCoordinates = new Coordinates(UUID.randomUUID(), 2, 3);
        LandSpaceProbeDTO landSpaceProbeDTO =
            new LandSpaceProbeDTO(spaceProbe.getId(), planet.getId(), landingCoordinates, Direction.N);

        // When
        Mockito.when(planetRepository.findById(planet.getId())).thenReturn(Optional.of(planet));
        Mockito.when(spaceProbeRepository.findById(spaceProbe.getId())).thenReturn(Optional.of(spaceProbe));
        landSpaceProbeUseCase.execute(landSpaceProbeDTO);

        // Then
        Assertions.assertNotNull(spaceProbe.getPlanet());
        Mockito.verify(spaceProbeRepository, Mockito.times(1)).save(spaceProbe);
    }

    @Test
    void Should_ThrowNotFound_When_PlanetIsNotStored() {
        // Given
        LandSpaceProbeDTO landSpaceProbeDTO = new LandSpaceProbeDTO(
            UUID.randomUUID(),
            UUID.randomUUID(),
            new Coordinates(UUID.randomUUID(), 2, 3),
            Direction.N
        );

        // When
        Mockito.when(planetRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> landSpaceProbeUseCase.execute(landSpaceProbeDTO));
    }

    @Test
    void Should_ThrowNotFound_When_SpaceProbeIsNotStored() {
        // Given
        Planet planet = new Planet();
        LandSpaceProbeDTO landSpaceProbeDTO = new LandSpaceProbeDTO(
            UUID.randomUUID(),
            planet.getId(),
            new Coordinates(UUID.randomUUID(), 2, 3),
            Direction.N
        );

        // When
        Mockito.when(planetRepository.findById(planet.getId())).thenReturn(Optional.of(planet));
        Mockito.when(spaceProbeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> landSpaceProbeUseCase.execute(landSpaceProbeDTO));
    }
}
