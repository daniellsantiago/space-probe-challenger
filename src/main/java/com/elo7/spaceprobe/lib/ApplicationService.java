package com.elo7.spaceprobe.lib;

public interface ApplicationService<P, R> {
    R execute(P payload);
}
