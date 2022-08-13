package com.elo7.spaceprobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class SpaceProbeTest {

    @Test
    void Should_LandSpaceProbeOnPlanet_When_PlanetPositionIsAvailable() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When
        Coordinates spaceProbeCoordinates = new Coordinates(3, 4);
        spaceProbe.landOnPlanet(planet, spaceProbeCoordinates);

        // Then
        Assertions.assertTrue(spaceProbe.isLanded());
        Assertions.assertEquals(spaceProbeCoordinates, spaceProbe.getPlanetPosition());
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnUnavailablePlanetPosition() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe landedSpaceProbe = new SpaceProbe(UUID.randomUUID());
        Coordinates landCoordinates = new Coordinates(3, 2);
        landedSpaceProbe.landOnPlanet(planet, landCoordinates);

        // When / Then
        SpaceProbe newSpaceProbe = new SpaceProbe(UUID.randomUUID());
        Assertions.assertThrows(Exception.class, () -> newSpaceProbe.landOnPlanet(planet, landCoordinates));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnNegativePlanetPosition() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When / Then
        Coordinates negativeCoordinates = new Coordinates(-1, 2);
        Assertions.assertThrows(Exception.class, () -> spaceProbe.landOnPlanet(planet, negativeCoordinates));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnOutsidePlanetPosition() {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When / Then
        Coordinates invalidCoordinates = new Coordinates(7, 2);
        Assertions.assertThrows(Exception.class, () -> spaceProbe.landOnPlanet(planet, invalidCoordinates));
    }
}
