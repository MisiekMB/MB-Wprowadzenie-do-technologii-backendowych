package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {

    /**
     * Finds trainings by user ID.
     *
     * @param userId the ID of the user
     * @return a list of trainings
     */
    List<Training> findByUserId(Long userId);
}
