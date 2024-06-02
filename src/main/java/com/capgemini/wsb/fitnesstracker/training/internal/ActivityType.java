package com.capgemini.wsb.fitnesstracker.training.internal;

import lombok.Getter;

/**
 * Enum reprezentujący typy aktywności.
 */
@Getter
public enum ActivityType {

    RUNNING("Bieganie"),
    CYCLING("Jazda na rowerze"),
    WALKING("Chodzenie"),
    SWIMMING("Pływanie"),
    TENNIS("Tenis");

    /**
     * Nazwa wyświetlana dla typu aktywności.
     */
    private final String displayName;

    /**
     * Konstruktor przypisujący nazwę wyświetlaną do typu aktywności.
     *
     * @param displayName nazwa wyświetlana dla typu aktywności
     */
    ActivityType(String displayName) {
        this.displayName = displayName;
    }

}
