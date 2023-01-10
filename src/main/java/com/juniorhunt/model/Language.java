package com.juniorhunt.model;

public enum Language {
    ENGLISH("Английский"),
    SPANISH("Испанский"),
    RUSSIAN("Русский"),
    GERMANY("Немецкий"),
    FRENCH("Французский");

    private final String name;

    Language(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
