package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

/**
 * Interfejs repozytorium do zarządzania sesjami treningowymi.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Znajduje wszystkie sesje treningowe, które zakończyły się po podanej dacie.
     *
     * @param endDate data, po której sesje treningowe powinny być zakończone
     * @return lista sesji treningowych zakończonych po podanej dacie
     */
    List<Training> findByEndTimeAfter(Date endDate);

    /**
     * Znajduje wszystkie sesje treningowe dla danego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista sesji treningowych dla danego użytkownika
     */
    @Query("SELECT t FROM Training t WHERE t.user.id = :userId")
    List<Training> findByUserId(@Param("userId") Long userId);

    /**
     * Znajduje wszystkie sesje treningowe dla danego typu aktywności.
     *
     * @param activityType typ aktywności
     * @return lista sesji treningowych dla danego typu aktywności
     */
    List<Training> findByActivityType(ActivityType activityType);
}
