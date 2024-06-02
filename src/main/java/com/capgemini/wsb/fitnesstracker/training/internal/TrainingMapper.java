package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Komponent mapujący obiekty {@link Training} na {@link TrainingDto} i odwrotnie.
 */
@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserRepository userRepository;

    /**
     * Konwertuje obiekt {@link Training} na {@link TrainingDto}.
     *
     * @param training obiekt treningu do konwersji
     * @return obiekt TrainingDto
     */
    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser() != null ? training.getUser().getId() : null,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }

    /**
     * Konwertuje obiekt {@link TrainingDto} na {@link Training}.
     *
     * @param trainingDto obiekt TrainingDto do konwersji
     * @return obiekt Training
     */
    public Training toEntity(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Nie znaleziono użytkownika"));

        return new Training(
                trainingDto.id(),
                user,
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.averageSpeed()
        );
    }
}
