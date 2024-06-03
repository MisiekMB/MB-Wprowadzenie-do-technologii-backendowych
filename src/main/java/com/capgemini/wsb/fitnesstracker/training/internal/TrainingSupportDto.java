package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Klasa DTO wspierająca, używana do przekazywania danych sesji treningowej.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingSupportDto {

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
     * Identyfikator użytkownika powiązanego z tą sesją treningową.
     */
    private Long userId;

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
