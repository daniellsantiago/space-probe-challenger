package com.daniellsantiago.spaceprobe.domain.entities;


import com.daniellsantiago.spaceprobe.domain.exception.BusinessException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.UniqueConstraint;
import java.util.*;

@Entity
public class Planet {

    @Id
    private UUID id = UUID.randomUUID();

    @ManyToMany
    @JoinTable(
        name = "planet_space_probe_landings",
        joinColumns = @JoinColumn(name = "planet_id"),
        inverseJoinColumns = @JoinColumn(name = "coordinates_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = {"planet_id", "coordinates_id"})
    )
    private Map<SpaceProbe, Coordinates> landedPlanets;

    @OneToOne(cascade = CascadeType.ALL)
    private Coordinates landSize;

    public Planet(UUID id, Coordinates landSize) {
        this.landSize = landSize;
        this.id = id;

        this.landedPlanets = new HashMap<>();
    }

    protected Planet(UUID id, Map<SpaceProbe, Coordinates> landedPlanets, Coordinates landSize) {
        this.id = id;
        this.landedPlanets = landedPlanets;
        this.landSize = landSize;
    }

    protected Planet() {
    }

    public void registerLandOccupation(SpaceProbe spaceProbe, Coordinates coordinates) throws BusinessException {
        if (!isCoordinatesValueValid(coordinates) || !isCoordinatesFree(coordinates)) {
            throw new BusinessException("This Coordinates is neither available or valid");
        }

        landedPlanets.put(spaceProbe, coordinates);
    }

    public Boolean isSpaceProbeOnLand(SpaceProbe spaceProbe) {
        return landedPlanets.containsKey(spaceProbe);
    }

    public Coordinates getSpaceProbeCoordinates(SpaceProbe spaceProbe) {
        if (!isSpaceProbeOnLand(spaceProbe)) {
            throw new RuntimeException("Provided SpaceProbe is not on Planet");
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

    public Map<SpaceProbe, Coordinates> getLandedPlanets() {
        return landedPlanets;
    }
}
