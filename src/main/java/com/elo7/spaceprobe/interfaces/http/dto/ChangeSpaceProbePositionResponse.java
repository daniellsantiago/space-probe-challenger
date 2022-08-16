package com.elo7.spaceprobe.interfaces.http.dto;

import com.elo7.spaceprobe.application.dto.SpaceProbePositionDto;
import com.elo7.spaceprobe.domain.Direction;

public class ChangeSpaceProbePositionResponse {
    private int x;
    private int y;
    private Direction direction;

    public ChangeSpaceProbePositionResponse() {
    }

    public ChangeSpaceProbePositionResponse(int x, int y, Direction direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public static ChangeSpaceProbePositionResponse fromDto(SpaceProbePositionDto spaceProbePositionDto) {
        return new ChangeSpaceProbePositionResponse(
            spaceProbePositionDto.getX(),
            spaceProbePositionDto.getY(),
            spaceProbePositionDto.getDirection()
        );
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
