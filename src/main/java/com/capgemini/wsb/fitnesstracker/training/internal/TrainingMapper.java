package com.capgemini.wsb.fitnesstracker.training.internal;
import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper do konwersji pomiędzy encją Training a DTO TrainingDto.
 */
@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserMapper userMapper;

    /**
     * Konwertuje encję Training na DTO TrainingDto.
     *
     * @param training encja Training
     * @return DTO TrainingDto
     */
    public TrainingDto toDto(Training training) {
        return new TrainingDto(
                training.getId(),
                training.getUser() != null ? userMapper.toDto(training.getUser()) : null,
                training.getStartTime(),
                training.getEndTime(),
                training.getActivityType(),
                training.getDistance(),
                training.getAverageSpeed()
        );
    }
}
