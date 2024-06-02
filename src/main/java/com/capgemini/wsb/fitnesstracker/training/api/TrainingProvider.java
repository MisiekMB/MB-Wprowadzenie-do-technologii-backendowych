package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.List;
import java.util.Optional;

public interface TrainingProvider {

    /**
     * Retrieves a training based on their ID.
     * If the training with the given ID is not found, then {@link Optional#empty()} will be returned.
     *
     * @param trainingId id of the training to be searched
     * @return An {@link Optional} containing the located TrainingDto, or {@link Optional#empty()} if not found
     */
    Optional<TrainingDto> getTraining(Long trainingId);

    /**
     * Retrieves all trainings.
     *
     * @return List of TrainingDto
     */
    List<TrainingDto> getAllTrainings();

    /**
     * Creates a new training.
     *
     * @param trainingDto the DTO of the training to be created
     * @return the created TrainingDto
     */
    TrainingDto createTraining(TrainingDto trainingDto);
}
