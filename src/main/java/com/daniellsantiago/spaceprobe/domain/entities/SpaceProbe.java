package com.daniellsantiago.spaceprobe.domain.entities;

import com.daniellsantiago.spaceprobe.domain.exception.BusinessException;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

    protected SpaceProbe(UUID id, Planet planet, Position position) {
        this.id = id;
        this.planet = planet;
        this.position = position;
    }

    protected SpaceProbe() {
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

        int actualX = getCoordinates().getX();
        int actualY = getCoordinates().getY();
        switch (direction) {
            case N:
                changeCoordinates(actualX, actualY + 1);
                break;
            case E:
                changeCoordinates(actualX + 1, actualY);
                break;
            case S:
                changeCoordinates(actualX, actualY - 1);
                break;
            case W:
                changeCoordinates(actualX - 1, actualY);
                break;
            default:
                throw new BusinessException("Given Direction is Unknown");
        }
        this.planet.registerLandOccupation(this, getCoordinates());
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

    private Boolean isLanded() {
        return this.planet != null;
    }

    private void changeDirection(Direction newDirection) {
        this.position = new Position(getCoordinates(), newDirection);
    }

    private void changeCoordinates(int x, int y) {
        Coordinates newCoordinates = new Coordinates(getCoordinates().getId(), x, y);
        this.position = new Position(newCoordinates, getDirection());
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
