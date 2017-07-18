package com.eliftech.common;

import java.util.UUID;

public class GeneratorUUID {
    public static String generate() {
        return UUID.randomUUID().toString();
    }
}
