package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Repozytorium JPA do zarządzania encjami {@link Training}.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Znajduje wszystkie treningi zakończone po podanej dacie.
     *
     * @param endDate data, po której treningi powinny się zakończyć
     * @return lista treningów zakończonych po podanej dacie
     */
    List<Training> findByEndTimeAfter(Date endDate);

    /**
     * Znajduje wszystkie treningi dla użytkownika o podanym identyfikatorze.
     *
     * @param userId identyfikator użytkownika
     * @return lista treningów dla użytkownika o podanym identyfikatorze
     */
    List<Training> findByUserId(Long userId);

    /**
     * Znajduje wszystkie treningi dla podanego typu aktywności.
     *
     * @param activityType typ aktywności
     * @return lista treningów dla podanego typu aktywności
     */
    List<Training> findByActivityType(ActivityType activityType);
}
