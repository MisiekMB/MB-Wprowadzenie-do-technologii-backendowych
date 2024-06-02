package com.capgemini.wsb.fitnesstracker.training.api;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs dostarczający metody do zarządzania treningami.
 */
public interface TrainingProvider {

    /**
     * Pobiera trening na podstawie jego ID.
     * Jeśli trening o podanym ID nie zostanie znaleziony, zwrócony zostanie {@link Optional#empty()}.
     *
     * @param trainingId id treningu do wyszukania
     * @return {@link Optional} zawierający znaleziony TrainingDto, lub {@link Optional#empty()} jeśli nie znaleziono
     */
    Optional<TrainingDto> getTraining(Long trainingId);

    /**
     * Pobiera wszystkie treningi.
     *
     * @return Lista obiektów TrainingDto
     */
    List<TrainingDto> getAllTrainings();

    /**
     * Tworzy nowy trening.
     *
     * @param trainingDto DTO treningu do utworzenia
     * @return utworzony TrainingDto
     */
    TrainingDto createTraining(TrainingDto trainingDto);
}
