package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingMapperTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingMapper trainingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test sprawdza, czy metoda toDto zwraca poprawny obiekt TrainingDto.
     */
    @Test
    void toDto_shouldReturnTrainingDto() {
        // Utwórz obiekt User za pomocą metody fabrycznej createTestUser
        User user = User.createTestUser(1L, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training training = new Training(1L, user, null, null, ActivityType.RUNNING, 10, 5);

        // Konwertuj obiekt Training na TrainingDto
        TrainingDto trainingDto = trainingMapper.toDto(training);

        // Zweryfikuj wynik
        assertEquals(training.getId(), trainingDto.id());
        assertEquals(training.getUser().getId(), trainingDto.userId());
        assertEquals(training.getStartTime(), trainingDto.startTime());
        assertEquals(training.getEndTime(), trainingDto.endTime());
        assertEquals(training.getActivityType(), trainingDto.activityType());
        assertEquals(training.getDistance(), trainingDto.distance());
        assertEquals(training.getAverageSpeed(), trainingDto.averageSpeed());
    }

    /**
     * Test sprawdza, czy metoda toEntity zwraca poprawny obiekt Training.
     */
    @Test
    void toEntity_shouldReturnTraining() {
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(null, userId, null, null, ActivityType.RUNNING, 10, 5);
        User user = User.createTestUser(userId, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        // Skonfiguruj mock userRepository, aby zwracał użytkownika
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Konwertuj obiekt TrainingDto na Training
        Training training = trainingMapper.toEntity(trainingDto);

        // Zweryfikuj wynik
        assertEquals(trainingDto.id(), training.getId());
        assertEquals(user, training.getUser());
        assertEquals(trainingDto.startTime(), training.getStartTime());
        assertEquals(trainingDto.endTime(), training.getEndTime());
        assertEquals(trainingDto.activityType(), training.getActivityType());
        assertEquals(trainingDto.distance(), training.getDistance());
        assertEquals(trainingDto.averageSpeed(), training.getAverageSpeed());
    }

    /**
     * Test sprawdza, czy metoda toEntity rzuca wyjątek, gdy użytkownik nie został znaleziony.
     */
    @Test
    void toEntity_shouldThrowException_whenUserNotFound() {
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(null, userId, null, null, ActivityType.RUNNING, 10, 5);

        // Skonfiguruj mock userRepository, aby zwracał pustą wartość
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Wykonaj metodę i zweryfikuj wynik
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingMapper.toEntity(trainingDto);
        });

        assertEquals("Nie znaleziono użytkownika", exception.getMessage());
    }
}
