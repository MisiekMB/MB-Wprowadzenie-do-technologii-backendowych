package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Klasa DTO reprezentująca dane sesji treningowej.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TrainingDto {

    /**
     * Unikalny identyfikator sesji treningowej.
     */
    private Long id;

    /**
     * Użytkownik powiązany z tą sesją treningową.
     * Może być null.
     */
    @Nullable
    private User user;

    /**
     * Czas rozpoczęcia sesji treningowej.
     */
    private Date startTime;

    /**
     * Czas zakończenia sesji treningowej.
     */
    private Date endTime;

    /**
     * Rodzaj aktywności wykonywanej podczas sesji treningowej.
     */
    private ActivityType activityType;

    /**
     * Dystans pokonany podczas sesji treningowej, w kilometrach.
     */
    private Double distance;

    /**
     * Średnia prędkość podczas sesji treningowej, w kilometrach na godzinę.
     */
    private Double averageSpeed;
}

