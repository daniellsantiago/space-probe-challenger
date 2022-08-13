package com.elo7.spaceprobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class PlanetTest {

    @Test
    void Should_RegisterSpaceProbeAndItsCoordinates_When_CoordinatesAreEitherValidAndAvailable() throws Exception {
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
}
