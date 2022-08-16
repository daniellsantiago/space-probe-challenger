package com.elo7.spaceprobe.application.dto;

import com.elo7.spaceprobe.domain.entities.Coordinates;
import com.elo7.spaceprobe.domain.entities.Direction;

import java.util.UUID;

public class LandSpaceProbeDTO {
    private final UUID spaceProbeId;
    private final UUID planetId;
    private final Coordinates landingCoordinates;
    private final Direction spaceLandingDirection;

    public LandSpaceProbeDTO(UUID spaceProbeId, UUID planetId, Coordinates landingCoordinates, Direction spaceLandingDirection) {
        this.spaceProbeId = spaceProbeId;
        this.planetId = planetId;
        this.landingCoordinates = landingCoordinates;
        this.spaceLandingDirection = spaceLandingDirection;
    }

    public UUID getSpaceProbeId() {
        return spaceProbeId;
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public Coordinates getLandingCoordinates() {
        return landingCoordinates;
    }

    public Direction getSpaceLandingDirection() {
        return spaceLandingDirection;
    }
}
