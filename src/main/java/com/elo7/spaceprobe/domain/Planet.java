package com.elo7.spaceprobe.domain;


import java.util.*;

public class Planet {
    private final UUID id;
    private final Map<SpaceProbe, Coordinates> occupiedLands;
    private final Coordinates landSize;

    public Planet(Coordinates landSize) {
        this.landSize = landSize;

        this.id = UUID.randomUUID();
        this.occupiedLands = new HashMap<>();
    }

    public void registerLandOccupation(SpaceProbe spaceProbe, Coordinates coordinates) throws Exception {
        if (!isCoordinatesValueValid(coordinates) || !isCoordinatesFree(coordinates)) {
            throw new Exception("This Coordinates is neither available or valid");
        }
        occupiedLands.put(spaceProbe, coordinates);
    }

    public Boolean isSpaceProbeOnLand(SpaceProbe spaceProbe) {
        return occupiedLands.containsKey(spaceProbe);
    }

    private Boolean isCoordinatesFree(Coordinates coordinates) {
        return !this.occupiedLands.containsValue(coordinates);
    }

    private Boolean isCoordinatesValueValid(Coordinates coordinates) {
        return !isAnyCoordinateNegative(coordinates) &&
            (coordinates.getY() <= landSize.getY() && coordinates.getX() <= landSize.getX());
    }

    private Boolean isAnyCoordinateNegative(Coordinates coordinates) {
        return coordinates.getX() < 0 || coordinates.getY() < 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return id.equals(planet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
