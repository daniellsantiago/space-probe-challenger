package com.elo7.spaceprobe.application.dto;

import java.util.Arrays;

public enum RotateCommandDto implements SpaceProbeCommandDto {
    L, R;

    public static Boolean exists(String value) {
        return Arrays.stream(values()).anyMatch((command) -> command.name().equals(value));
    }
}
