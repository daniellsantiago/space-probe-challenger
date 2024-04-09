package com.daniellsantiago.spaceprobe.lib;

public interface ApplicationService<P, R> {
    R execute(P payload);
}
