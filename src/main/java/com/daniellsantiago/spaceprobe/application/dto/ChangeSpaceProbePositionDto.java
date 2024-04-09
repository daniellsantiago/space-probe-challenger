package com.daniellsantiago.spaceprobe.application.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public class ChangeSpaceProbePositionDto {
    @NotEmpty
    private List<SpaceProbeCommandDto> commands;

    @NotNull
    private UUID spaceProbeId;

    public ChangeSpaceProbePositionDto(List<SpaceProbeCommandDto> commands, UUID spaceProbeId) {
        this.commands = commands;
        this.spaceProbeId = spaceProbeId;
    }

    public List<SpaceProbeCommandDto> getCommands() {
        return commands;
    }

    public void setCommands(List<SpaceProbeCommandDto> commands) {
        this.commands = commands;
    }

    public UUID getSpaceProbeId() {
        return spaceProbeId;
    }

    public void setSpaceProbeId(UUID spaceProbeId) {
        this.spaceProbeId = spaceProbeId;
    }
}
