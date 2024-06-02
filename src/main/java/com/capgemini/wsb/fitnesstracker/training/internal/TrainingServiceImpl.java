package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingProvider {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;
    private final UserRepository userRepository;

    @Override
    public Optional<TrainingDto> getTraining(Long trainingId) {
        return trainingRepository.findById(trainingId)
                .map(trainingMapper::toDto);
    }

    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findByUserId(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getTrainingsByEndDateAfter(Date endDate) {
        return trainingRepository.findByEndTimeAfter(endDate).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<TrainingDto> getTrainingsByActivityType(ActivityType activityType) {
        return trainingRepository.findByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    public TrainingDto createTraining(TrainingDto trainingDto) {
        Training training = trainingMapper.toEntity(trainingDto);
        Training savedTraining = trainingRepository.save(training);
        return trainingMapper.toDto(savedTraining);
    }

    public TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto) {
        Optional<Training> existingTrainingOpt = trainingRepository.findById(trainingId);
        if (existingTrainingOpt.isEmpty()) {
            throw new IllegalArgumentException("Training not found");
        }

        Training existingTraining = existingTrainingOpt.get();
        if (trainingDto.userId() != null) {   // Ensure correct access to userId
            User user = userRepository.findById(trainingDto.userId())
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            existingTraining.setUser(user);
        }
        if (trainingDto.startTime() != null) {
            existingTraining.setStartTime(trainingDto.startTime());
        }
        if (trainingDto.endTime() != null) {
            existingTraining.setEndTime(trainingDto.endTime());
        }
        if (trainingDto.activityType() != null) {
            existingTraining.setActivityType(trainingDto.activityType());
        }
        if (trainingDto.distance() != 0) {
            existingTraining.setDistance(trainingDto.distance());
        }
        if (trainingDto.averageSpeed() != 0) {
            existingTraining.setAverageSpeed(trainingDto.averageSpeed());
        }

        Training updatedTraining = trainingRepository.save(existingTraining);
        return trainingMapper.toDto(updatedTraining);
    }
}
