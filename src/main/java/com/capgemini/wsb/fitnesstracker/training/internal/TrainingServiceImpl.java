package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Implementacja serwisu do zarządzania sesjami treningowymi.
 */
@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;

    /**
     * Zwraca listę wszystkich sesji treningowych.
     *
     * @return lista wszystkich sesji treningowych
     */
    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    /**
     * Zwraca listę sesji treningowych dla danego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista sesji treningowych dla danego użytkownika
     */
    public List<Training> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId);
    }

    /**
     * Zwraca listę sesji treningowych zakończonych po podanej dacie.
     *
     * @param endDate data, po której sesje treningowe powinny być zakończone
     * @return lista sesji treningowych zakończonych po podanej dacie
     */
    public List<Training> getTrainingsByEndDateAfter(Date endDate) {
        return trainingRepository.findByEndTimeAfter(endDate);
    }

    /**
     * Zwraca listę sesji treningowych dla danego typu aktywności.
     *
     * @param activityType typ aktywności
     * @return lista sesji treningowych dla danego typu aktywności
     */
    public List<Training> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType);
    }

    /**
     * Tworzy nową sesję treningową.
     *
     * @param training obiekt sesji treningowej do utworzenia
     * @return utworzona sesja treningowa
     */
    @Override
    @Transactional
    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    /**
     * Aktualizuje istniejącą sesję treningową.
     *
     * @param trainingId identyfikator sesji treningowej do aktualizacji
     * @param trainingDto obiekt DTO zawierający zaktualizowane dane sesji treningowej
     * @return zaktualizowana sesja treningowa
     */
    @Override
    @Transactional
    public Training updateTraining(Long trainingId, TrainingDto trainingDto) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new RuntimeException("Training with ID=%s was not found".formatted(trainingId)));

        if (trainingDto.getUser() != null) {
            training.setUser(trainingDto.getUser());
        }

        if (trainingDto.getStartTime() != null) {
            training.setStartTime(trainingDto.getStartTime());
        }

        if (trainingDto.getEndTime() != null) {
            training.setEndTime(trainingDto.getEndTime());
        }

        if (trainingDto.getActivityType() != null) {
            training.setActivityType(trainingDto.getActivityType());
        }

        if (trainingDto.getDistance() != null) {
            training.setDistance(trainingDto.getDistance());
        }

        if (trainingDto.getAverageSpeed() != null) {
            training.setAverageSpeed(trainingDto.getAverageSpeed());
        }

        return trainingRepository.save(training);
    }
}
