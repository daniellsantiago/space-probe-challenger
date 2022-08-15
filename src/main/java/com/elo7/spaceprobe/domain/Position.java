package com.elo7.spaceprobe.domain;

import java.util.Objects;

public class Position {
    private final Coordinates coordinates;
    private final Direction direction;

    public Position(Coordinates coordinates, Direction direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return coordinates.equals(position.coordinates) && direction == position.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates, direction);
    }
}
