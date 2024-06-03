package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingProvider;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    private Training training;
    private TrainingDto trainingDto;

    @BeforeEach
    void setUp() {
        training = new Training();
        training.setId(1L);
        training.setStartTime(new Date());
        training.setEndTime(new Date());
        training.setActivityType(ActivityType.RUNNING);
        training.setDistance(10.0);
        training.setAverageSpeed(8.0);

        trainingDto = new TrainingDto();
        trainingDto.setId(1L);
        trainingDto.setStartTime(new Date());
        trainingDto.setEndTime(new Date());
        trainingDto.setActivityType(ActivityType.RUNNING);
        trainingDto.setDistance(10.0);
        trainingDto.setAverageSpeed(8.0);
    }

    @Test
    void shouldGetAllTrainings() {
        when(trainingRepository.findAll()).thenReturn(Collections.singletonList(training));

        List<Training> trainings = trainingService.getAllTrainings();

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        verify(trainingRepository, times(1)).findAll();
    }

    @Test
    void shouldGetTrainingsByUserId() {
        when(trainingRepository.findByUserId(1L)).thenReturn(Collections.singletonList(training));

        List<Training> trainings = trainingService.getTrainingsByUserId(1L);

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        verify(trainingRepository, times(1)).findByUserId(1L);
    }

    @Test
    void shouldGetTrainingsByEndDateAfter() {
        Date date = new Date();
        when(trainingRepository.findByEndTimeAfter(date)).thenReturn(Collections.singletonList(training));

        List<Training> trainings = trainingService.getTrainingsByEndDateAfter(date);

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        verify(trainingRepository, times(1)).findByEndTimeAfter(date);
    }

    @Test
    void shouldGetTrainingsByActivityType() {
        when(trainingRepository.findByActivityType(ActivityType.RUNNING)).thenReturn(Collections.singletonList(training));

        List<Training> trainings = trainingService.getTrainingsByActivityType(ActivityType.RUNNING);

        assertNotNull(trainings);
        assertEquals(1, trainings.size());
        verify(trainingRepository, times(1)).findByActivityType(ActivityType.RUNNING);
    }

    @Test
    void shouldCreateTraining() {
        when(trainingRepository.save(training)).thenReturn(training);

        Training createdTraining = trainingService.createTraining(training);

        assertNotNull(createdTraining);
        assertEquals(training.getId(), createdTraining.getId());
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void shouldUpdateTraining() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.of(training));
        when(trainingRepository.save(training)).thenReturn(training);

        Training updatedTraining = trainingService.updateTraining(1L, trainingDto);

        assertNotNull(updatedTraining);
        assertEquals(training.getId(), updatedTraining.getId());
        verify(trainingRepository, times(1)).findById(1L);
        verify(trainingRepository, times(1)).save(training);
    }

    @Test
    void shouldThrowExceptionWhenTrainingNotFound() {
        when(trainingRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            trainingService.updateTraining(1L, trainingDto);
        });

        assertEquals("Training with ID=1 was not found", exception.getMessage());
        verify(trainingRepository, times(1)).findById(1L);
        verify(trainingRepository, times(0)).save(training);
    }
}
