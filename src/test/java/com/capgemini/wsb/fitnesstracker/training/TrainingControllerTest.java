package com.capgemini.wsb.fitnesstracker.training;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.*;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Klasa testowa dla TrainingController.
 */
public class TrainingControllerTest {

    @Mock
    private TrainingServiceImpl trainingService;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private TrainingController trainingController;

    /**
     * Inicjalizacja mocków przed każdym testem.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Testuje metodę getAllTrainings().
     */
    @Test
    void testGetAllTrainings() {
        Training training = new Training();
        TrainingDto trainingDto = new TrainingDto();

        when(trainingService.getAllTrainings()).thenReturn(List.of(training));
        when(trainingMapper.toTrainingDto(training)).thenReturn(trainingDto);

        List<TrainingDto> result = trainingController.getAllTrainings();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingService, times(1)).getAllTrainings();
        verify(trainingMapper, times(1)).toTrainingDto(training);
    }

    /**
     * Testuje metodę getTrainingsByUserId(Long userId).
     */
    @Test
    void testGetTrainingsByUserId() {
        Long userId = 1L;
        Training training = new Training();
        TrainingDto trainingDto = new TrainingDto();

        when(trainingService.getTrainingsByUserId(userId)).thenReturn(List.of(training));
        when(trainingMapper.toTrainingDto(training)).thenReturn(trainingDto);

        List<TrainingDto> result = trainingController.getTrainingsByUserId(userId);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingService, times(1)).getTrainingsByUserId(userId);
        verify(trainingMapper, times(1)).toTrainingDto(training);
    }

    /**
     * Testuje metodę getTrainingsByEndDateAfter(Date date).
     */
    @Test
    void testGetTrainingsByEndDateAfter() {
        Date date = new Date();
        Training training = new Training();
        TrainingDto trainingDto = new TrainingDto();

        when(trainingService.getTrainingsByEndDateAfter(date)).thenReturn(List.of(training));
        when(trainingMapper.toTrainingDto(training)).thenReturn(trainingDto);

        List<TrainingDto> result = trainingController.getTrainingsByEndDateAfter(date);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingService, times(1)).getTrainingsByEndDateAfter(date);
        verify(trainingMapper, times(1)).toTrainingDto(training);
    }

    /**
     * Testuje metodę getTrainingsByActivityType(ActivityType activityType).
     */
    @Test
    void testGetTrainingsByActivityType() {
        ActivityType activityType = ActivityType.RUNNING;
        Training training = new Training();
        TrainingDto trainingDto = new TrainingDto();

        when(trainingService.getTrainingsByActivityType(activityType)).thenReturn(List.of(training));
        when(trainingMapper.toTrainingDto(training)).thenReturn(trainingDto);

        List<TrainingDto> result = trainingController.getTrainingsByActivityType(activityType);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(trainingService, times(1)).getTrainingsByActivityType(activityType);
        verify(trainingMapper, times(1)).toTrainingDto(training);
    }

    /**
     * Testuje metodę createTraining(TrainingSupportDto trainingSupportDto).
     */
    @Test
    void testCreateTraining() {
        TrainingSupportDto trainingSupportDto = new TrainingSupportDto();
        trainingSupportDto.setUserId(1L);
        TrainingDto trainingDto = new TrainingDto();
        User user = new User();
        Training training = new Training();

        when(userService.getUser(trainingSupportDto.getUserId())).thenReturn(Optional.of(user));
        when(trainingMapper.toTrainingFromTrainingSupportDto(trainingSupportDto)).thenReturn(trainingDto);
        when(trainingMapper.toTrainingEntity(trainingDto)).thenReturn(training);
        when(trainingService.createTraining(training)).thenReturn(training);
        when(trainingMapper.toTrainingDto(training)).thenReturn(trainingDto);

        ResponseEntity<TrainingDto> response = trainingController.createTraining(trainingSupportDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(trainingDto, response.getBody());
        verify(userService, times(1)).getUser(trainingSupportDto.getUserId());
        verify(trainingMapper, times(1)).toTrainingFromTrainingSupportDto(trainingSupportDto);
        verify(trainingMapper, times(1)).toTrainingEntity(trainingDto);
        verify(trainingService, times(1)).createTraining(training);
        verify(trainingMapper, times(1)).toTrainingDto(training);
    }

    /**
     * Testuje metodę createTraining(TrainingSupportDto trainingSupportDto) w przypadku, gdy użytkownik nie został znaleziony.
     */
    @Test
    void testCreateTrainingUserNotFound() {
        TrainingSupportDto trainingSupportDto = new TrainingSupportDto();
        trainingSupportDto.setUserId(1L);

        when(userService.getUser(trainingSupportDto.getUserId())).thenReturn(Optional.empty());

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
            trainingController.createTraining(trainingSupportDto);
        });

        assertEquals("Użytkownik nie istnieje.", thrown.getMessage());

        verify(userService, times(1)).getUser(trainingSupportDto.getUserId());
        verify(trainingMapper, never()).toTrainingFromTrainingSupportDto(any());
        verify(trainingMapper, never()).toTrainingEntity(any());
        verify(trainingService, never()).createTraining(any());
    }

    /**
     * Testuje metodę updateTraining(TrainingDto trainingDto, Long trainingId).
     */
    @Test
    void testUpdateTraining() {
        Long trainingId = 1L;
        TrainingDto trainingDto = new TrainingDto();
        Training training = new Training();

        when(trainingService.updateTraining(trainingId, trainingDto)).thenReturn(training);
        when(trainingMapper.toTrainingDto(training)).thenReturn(trainingDto);

        TrainingDto result = trainingController.updateTraining(trainingDto, trainingId);

        assertNotNull(result);
        assertEquals(trainingDto, result);
        verify(trainingService, times(1)).updateTraining(trainingId, trainingDto);
        verify(trainingMapper, times(1)).toTrainingDto(training);
    }

    /**
     * Testuje metodę handleUserNotFoundException(UserNotFoundException ex).
     */
    @Test
    void testHandleUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException("Użytkownik nie istnieje.");

        ResponseEntity<String> response = trainingController.handleUserNotFoundException(ex);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Użytkownik nie istnieje.", response.getBody());
    }
}
