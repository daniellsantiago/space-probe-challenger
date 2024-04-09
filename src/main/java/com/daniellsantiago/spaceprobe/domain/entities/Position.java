package com.daniellsantiago.spaceprobe.domain.entities;

import javax.persistence.*;
import java.util.Objects;

@Embeddable
public class Position {

    @ManyToOne(cascade = CascadeType.ALL)
    private Coordinates coordinates;

    @Enumerated(EnumType.STRING)
    private Direction direction;

    public Position() {
    }

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

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
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
