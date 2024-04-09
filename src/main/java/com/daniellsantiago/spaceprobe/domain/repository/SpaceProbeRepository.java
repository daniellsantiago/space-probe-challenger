package com.daniellsantiago.spaceprobe.domain.repository;

import com.daniellsantiago.spaceprobe.domain.entities.SpaceProbe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpaceProbeRepository extends JpaRepository<SpaceProbe, UUID> {

}
