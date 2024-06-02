package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper do konwersji pomiędzy encją Training a DTO TrainingDto.
 */
@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserRepository userRepository;

    /**
     * Konwertuje encję Training na DTO TrainingDto.
     *
     * @param training encja Training
     * @return DTO TrainingDto
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


    public Training toEntity(TrainingDto trainingDto) {
        User user = userRepository.findById(trainingDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return new Training(
                user,
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.averageSpeed()
        );
    }
}
