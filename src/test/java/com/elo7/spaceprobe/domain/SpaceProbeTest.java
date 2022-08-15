package com.elo7.spaceprobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SpaceProbeTest {

    @Test
    void Should_LandSpaceProbeOnPlanet_When_PlanetPositionIsAvailable() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When
        Position spaceProbePosition = new Position(new Coordinates(3, 4), Direction.E);
        spaceProbe.landOnPlanet(planet, spaceProbePosition);

        // Then
        Assertions.assertEquals(spaceProbePosition.getCoordinates(), planet.getSpaceProbeCoordinates(spaceProbe));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnUnavailablePlanetPosition() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe landedSpaceProbe = new SpaceProbe(UUID.randomUUID());
        Position landPosition = new Position(new Coordinates(3, 2), Direction.N);
        landedSpaceProbe.landOnPlanet(planet, landPosition);

        // When / Then
        SpaceProbe newSpaceProbe = new SpaceProbe(UUID.randomUUID());
        Assertions.assertThrows(Exception.class, () -> newSpaceProbe.landOnPlanet(planet, landPosition));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnNegativePlanetPosition() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When / Then
        Position negativePosition = new Position(new Coordinates(-1, 2), Direction.N);
        Assertions.assertThrows(Exception.class, () -> spaceProbe.landOnPlanet(planet, negativePosition));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnOutsidePlanetPosition() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When / Then
        Position invalidPosition = new Position(new Coordinates(7, 2), Direction.N);
        Assertions.assertThrows(Exception.class, () -> spaceProbe.landOnPlanet(planet, invalidPosition));
    }

    @Test
    void Should_MoveForward_When_GivenDirectionIsNorth() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        Position spaceProbePosition = new Position(new Coordinates(3, 4), Direction.N);
        spaceProbe.landOnPlanet(planet, spaceProbePosition);

        // When
        spaceProbe.move(Direction.N);

        // Then
        Assertions.assertEquals(new Coordinates(3, 5), spaceProbe.getCoordinates());
        Assertions.assertEquals(Direction.N, spaceProbe.getDirection());
    }

    @Test
    void Should_MoveRight_When_GivenDirectionIsEast() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        Position spaceProbePosition = new Position(new Coordinates(3, 4), Direction.N);
        spaceProbe.landOnPlanet(planet, spaceProbePosition);

        // When
        spaceProbe.move(Direction.E);

        // Then
        Assertions.assertEquals(new Coordinates(4, 4), spaceProbe.getCoordinates());
        Assertions.assertEquals(Direction.E, spaceProbe.getDirection());
    }

    @Test
    void Should_MoveBack_When_GivenDirectionIsSouth() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        Position spaceProbePosition = new Position(new Coordinates(3, 4), Direction.N);
        spaceProbe.landOnPlanet(planet, spaceProbePosition);

        // When
        spaceProbe.move(Direction.S);

        // Then
        Assertions.assertEquals(new Coordinates(3, 3), spaceProbe.getCoordinates());
        Assertions.assertEquals(Direction.S, spaceProbe.getDirection());
    }

    @Test
    void Should_MoveLeft_When_GivenDirectionIsWest() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        Position spaceProbePosition = new Position(new Coordinates(3, 4), Direction.N);
        spaceProbe.landOnPlanet(planet, spaceProbePosition);

        // When
        spaceProbe.move(Direction.W);

        // Then
        Assertions.assertEquals(new Coordinates(2, 4), spaceProbe.getCoordinates());
        Assertions.assertEquals(Direction.W, spaceProbe.getDirection());
    }
}
