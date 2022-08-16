package com.elo7.spaceprobe.domain.repository;

import com.elo7.spaceprobe.domain.entities.SpaceProbe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpaceProbeRepository extends JpaRepository<SpaceProbe, UUID> {

}
