package com.elo7.spaceprobe.domain;


import java.util.*;

public class Planet {
    private final UUID id;
    private final Map<SpaceProbe, Coordinates> landedPlanets;
    private final Coordinates landSize;

    public Planet(UUID id, Coordinates landSize) {
        this.landSize = landSize;
        this.id = id;

        this.landedPlanets = new HashMap<>();
    }

    public void registerLandOccupation(SpaceProbe spaceProbe, Coordinates coordinates) throws Exception {
        if (!isCoordinatesValueValid(coordinates) || !isCoordinatesFree(coordinates)) {
            throw new Exception("This Coordinates is neither available or valid");
        }
        landedPlanets.put(spaceProbe, coordinates);
    }

    public Boolean isSpaceProbeOnLand(SpaceProbe spaceProbe) {
        return landedPlanets.containsKey(spaceProbe);
    }

    public Coordinates getSpaceProbePosition(SpaceProbe spaceProbe) throws Exception {
        if (!isSpaceProbeOnLand(spaceProbe)) {
            throw new Exception("Provided SpaceProbe is not on Planet");
        }
        return landedPlanets.get(spaceProbe);
    }

    private Boolean isCoordinatesFree(Coordinates coordinates) {
        return !this.landedPlanets.containsValue(coordinates);
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
