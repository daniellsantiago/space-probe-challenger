package com.daniellsantiago.spaceprobe.interfaces.http.dto;

import com.daniellsantiago.spaceprobe.application.dto.ChangeSpaceProbePositionDto;
import com.daniellsantiago.spaceprobe.application.dto.MoveCommandDto;
import com.daniellsantiago.spaceprobe.application.dto.RotateCommandDto;
import com.daniellsantiago.spaceprobe.application.dto.SpaceProbeCommandDto;

import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ChangeSpaceProbePositionRequest {
    @NotEmpty
    private String commands;

    public ChangeSpaceProbePositionRequest() {
    }

    public ChangeSpaceProbePositionRequest(String commands) {
        this.commands = commands;
    }

    public ChangeSpaceProbePositionDto toDto(UUID spaceProbeId) {
        List<SpaceProbeCommandDto> mappedCommands = commands.chars()
            .mapToObj(intCommand -> {
                String command = String.valueOf((char) intCommand).toUpperCase();
                if (MoveCommandDto.exists(command)) {
                    return MoveCommandDto.valueOf(command);
                } else if (RotateCommandDto.exists(command)) {
                    return RotateCommandDto.valueOf(command);
                }
                throw new ValidationException(command + " is not a valid Command");
            }).collect(Collectors.toList());
        return new ChangeSpaceProbePositionDto(mappedCommands, spaceProbeId);
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }
}
