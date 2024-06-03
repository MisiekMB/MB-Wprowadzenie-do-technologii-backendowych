package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Klasa reprezentująca sesję treningową.
 */
@Entity
@Table(name = "trainings")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Training {

    /**
     * Unikalny identyfikator sesji treningowej.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Użytkownik powiązany z tą sesją treningową.
     */
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Czas rozpoczęcia sesji treningowej.
     */
    @Setter
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    /**
     * Czas zakończenia sesji treningowej.
     */
    @Setter
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    /**
     * Rodzaj aktywności wykonywanej podczas sesji treningowej.
     */
    @Setter
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    /**
     * Dystans pokonany podczas sesji treningowej, w kilometrach.
     */
    @Setter
    @Column(name = "distance")
    private double distance;

    /**
     * Średnia prędkość podczas sesji treningowej, w kilometrach na godzinę.
     */
    @Setter
    @Column(name = "average_speed")
    private double averageSpeed;

    /**
     * Konstruktor tworzący nową instancję Training.
     *
     * @param user użytkownik powiązany z sesją treningową
     * @param startTime czas rozpoczęcia sesji treningowej
     * @param endTime czas zakończenia sesji treningowej
     * @param activityType rodzaj aktywności wykonywanej podczas sesji treningowej
     * @param distance dystans pokonany podczas sesji treningowej
     * @param averageSpeed średnia prędkość podczas sesji treningowej
     */
    public Training(
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }
}
