package com.elo7.spaceprobe.domain;

import java.util.Objects;
import java.util.UUID;

public class SpaceProbe {
    private final UUID id;
    private Planet planet;

    public SpaceProbe(UUID id) {
        this.id = id;
    }

    public void landOnPlanet(Planet planet, Coordinates coordinates) throws Exception {
        planet.registerLandOccupation(this, coordinates);
        this.planet = planet;
    }

    public void move(Coordinates coordinates) throws Exception {
        if (!isLanded()) {
            throw new Exception("SpaceProbe is not on Planet");
        }
        planet.registerLandOccupation(this, coordinates);
    }

    public Boolean isLanded() {
        return this.planet != null;
    }

    public Coordinates getPlanetPosition() throws Exception {
        if (!isLanded()) {
            throw new Exception("SpaceProbe is not on Planet");
        }
        return this.planet.getSpaceProbePosition(this);
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
