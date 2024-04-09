package com.daniellsantiago.spaceprobe.application.usecases;

import com.daniellsantiago.spaceprobe.application.dto.LandSpaceProbeDTO;
import com.daniellsantiago.spaceprobe.domain.entities.Coordinates;
import com.daniellsantiago.spaceprobe.domain.entities.Direction;
import com.daniellsantiago.spaceprobe.domain.entities.Planet;
import com.daniellsantiago.spaceprobe.domain.entities.SpaceProbe;
import com.daniellsantiago.spaceprobe.domain.repository.PlanetRepository;
import com.daniellsantiago.spaceprobe.domain.repository.SpaceProbeRepository;
import com.daniellsantiago.spaceprobe.lib.exception.NotFoundException;
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
        UUID spaceProbeId = UUID.randomUUID();
        UUID planetId = UUID.randomUUID();
        Coordinates coordinates = new Coordinates(3, 4);
        Planet planet = new Planet(planetId, coordinates);
        SpaceProbe spaceProbe = new SpaceProbe(spaceProbeId);

        Coordinates landingCoordinates = new Coordinates(2, 3);
        LandSpaceProbeDTO landSpaceProbeDTO =
            new LandSpaceProbeDTO(spaceProbeId, planetId, landingCoordinates, Direction.N);

        // When
        Mockito.when(planetRepository.findById(planetId)).thenReturn(Optional.of(planet));
        Mockito.when(spaceProbeRepository.findById(spaceProbeId)).thenReturn(Optional.of(spaceProbe));
        landSpaceProbeUseCase.execute(landSpaceProbeDTO);

        // Then
        Assertions.assertNotNull(planet.isSpaceProbeOnLand(spaceProbe));
        Mockito.verify(spaceProbeRepository, Mockito.times(1)).save(spaceProbe);
    }

    @Test
    void Should_ThrowNotFound_When_PlanetIsNotStored() {
        // Given
        LandSpaceProbeDTO landSpaceProbeDTO = new LandSpaceProbeDTO(
            UUID.randomUUID(),
            UUID.randomUUID(),
            new Coordinates(2, 3),
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
        UUID planetId = UUID.randomUUID();
        Planet planet = new Planet(planetId, new Coordinates(5, 2));
        LandSpaceProbeDTO landSpaceProbeDTO = new LandSpaceProbeDTO(
            UUID.randomUUID(),
            planetId,
            new Coordinates(2, 3),
            Direction.N
        );

        // When
        Mockito.when(planetRepository.findById(planetId)).thenReturn(Optional.of(planet));
        Mockito.when(spaceProbeRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(NotFoundException.class, () -> landSpaceProbeUseCase.execute(landSpaceProbeDTO));
    }
}
