package com.daniellsantiago.spaceprobe.interfaces.http.dto;

import com.daniellsantiago.spaceprobe.domain.entities.Coordinates;
import com.daniellsantiago.spaceprobe.domain.entities.Direction;
import com.daniellsantiago.spaceprobe.application.dto.LandSpaceProbeDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public final class LandSpaceProbeRequest {
    @NotNull
    private UUID planetId;

    @Valid
    @NotNull
    private LandSpaceProbeCoordinatesRequest coordinates;

    @NotNull
    private Direction direction;

    public LandSpaceProbeRequest() {
    }

    public LandSpaceProbeRequest(UUID planetId, LandSpaceProbeCoordinatesRequest coordinates, Direction direction) {
        this.planetId = planetId;
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public LandSpaceProbeDTO toDto(UUID spaceProbeId) {
        return new LandSpaceProbeDTO(
            spaceProbeId,
            planetId,
            new Coordinates(coordinates.getX(), coordinates.getY()),
            direction
        );
    }

    public UUID getPlanetId() {
        return planetId;
    }

    public void setPlanetId(UUID planetId) {
        this.planetId = planetId;
    }

    public LandSpaceProbeCoordinatesRequest getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LandSpaceProbeCoordinatesRequest coordinates) {
        this.coordinates = coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public static class LandSpaceProbeCoordinatesRequest {
        @NotNull
        @Min(value = 0)
        private int x;

        @NotNull
        @Min(value = 0)
        private int y;

        public LandSpaceProbeCoordinatesRequest() {
        }

        public LandSpaceProbeCoordinatesRequest(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
