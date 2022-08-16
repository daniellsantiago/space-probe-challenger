package com.elo7.spaceprobe.integration;

import com.elo7.spaceprobe.domain.entities.*;
import com.elo7.spaceprobe.domain.repository.PlanetRepository;
import com.elo7.spaceprobe.domain.repository.SpaceProbeRepository;
import com.elo7.spaceprobe.interfaces.http.dto.ChangeSpaceProbePositionRequest;
import com.elo7.spaceprobe.interfaces.http.dto.ChangeSpaceProbePositionResponse;
import com.elo7.spaceprobe.interfaces.http.dto.LandSpaceProbeRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SpaceControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SpaceProbeRepository spaceProbeRepository;

    @Autowired
    private PlanetRepository planetRepository;

    @Test
    @Transactional
    void Should_LandSpaceProbeOnPlanet_When_RequestIsValidAndSpaceProbeIsNotOnLandYet() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        Planet planet = createGenericPlanet();
        SpaceProbe spaceProbe = createGenericSpaceProbe(spaceProbeId);

        // When
        Position landingPosition = new Position(new Coordinates(2, 3), Direction.N);
        LandSpaceProbeRequest request = new LandSpaceProbeRequest(
            planet.getId(),
            new LandSpaceProbeRequest.LandSpaceProbeCoordinatesRequest(
                landingPosition.getCoordinates().getX(), landingPosition.getCoordinates().getY()
            ),
            landingPosition.getDirection()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbeId + "/landing")
            .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isCreated());

        // Then
        SpaceProbe landedSpaceProbe = spaceProbeRepository.findById(spaceProbeId).get();
        Planet planetWithSpaceProbe = planetRepository.findById(planet.getId()).get();

        Assertions.assertEquals(spaceProbe, landedSpaceProbe);
        Assertions.assertTrue(planetWithSpaceProbe.isSpaceProbeOnLand(spaceProbe));
        Assertions.assertEquals(planetWithSpaceProbe.getSpaceProbeCoordinates(landedSpaceProbe), landedSpaceProbe.getCoordinates());
        Assertions.assertTrue(planetWithSpaceProbe.isSpaceProbeOnLand(landedSpaceProbe));
    }

    @Test
    @Transactional
    void Should_GetError_When_RequestIsValid() throws Exception {
        LandSpaceProbeRequest invalidRequest = new LandSpaceProbeRequest(
            null,
            null,
            Direction.N
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + UUID.randomUUID() + "/landing")
            .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Transactional
    void Should_GetError_When_PlanetDoesNotExists() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        createGenericSpaceProbe(spaceProbeId);

        // When / Then
        Position landingPosition = new Position(new Coordinates(2, 3), Direction.N);
        LandSpaceProbeRequest request = new LandSpaceProbeRequest(
            planet.getId(),
            new LandSpaceProbeRequest.LandSpaceProbeCoordinatesRequest(
                landingPosition.getCoordinates().getX(), landingPosition.getCoordinates().getY()
            ),
            landingPosition.getDirection()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbeId + "/landing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional
    void Should_GetError_When_PlanetTriesToLandOnOccupiedPlace() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        UUID spaceProbeIdTwo = UUID.randomUUID();
        Planet planet = createGenericPlanet();
        createGenericSpaceProbe(spaceProbeId);
        createGenericSpaceProbe(spaceProbeIdTwo);

        // When / Then
        Position landingPosition = new Position(new Coordinates(2, 3), Direction.N);
        LandSpaceProbeRequest request = new LandSpaceProbeRequest(
            planet.getId(),
            new LandSpaceProbeRequest.LandSpaceProbeCoordinatesRequest(
                landingPosition.getCoordinates().getX(), landingPosition.getCoordinates().getY()
            ),
            landingPosition.getDirection()
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbeId + "/landing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(MockMvcRequestBuilders.post("/spaceProbe/" + spaceProbeIdTwo + "/landing")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    void Should_MoveSpaceProbeForward_When_CommandsIsMoveCommand() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        Planet planet = createGenericPlanet();
        SpaceProbe spaceProbe = createGenericSpaceProbe(spaceProbeId);

        Position landPosition = new Position(new Coordinates(2, 2), Direction.N);
        spaceProbe.landOnPlanet(planet, landPosition);
        spaceProbeRepository.save(spaceProbe);

        // When
        ChangeSpaceProbePositionRequest request = new ChangeSpaceProbePositionRequest("M");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/spaceProbe/" + spaceProbeId + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        ChangeSpaceProbePositionResponse mappedResponse =
            objectMapper.readValue(result.getResponse().getContentAsString(), ChangeSpaceProbePositionResponse.class);

        // Then
        Assertions.assertEquals(landPosition.getDirection(), mappedResponse.getDirection());
        Assertions.assertEquals(3, mappedResponse.getY());
        Assertions.assertEquals(landPosition.getCoordinates().getY(), mappedResponse.getY());
        Assertions.assertEquals(landPosition.getCoordinates().getX(), mappedResponse.getX());
    }

    @Test
    @Transactional
    void Should_RotateSpaceProbeRight_When_CommandsIsRotateR() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        Planet planet = createGenericPlanet();
        SpaceProbe spaceProbe = createGenericSpaceProbe(spaceProbeId);

        Position landPosition = new Position(new Coordinates(2, 2), Direction.N);
        spaceProbe.landOnPlanet(planet, landPosition);
        spaceProbeRepository.save(spaceProbe);

        // When
        ChangeSpaceProbePositionRequest request = new ChangeSpaceProbePositionRequest("R");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/spaceProbe/" + spaceProbeId + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        ChangeSpaceProbePositionResponse mappedResponse =
            objectMapper.readValue(result.getResponse().getContentAsString(), ChangeSpaceProbePositionResponse.class);

        // Then
        Assertions.assertEquals(Direction.E, mappedResponse.getDirection());
        Assertions.assertEquals(landPosition.getCoordinates().getY(), mappedResponse.getY());
        Assertions.assertEquals(landPosition.getCoordinates().getX(), mappedResponse.getX());
    }

    @Test
    @Transactional
    void Should_ChangeSpaceProbePosition_When_CommandsAreMoveAndRotate() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        Planet planet = createGenericPlanet();
        SpaceProbe spaceProbe = createGenericSpaceProbe(spaceProbeId);

        Position landPosition = new Position(new Coordinates(3, 3), Direction.E);
        spaceProbe.landOnPlanet(planet, landPosition);
        spaceProbeRepository.save(spaceProbe);

        // When
        ChangeSpaceProbePositionRequest request = new ChangeSpaceProbePositionRequest("MMRMMRMRRML");

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put("/spaceProbe/" + spaceProbeId + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        ChangeSpaceProbePositionResponse mappedResponse =
            objectMapper.readValue(result.getResponse().getContentAsString(), ChangeSpaceProbePositionResponse.class);

        // Then
        Assertions.assertEquals(Direction.N, mappedResponse.getDirection());
        Assertions.assertEquals(5, mappedResponse.getX());
        Assertions.assertEquals(1, mappedResponse.getY());
    }

    @Test
    @Transactional
    void Should_Get422Status_When_SpaceProbeMovementIsNotValid() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        Planet planet = createGenericPlanet();
        SpaceProbe spaceProbe = createGenericSpaceProbe(spaceProbeId);

        Position landPosition = new Position(new Coordinates(3, 3), Direction.E);
        spaceProbe.landOnPlanet(planet, landPosition);
        spaceProbeRepository.save(spaceProbe);

        // When / Then
        ChangeSpaceProbePositionRequest request = new ChangeSpaceProbePositionRequest("MMMMMM");

        mockMvc.perform(MockMvcRequestBuilders.put("/spaceProbe/" + spaceProbeId + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity());
    }

    @Test
    @Transactional
    void Should_Get400Status_When_SpaceProbeIsNotStored() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();

        // When / Then
        ChangeSpaceProbePositionRequest request = new ChangeSpaceProbePositionRequest("MMMMMM");

        mockMvc.perform(MockMvcRequestBuilders.put("/spaceProbe/" + spaceProbeId + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @Transactional
    void Should_Get404Status_When_ProvidedCommandsAreInvalid() throws Exception {
        // Given
        UUID spaceProbeId = UUID.randomUUID();
        SpaceProbe spaceProbe = new SpaceProbe(spaceProbeId);

        // When / Then
        ChangeSpaceProbePositionRequest request = new ChangeSpaceProbePositionRequest("CCCCC");

        mockMvc.perform(MockMvcRequestBuilders.put("/spaceProbe/" + spaceProbeId + "/position")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private Planet createGenericPlanet() {
        Planet planet = new Planet(UUID.randomUUID(), new Coordinates(5, 5));
        return planetRepository.save(planet);
    }

    private SpaceProbe createGenericSpaceProbe(UUID spaceProbeId) {
        SpaceProbe spaceProbe = new SpaceProbe(spaceProbeId);
        return spaceProbeRepository.save(spaceProbe);
    }
}
