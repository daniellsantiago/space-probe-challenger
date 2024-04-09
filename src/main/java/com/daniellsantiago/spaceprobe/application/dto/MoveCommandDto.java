package com.daniellsantiago.spaceprobe.application.dto;

import java.util.Arrays;

public enum MoveCommandDto implements SpaceProbeCommandDto {
    M;

    public static Boolean exists(String value) {
        return Arrays.stream(values()).anyMatch((command) -> command.name().equals(value));
    }
}
