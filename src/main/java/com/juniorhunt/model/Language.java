package com.juniorhunt.model;

public enum Language {
    ENGLISH("Английский"),
    SPANISH("Испанский"),
    RUSSIAN("Русский");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
