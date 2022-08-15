package com.elo7.spaceprobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class PlanetTest {

    @Test
    void Should_RegisterSpaceProbeAndItsCoordinates_When_CoordinatesAreEitherValidAndAvailable() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When
        Coordinates landCoordinates = new Coordinates(5, 5);
        planet.registerLandOccupation(spaceProbe, landCoordinates);

        // Then
        Assertions.assertTrue(planet.isSpaceProbeOnLand(spaceProbe));
    }

    @Test
    void Should_ThrowException_When_TriesToRegisterSpaceProbeToNegativeCoordinates() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When / Then
        Coordinates landCoordinates = new Coordinates(-5, 5);
        Assertions.assertThrows(Exception.class, () -> planet.registerLandOccupation(spaceProbe, landCoordinates));
        Assertions.assertFalse(planet.isSpaceProbeOnLand(spaceProbe));
    }

    @Test
    void Should_ThrowException_When_TriesToRegisterSpaceProbeToNotAvailableCoordinates() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When / Then
        Coordinates landCoordinates = new Coordinates(6, 5);
        Assertions.assertThrows(Exception.class, () -> planet.registerLandOccupation(spaceProbe, landCoordinates));
        Assertions.assertFalse(planet.isSpaceProbeOnLand(spaceProbe));
    }

    @Test
    void Should_ReturnSpaceProbeCoordinates_When_ItIsRegistered() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        spaceProbe.landOnPlanet(planet, new Position(new Coordinates(2, 3), Direction.N));

        // When
        Coordinates spaceProbePosition = planet.getSpaceProbeCoordinates(spaceProbe);

        // Then
        Assertions.assertEquals(new Coordinates(2, 3), spaceProbePosition);
    }

    @Test
    void Should_ThrowException_When_TriesToGetUnregisteredSpaceProbePosition() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // Then / When
        Assertions.assertThrows(Exception.class, () -> planet.getSpaceProbeCoordinates(spaceProbe));
    }

    @Test
    void Should_ReturnTrue_When_SpaceProbeIsRegisteredOnPlanet() throws BusinessException {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        spaceProbe.landOnPlanet(planet, new Position(new Coordinates(4, 4), Direction.S));

        // When
        Boolean isSpaceProbeLanded = planet.isSpaceProbeOnLand(spaceProbe);

        // Then
        Assertions.assertTrue(isSpaceProbeLanded);
    }

    @Test
    void Should_ReturnFalse_When_SpaceProbeIsNotRegisteredOnPlanet() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When
        Boolean isSpaceProbeLanded = planet.isSpaceProbeOnLand(spaceProbe);

        // Then
        Assertions.assertFalse(isSpaceProbeLanded);
    }
}
