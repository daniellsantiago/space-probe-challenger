package com.elo7.spaceprobe.integration;

import com.elo7.spaceprobe.domain.*;
import com.elo7.spaceprobe.interfaces.http.dto.LandSpaceProbeRequest;
import com.elo7.spaceprobe.utils.JsonConvertionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class SpaceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SpaceProbeRepository spaceProbeRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    void Should_LandSpaceProbeOnPlanet_When_RequestIsValidAndSpaceProbeIsNotOnLandYet() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        planetRepository.save(planet);
        spaceProbeRepository.save(spaceProbe);

        // When
        Position landingPosition = new Position(new Coordinates(2, 3), Direction.N);
        LandSpaceProbeRequest request = new LandSpaceProbeRequest(
            planet.getId(),
            new LandSpaceProbeRequest.LandSpaceProbeCoordinatesRequest(
                landingPosition.getCoordinates().getX(), landingPosition.getCoordinates().getY()
            ),
            landingPosition.getDirection()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbe.getId() + "/landing")
            .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(request)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        // Then
        SpaceProbe landedSpaceProbe = spaceProbeRepository.findById(spaceProbe.getId()).get();
        Planet planetWithSpaceProbe = planetRepository.findById(planet.getId()).get();

        Assertions.assertEquals(spaceProbe.getId(), landedSpaceProbe.getId());
        Assertions.assertEquals(planetWithSpaceProbe, landedSpaceProbe.getPlanet());
        Assertions.assertEquals(planetWithSpaceProbe.getSpaceProbeCoordinates(landedSpaceProbe), landedSpaceProbe.getCoordinates());
        Assertions.assertTrue(planetWithSpaceProbe.isSpaceProbeOnLand(landedSpaceProbe));
    }

    @Test
    void Should_GetError_When_RequestIsValid() throws Exception {
        LandSpaceProbeRequest invalidRequest = new LandSpaceProbeRequest(
            null,
            null,
            Direction.N
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + UUID.randomUUID() + "/landing")
            .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(invalidRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void Should_GetError_When_PlanetDoesNotExists() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        spaceProbeRepository.save(spaceProbe);

        // When / Then
        Position landingPosition = new Position(new Coordinates(2, 3), Direction.N);
        LandSpaceProbeRequest request = new LandSpaceProbeRequest(
            planet.getId(),
            new LandSpaceProbeRequest.LandSpaceProbeCoordinatesRequest(
                landingPosition.getCoordinates().getX(), landingPosition.getCoordinates().getY()
            ),
            landingPosition.getDirection()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbe.getId() + "/landing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(request)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void Should_GetError_When_PlanetTriesToLandOnOccupiedPlace() throws Exception {
        // Given
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        SpaceProbe spaceProbe = new SpaceProbe(UUID.randomUUID());
        SpaceProbe spaceProbeTwo = new SpaceProbe(UUID.randomUUID());

        planetRepository.save(planet);
        spaceProbeRepository.save(spaceProbeTwo);
        spaceProbeRepository.save(spaceProbe);
        // When / Then
        Position landingPosition = new Position(new Coordinates(2, 3), Direction.N);
        LandSpaceProbeRequest request = new LandSpaceProbeRequest(
            planet.getId(),
            new LandSpaceProbeRequest.LandSpaceProbeCoordinatesRequest(
                landingPosition.getCoordinates().getX(), landingPosition.getCoordinates().getY()
            ),
            landingPosition.getDirection()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbe.getId() + "/landing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(request)));

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbeTwo.getId() + "/landing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonConvertionUtils.asJsonString(request)))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }
}
