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

    @Test
    void Should_ReturnTrue_When_PlanetIsLanded() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        spaceProbe.landOnPlanet(planet, new Coordinates(1, 2));

        // When
        Boolean isLanded = spaceProbe.isLanded();

        // Then
        Assertions.assertTrue(isLanded);
    }

    @Test
    void Should_ReturnFalse_When_PlanetIsNotLanded() {
        // Given
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // When
        Boolean isLanded = spaceProbe.isLanded();

        // Then
        Assertions.assertFalse(isLanded);
    }

    @Test
    void Should_ReturnPlanetPosition_When_ItIsLanded() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        spaceProbe.landOnPlanet(planet, new Coordinates(5, 5));

        // When
        Coordinates planetPosition = spaceProbe.getPlanetPosition();

        // Then
        Assertions.assertEquals(new Coordinates(5, 5), planetPosition);
    }

    @Test
    void Should_ThrowException_When_TriesToGetPlanetPositionButSpaceProbeIsNotRegistered() {
        // Given
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // Then / When
        Assertions.assertThrows(Exception.class, spaceProbe::getPlanetPosition);
    }

    @Test
    void Should_ChangeSpaceProbePositionOnPlanet_When_ItIsLanded() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        Coordinates spaceProbeCoordinates = new Coordinates(3, 4);
        spaceProbe.landOnPlanet(planet, spaceProbeCoordinates);

        // When
        Coordinates newCoordinates = new Coordinates(0, 0);
        spaceProbe.move(newCoordinates);

        // Then
        Assertions.assertEquals(newCoordinates, spaceProbe.getPlanetPosition());
    }

    @Test
    void Should_ThrowException_When_TriesToMovePlanetButItIsLanded() {
        // Given
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());

        // Then / When
        Assertions.assertThrows(Exception.class, () -> spaceProbe.move(new Coordinates(5, 5)));
    }
}
