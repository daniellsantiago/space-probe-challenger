package com.elo7.spaceprobe.domain;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class SpaceProbe {
    @Id
    private UUID id = UUID.randomUUID();

    @ManyToOne
    private Planet planet;

    @Embedded
    private Position position;

    public SpaceProbe(UUID id) {
        this.id = id;
    }

    public void landOnPlanet(Planet planet, Position position) throws BusinessException {
        planet.registerLandOccupation(this, position.getCoordinates());
        this.position = position;
        this.planet = planet;
    }

    public void move(Direction direction) throws BusinessException {
        if (!isLanded()) {
            throw new BusinessException("SpaceProbe is not on Planet");
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
                throw new BusinessException("Given Direction is Unknown");
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Planet getPlanet() {
        return planet;
    }

    public void setPlanet(Planet planet) {
        this.planet = planet;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
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
