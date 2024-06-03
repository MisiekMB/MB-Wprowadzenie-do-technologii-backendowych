package com.capgemini.wsb.fitnesstracker.training;

import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingSupportDto;
import com.capgemini.wsb.fitnesstracker.user.internal.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

/**
 * Klasa testowa dla TrainingMapper.
 */
public class TrainingMapperTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private TrainingMapper trainingMapper;

    /**
     * Inicjalizacja mocków przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Testuje mapowanie obiektu Training na TrainingDto.
     */
    @Test
    void testToTrainingDto() {
        Training training = new Training();
        training.setId(1L);
        training.setStartTime(new Date());
        training.setEndTime(new Date());
        training.setActivityType(ActivityType.RUNNING);
        training.setDistance(5.0);
        training.setAverageSpeed(10.0);

        TrainingDto trainingDto = trainingMapper.toTrainingDto(training);

        assertNotNull(trainingDto);
        assertEquals(training.getId(), trainingDto.getId());
        assertEquals(training.getStartTime(), trainingDto.getStartTime());
        assertEquals(training.getEndTime(), trainingDto.getEndTime());
        assertEquals(training.getActivityType(), trainingDto.getActivityType());
        assertEquals(training.getDistance(), trainingDto.getDistance());
        assertEquals(training.getAverageSpeed(), trainingDto.getAverageSpeed());
    }

    /**
     * Testuje mapowanie obiektu TrainingDto na Training.
     */
    @Test
    void testToTrainingEntity() {
        TrainingDto trainingDto = new TrainingDto();
        trainingDto.setStartTime(new Date());
        trainingDto.setEndTime(new Date());
        trainingDto.setActivityType(ActivityType.RUNNING);
        trainingDto.setDistance(5.0);
        trainingDto.setAverageSpeed(10.0);

        Training training = trainingMapper.toTrainingEntity(trainingDto);

        assertNotNull(training);
        assertEquals(trainingDto.getStartTime(), training.getStartTime());
        assertEquals(trainingDto.getEndTime(), training.getEndTime());
        assertEquals(trainingDto.getActivityType(), training.getActivityType());
        assertEquals(trainingDto.getDistance(), training.getDistance());
        assertEquals(trainingDto.getAverageSpeed(), training.getAverageSpeed());
    }

    /**
     * Testuje mapowanie obiektu TrainingSupportDto na TrainingDto.
     */
    @Test
    void testToTrainingFromTrainingSupportDto() {
        TrainingSupportDto trainingSupportDto = new TrainingSupportDto();
        trainingSupportDto.setStartTime(new Date());
        trainingSupportDto.setEndTime(new Date());
        trainingSupportDto.setActivityType(ActivityType.RUNNING);
        trainingSupportDto.setDistance(5.0);
        trainingSupportDto.setAverageSpeed(10.0);

        TrainingDto trainingDto = trainingMapper.toTrainingFromTrainingSupportDto(trainingSupportDto);

        assertNotNull(trainingDto);
        assertEquals(trainingSupportDto.getStartTime(), trainingDto.getStartTime());
        assertEquals(trainingSupportDto.getEndTime(), trainingDto.getEndTime());
        assertEquals(trainingSupportDto.getActivityType(), trainingDto.getActivityType());
        assertEquals(trainingSupportDto.getDistance(), trainingDto.getDistance());
        assertEquals(trainingSupportDto.getAverageSpeed(), trainingDto.getAverageSpeed());
    }
}
