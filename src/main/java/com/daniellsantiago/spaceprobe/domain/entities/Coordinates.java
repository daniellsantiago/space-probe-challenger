package com.daniellsantiago.spaceprobe.domain.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Coordinates {
    @Id
    private UUID id = UUID.randomUUID();

    private int x;

    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected Coordinates(UUID id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    protected Coordinates() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
