package com.daniellsantiago.spaceprobe.application.dto;

import com.daniellsantiago.spaceprobe.domain.entities.Direction;

public class SpaceProbePositionDto {
    private int x;
    private int y;
    private Direction direction;

    public SpaceProbePositionDto(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
