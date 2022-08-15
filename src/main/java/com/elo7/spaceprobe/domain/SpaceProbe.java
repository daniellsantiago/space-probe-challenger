package com.elo7.spaceprobe.domain;

import java.util.Objects;
import java.util.UUID;

public class SpaceProbe {
    private final UUID id;
    private Planet planet;
    private Position position;

    public SpaceProbe(UUID id) {
        this.id = id;
    }

    public void landOnPlanet(Planet planet, Position position) throws Exception {
        planet.registerLandOccupation(this, position.getCoordinates());
        this.position = position;
        this.planet = planet;
    }

    public void move(Direction direction) throws Exception {
        if (!isLanded()) {
            throw new Exception("SpaceProbe is not on Planet");
        }
        int actualX = position.getCoordinates().getX();
        int actualY = position.getCoordinates().getY();
        Coordinates newCoordinates;
        switch (direction) {
            case N:
                newCoordinates = new Coordinates(actualX, actualY + 1);
                break;
            case E:
                newCoordinates = new Coordinates(actualX + 1, actualY);
                break;
            case S:
                newCoordinates = new Coordinates(actualX, actualY - 1);
                break;
            case W:
                newCoordinates =new Coordinates(actualX - 1, actualY);
                break;
            default:
                throw new Exception("Given Direction is Unknown");
        }
        planet.registerLandOccupation(this, newCoordinates);
        this.position = new Position(newCoordinates, direction);
    }

    public Coordinates getCoordinates() {
        return position.getCoordinates();
    }

    public Direction getDirection() {
        return position.getDirection();
    }

    private Boolean isLanded() {
        return this.planet != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceProbe that = (SpaceProbe) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
