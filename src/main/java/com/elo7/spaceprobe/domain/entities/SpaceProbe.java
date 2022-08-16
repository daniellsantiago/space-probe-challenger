package com.elo7.spaceprobe.domain.entities;

import com.elo7.spaceprobe.domain.exception.BusinessException;

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

    public SpaceProbe() {
    }

    public SpaceProbe(UUID id, Planet planet, Position position) {
        this.id = id;
        this.planet = planet;
        this.position = position;
    }

    public SpaceProbe(UUID id) {
        this.id = id;
    }

    public void landOnPlanet(Planet planet, Position position) throws BusinessException {
        if (this.planet != null) {
            throw new BusinessException("SpaceProbe is already on Planet");
        }
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
        Coordinates newCoordinates = new Coordinates(getCoordinates().getId(), actualX, actualY);
        switch (direction) {
            case N:
                newCoordinates.changeCoordinates(actualX, actualY + 1);
                break;
            case E:
                newCoordinates.changeCoordinates(actualX + 1, actualY);
                break;
            case S:
                newCoordinates.changeCoordinates(actualX, actualY - 1);
                break;
            case W:
                newCoordinates.changeCoordinates(actualX - 1, actualY);
                break;
            default:
                throw new BusinessException("Given Direction is Unknown");
        }
        this.planet.registerLandOccupation(this, newCoordinates);
        this.setPosition(new Position(newCoordinates, getDirection()));
    }

    public void rotateClockwise() {
        switch (getDirection()) {
            case N:
                this.changeDirection(Direction.E);
                break;
            case E:
                this.changeDirection(Direction.S);
                break;
            case S:
                this.changeDirection(Direction.W);
                break;
            case W:
                this.changeDirection(Direction.N);
                break;
        }
    }

    public void rotateCounterClockwise() {
        switch (getDirection()) {
            case N:
                this.changeDirection(Direction.W);
                break;
            case W:
                this.changeDirection(Direction.S);
                break;
            case S:
                this.changeDirection(Direction.E);
                break;
            case E:
                this.changeDirection(Direction.N);
                break;
        }
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

    private void changeDirection(Direction newDirection) {
        position.setDirection(newDirection);
    }

    private void changeCoordinates(int x, int y) {
        getCoordinates().changeCoordinates(x, y);
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
