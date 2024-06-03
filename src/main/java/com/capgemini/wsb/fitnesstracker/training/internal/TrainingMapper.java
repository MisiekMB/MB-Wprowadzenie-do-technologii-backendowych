package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Komponent mapujący dane pomiędzy encjami Training, TrainingDto i TrainingSupportDto.
 */
@Component
@RequiredArgsConstructor
public class TrainingMapper {

    /**
     * Serwis użytkownika, używany do zarządzania danymi użytkowników.
     */
    public final UserServiceImpl userService;

    /**
     * Mapuje encję Training na obiekt TrainingDto.
     *
     * @param training encja Training
     * @return obiekt TrainingDto
     */
    public TrainingDto toTrainingDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser(),
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    /**
     * Mapuje obiekt TrainingDto na encję Training.
     *
     * @param trainingDto obiekt TrainingDto
     * @return encja Training
     */
    public Training toTrainingEntity(TrainingDto trainingDto) {
        return new Training(
                trainingDto.getUser(),
                trainingDto.getStartTime(),
                trainingDto.getEndTime(),
                trainingDto.getActivityType(),
                trainingDto.getDistance(),
                trainingDto.getAverageSpeed()
        );
    }

    /**
     * Mapuje obiekt TrainingSupportDto na obiekt TrainingDto.
     *
     * @param trainingSupportDto obiekt TrainingSupportDto
     * @return obiekt TrainingDto
     */
    public TrainingDto toTrainingFromTrainingSupportDto(TrainingSupportDto trainingSupportDto) {
        return new TrainingDto(
                trainingSupportDto.getId(),
                trainingSupportDto.getUser(),
                trainingSupportDto.getStartTime(),
                trainingSupportDto.getEndTime(),
                trainingSupportDto.getActivityType(),
                trainingSupportDto.getDistance(),
                trainingSupportDto.getAverageSpeed()
        );
    }
}

