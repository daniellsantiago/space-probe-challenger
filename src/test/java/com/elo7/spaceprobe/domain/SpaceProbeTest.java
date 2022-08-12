package com.elo7.spaceprobe.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SpaceProbeTest {

    @Test
    void Should_LandSpaceProbeOnPlanet_When_PlanetPositionIsAvailable() throws Exception {
        // Given
        Planet planet = new Planet(new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(planet, new Coordinates(1, 2));

        // When
        Coordinates newCoordinates = new Coordinates(3, 4);
        spaceProbe.land(newCoordinates);

        // Then
        Assertions.assertTrue(spaceProbe.isLanded());
        Assertions.assertEquals(newCoordinates, spaceProbe.getPosition());
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnUnavailablePlanetPosition() throws Exception {
        // Given
        Planet planet = new Planet(new Coordinates(5, 5));

        SpaceProbe landedSpaceProbe = new SpaceProbe(planet, new Coordinates(1, 2));
        Coordinates landCoordinates = new Coordinates(3, 2);
        landedSpaceProbe.land(landCoordinates);

        SpaceProbe inOrbitSpaceProbe = new SpaceProbe(planet, new Coordinates(4, 2));

        // When / Then
        Assertions.assertThrows(Exception.class, () -> inOrbitSpaceProbe.land(landCoordinates));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnNegativePlanetPosition() {
        // Given
        Planet planet = new Planet(new Coordinates(5, 5));

        SpaceProbe spaceProbe = new SpaceProbe(planet, new Coordinates(1, 2));

        // When / Then
        Coordinates negativeCoordinates = new Coordinates(-1, 2);
        Assertions.assertThrows(Exception.class, () -> spaceProbe.land(negativeCoordinates));
    }

    @Test
    void Should_ThrowException_When_TriesToLandOnOutsidePlanetPosition() {
        // Given
        Planet planet = new Planet(new Coordinates(5, 5));

        SpaceProbe spaceProbe = new SpaceProbe(planet, new Coordinates(1, 2));

        // When / Then
        Coordinates negativeCoordinates = new Coordinates(7, 2);
        Assertions.assertThrows(Exception.class, () -> spaceProbe.land(negativeCoordinates));
    }
}
