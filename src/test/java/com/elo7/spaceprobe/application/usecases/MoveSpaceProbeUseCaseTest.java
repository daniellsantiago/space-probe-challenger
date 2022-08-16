package com.elo7.spaceprobe.application.usecases;

import com.elo7.spaceprobe.application.dto.ChangeSpaceProbePositionDto;
import com.elo7.spaceprobe.application.dto.MoveCommandDto;
import com.elo7.spaceprobe.application.dto.RotateCommandDto;
import com.elo7.spaceprobe.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class MoveSpaceProbeUseCaseTest {

    @InjectMocks
    private MoveSpaceProbeUseCase moveSpaceProbeUseCase;

    @Mock
    private SpaceProbeRepository spaceProbeRepository;

    @Test
    void Should_PerformMovement_When_SpaceProbeIsStoredAndMoveCommandsAreProvided() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe();
        Position landPosition = new Position(new Coordinates(2, 2), Direction.N);
        spaceProbe.landOnPlanet(planet, landPosition);

        ChangeSpaceProbePositionDto request = new ChangeSpaceProbePositionDto(
            List.of(MoveCommandDto.M, MoveCommandDto.M),
            spaceProbe.getId()
        );

        // When
        Mockito.when(spaceProbeRepository.findById(spaceProbe.getId())).thenReturn(Optional.of(spaceProbe));
        moveSpaceProbeUseCase.execute(request);

        // Then
        Mockito.verify(spaceProbeRepository, Mockito.times(1)).save(spaceProbe);
        Assertions.assertEquals(4, spaceProbe.getCoordinates().getY());
    }

    @Test
    void Should_PerformRotation_When_SpaceProbeIsStoredAndRotationCommandsAreProvided() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe();
        Position landPosition = new Position(new Coordinates(2, 2), Direction.N);
        spaceProbe.landOnPlanet(planet, landPosition);

        ChangeSpaceProbePositionDto request = new ChangeSpaceProbePositionDto(
            List.of(RotateCommandDto.L, RotateCommandDto.R),
            spaceProbe.getId()
        );

        // When
        Mockito.when(spaceProbeRepository.findById(spaceProbe.getId())).thenReturn(Optional.of(spaceProbe));
        moveSpaceProbeUseCase.execute(request);

        // Then
        Mockito.verify(spaceProbeRepository, Mockito.times(1)).save(spaceProbe);
        Assertions.assertEquals(Direction.N, spaceProbe.getDirection());
    }

    @Test
    void Should_PerformRotationAndMovement_When_SpaceProbeIsStoredAndRotationAndMoveCommandsAreProvided() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe();
        Position landPosition = new Position(new Coordinates(2, 2), Direction.N);
        spaceProbe.landOnPlanet(planet, landPosition);

        ChangeSpaceProbePositionDto request = new ChangeSpaceProbePositionDto(
            List.of(RotateCommandDto.L, MoveCommandDto.M, RotateCommandDto.R, MoveCommandDto.M),
            spaceProbe.getId()
        );

        // When
        Mockito.when(spaceProbeRepository.findById(spaceProbe.getId())).thenReturn(Optional.of(spaceProbe));
        moveSpaceProbeUseCase.execute(request);

        // Then
        Mockito.verify(spaceProbeRepository, Mockito.times(1)).save(spaceProbe);
        Assertions.assertEquals(Direction.N, spaceProbe.getDirection());
        Assertions.assertEquals(1, spaceProbe.getCoordinates().getX());
        Assertions.assertEquals(3, spaceProbe.getCoordinates().getY());
    }
}
