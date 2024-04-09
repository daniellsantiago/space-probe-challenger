package com.daniellsantiago.spaceprobe.domain.repository;

import com.daniellsantiago.spaceprobe.domain.entities.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanetRepository extends JpaRepository<Planet, UUID> {
}
