package com.elo7.spaceprobe.domain.repository;

import com.elo7.spaceprobe.domain.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanetRepository extends JpaRepository<Planet, UUID> {
}
