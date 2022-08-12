package com.elo7.spaceprobe.domain;

import java.util.Objects;
import java.util.UUID;

public class SpaceProbe {
    private final UUID id;
    private final Planet planet;
    private Coordinates position;
    private Boolean isLanded;

    public SpaceProbe(Planet planet, Coordinates position) {
        this.planet = planet;
        this.position = position;

        this.isLanded = false;
        this.id = UUID.randomUUID();
    }

    public void land(Coordinates coordinates) throws Exception {
        if (isLanded) {
            throw new Exception("SpaceProbe is already on planet");
        }

        this.planet.registerLandOccupation(this, coordinates);
        this.position = coordinates;
        this.isLanded = true;
    }

    public Boolean isLanded() {
        return isLanded;
    }

    public Coordinates getPosition() {
        return position;
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
