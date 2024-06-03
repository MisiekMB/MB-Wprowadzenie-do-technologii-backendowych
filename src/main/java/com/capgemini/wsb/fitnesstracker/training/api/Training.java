package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

/**
 * Klasa reprezentująca trening.
 */
@Entity
@Table(name = "trainings")
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ToString
public class Training {

    /**
     * Unikalny identyfikator treningu.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Użytkownik powiązany z treningiem.
     */
    @Setter
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Czas rozpoczęcia treningu.
     */
    @Setter
    @Column(name = "start_time", nullable = false)
    private Date startTime;

    /**
     * Czas zakończenia treningu.
     */
    @Setter
    @Column(name = "end_time", nullable = false)
    private Date endTime;

    /**
     * Typ aktywności podczas treningu.
     */
    @Setter
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "activity_type", nullable = false)
    private ActivityType activityType;

    /**
     * Przebyty dystans podczas treningu.
     */
    @Setter
    @Column(name = "distance")
    private double distance;

    /**
     * Średnia prędkość podczas treningu.
     */
    @Setter
    @Column(name = "average_speed")
    private double averageSpeed;

    /**
     * Konstruktor tworzący nowy obiekt treningu z wszystkimi polami.
     *
     * @param id          unikalny identyfikator treningu
     * @param user        użytkownik powiązany z treningiem
     * @param startTime   czas rozpoczęcia treningu
     * @param endTime     czas zakończenia treningu
     * @param activityType typ aktywności podczas treningu
     * @param distance    przebyty dystans podczas treningu
     * @param averageSpeed średnia prędkość podczas treningu
     */
    public Training(
            Long id,
            final User user,
            final Date startTime,
            final Date endTime,
            final ActivityType activityType,
            final double distance,
            final double averageSpeed) {
        this.id = id;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.activityType = activityType;
        this.distance = distance;
        this.averageSpeed = averageSpeed;
    }

    /**
     * Konstruktor tworzący nowy obiekt treningu bez identyfikatora.
     *
     * @param user        użytkownik powiązany z treningiem
     * @param startTime   czas rozpoczęcia treningu
     * @param endTime     czas zakończenia treningu
     * @param activityType typ aktywności podczas treningu
     * @param distance    przebyty dystans podczas treningu
     * @param averageSpeed średnia prędkość podczas treningu
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
