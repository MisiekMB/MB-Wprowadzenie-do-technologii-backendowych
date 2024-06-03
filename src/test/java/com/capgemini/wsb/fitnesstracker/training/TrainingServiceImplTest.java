package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingMapper;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainingServiceImplTest {

    @Mock
    private TrainingRepository trainingRepository;

    @Mock
    private TrainingMapper trainingMapper;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test sprawdza, czy metoda getTraining zwraca TrainingDto, gdy trening istnieje.
     */
    @Test
    void getTraining_shouldReturnTrainingDto_whenTrainingExists() {
        Long trainingId = 1L;
        Training training = mock(Training.class);
        TrainingDto trainingDto = mock(TrainingDto.class);

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        // Wykonaj metodę
        Optional<TrainingDto> result = trainingService.getTraining(trainingId);

        // Zweryfikuj wynik
        assertTrue(result.isPresent());
        assertEquals(trainingDto, result.get());
    }

    /**
     * Test sprawdza, czy metoda getAllTrainings zwraca listę TrainingDto.
     */
    @Test
    void getAllTrainings_shouldReturnListOfTrainingDtos() {
        Training training = mock(Training.class);
        TrainingDto trainingDto = mock(TrainingDto.class);

        // Skonfiguruj mocki
        when(trainingRepository.findAll()).thenReturn(Collections.singletonList(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getAllTrainings();

        // Zweryfikuj wynik
        assertEquals(1, result.size());
        assertEquals(trainingDto, result.get(0));
    }

    /**
     * Test sprawdza, czy metoda createTraining zwraca utworzony TrainingDto.
     */
    @Test
    void createTraining_shouldReturnCreatedTrainingDto() {
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(null, userId, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        Training training = mock(Training.class);
        Training savedTraining = mock(Training.class);
        TrainingDto savedTrainingDto = mock(TrainingDto.class);
        User user = User.createTestUser(userId, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");

        // Skonfiguruj mocki
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trainingMapper.toEntity(trainingDto)).thenReturn(training);
        when(trainingRepository.save(training)).thenReturn(savedTraining);
        when(trainingMapper.toDto(savedTraining)).thenReturn(savedTrainingDto);

        // Wykonaj metodę
        TrainingDto result = trainingService.createTraining(trainingDto);

        // Zweryfikuj wynik
        assertEquals(savedTrainingDto, result);
    }

    /**
     * Test sprawdza, czy metoda createTraining rzuca wyjątek, gdy użytkownik nie został znaleziony.
     */
    @Test
    void createTraining_shouldThrowException_whenUserNotFound() {
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(null, userId, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.createTraining(trainingDto);
        });

        assertEquals("Użytkownik nie został znaleziony", exception.getMessage());
    }

    /**
     * Test sprawdza, czy metoda updateTraining rzuca wyjątek, gdy trening nie został znaleziony.
     */
    @Test
    void updateTraining_shouldThrowException_whenTrainingNotFound() {
        Long trainingId = 1L;
        TrainingDto trainingDto = mock(TrainingDto.class);

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        // Wykonaj metodę i zweryfikuj wynik
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.updateTraining(trainingId, trainingDto);
        });

        assertEquals("Trening nie został znaleziony", exception.getMessage());
    }

    /**
     * Test sprawdza, czy metoda updateTraining rzuca wyjątek, gdy użytkownik nie został znaleziony.
     */
    @Test
    void updateTraining_shouldThrowException_whenUserNotFound() {
        Long trainingId = 1L;
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(trainingId, userId, null, null, ActivityType.RUNNING, 10.0, 5.0);

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(new Training()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Wykonaj metodę i zweryfikuj wynik
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.updateTraining(trainingId, trainingDto);
        });

        assertEquals("Użytkownik nie został znaleziony", exception.getMessage());
    }

    /**
     * Test sprawdza, czy metoda updateTraining zwraca zaktualizowany TrainingDto, gdy trening istnieje.
     */
    @Test
    void updateTraining_shouldReturnUpdatedTrainingDto_whenTrainingExists() {
        Long trainingId = 1L;
        Long userId = 1L;

        // Utwórz mock TrainingDto
        TrainingDto trainingDto = new TrainingDto(null, userId, null, null, ActivityType.RUNNING, 10, 5);

        // Utwórz mock istniejącego treningu
        User user = User.createTestUser(userId, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        Training existingTraining = new Training(trainingId, user, null, null, ActivityType.RUNNING, 10, 5);

        // Utwórz mock zaktualizowanego treningu
        Training updatedTraining = new Training(trainingId, user, null, null, ActivityType.RUNNING, 10, 5);

        // Utwórz mock zaktualizowanego TrainingDto
        TrainingDto updatedTrainingDto = new TrainingDto(trainingId, userId, null, null, ActivityType.RUNNING, 10, 5);

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(existingTraining));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(trainingRepository.save(existingTraining)).thenReturn(updatedTraining);
        when(trainingMapper.toDto(updatedTraining)).thenReturn(updatedTrainingDto);

        // Wykonaj operację aktualizacji
        TrainingDto result = trainingService.updateTraining(trainingId, trainingDto);

        // Zweryfikuj wynik
        assertEquals(updatedTrainingDto, result);
    }

    /**
     * Test sprawdza, czy metoda deleteTraining usuwa trening, gdy trening istnieje.
     */
    @Test
    void deleteTraining_shouldDeleteTraining_whenTrainingExists() {
        Long trainingId = 1L;
        Training training = mock(Training.class);

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        doNothing().when(trainingRepository).delete(training);

        // Wykonaj metodę
        trainingService.deleteTraining(trainingId);

        // Zweryfikuj wynik
        verify(trainingRepository).delete(training);
    }

    /**
     * Test sprawdza, czy metoda deleteTraining rzuca wyjątek, gdy trening nie został znaleziony.
     */
    @Test
    void deleteTraining_shouldThrowException_whenTrainingNotFound() {
        Long trainingId = 1L;

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        // Wykonaj metodę i zweryfikuj wynik
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.deleteTraining(trainingId);
        });

        assertEquals("Trening nie został znaleziony", exception.getMessage());
    }

    // Nowe testy

    /**
     * Test sprawdza, czy metoda getTrainingsByUserId zwraca listę TrainingDto dla użytkownika.
     */
    @Test
    void getTrainingsByUserId_shouldReturnListOfTrainingDtosForUser() {
        Long userId = 1L;
        Training training = mock(Training.class);
        TrainingDto trainingDto = mock(TrainingDto.class);

        // Skonfiguruj mocki
        when(trainingRepository.findByUserId(userId)).thenReturn(Collections.singletonList(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getTrainingsByUserId(userId);

        // Zweryfikuj wynik
        assertEquals(1, result.size());
        assertEquals(trainingDto, result.get(0));
    }

    /**
     * Test sprawdza, czy metoda getTrainingsByEndDateAfter zwraca listę TrainingDto zakończonych po podanej dacie.
     */
    @Test
    void getTrainingsByEndDateAfter_shouldReturnListOfTrainingDtosAfterDate() {
        Date endDate = new Date();
        Training training = mock(Training.class);
        TrainingDto trainingDto = mock(TrainingDto.class);

        // Skonfiguruj mocki
        when(trainingRepository.findByEndTimeAfter(endDate)).thenReturn(Collections.singletonList(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getTrainingsByEndDateAfter(endDate);

        // Zweryfikuj wynik
        assertEquals(1, result.size());
        assertEquals(trainingDto, result.get(0));
    }

    /**
     * Test sprawdza, czy metoda getTrainingsByActivityType zwraca listę TrainingDto dla podanego typu aktywności.
     */
    @Test
    void getTrainingsByActivityType_shouldReturnListOfTrainingDtosForActivityType() {
        ActivityType activityType = ActivityType.RUNNING;
        Training training = mock(Training.class);
        TrainingDto trainingDto = mock(TrainingDto.class);

        // Skonfiguruj mocki
        when(trainingRepository.findByActivityType(activityType)).thenReturn(Collections.singletonList(training));
        when(trainingMapper.toDto(training)).thenReturn(trainingDto);

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getTrainingsByActivityType(activityType);

        // Zweryfikuj wynik
        assertEquals(1, result.size());
        assertEquals(trainingDto, result.get(0));
    }

    /**
     * Test sprawdza, czy metoda getTraining zwraca pusty Optional, gdy trening nie został znaleziony.
     */
    @Test
    void getTraining_shouldReturnEmptyOptional_whenTrainingNotFound() {
        Long trainingId = 1L;

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.empty());

        // Wykonaj metodę
        Optional<TrainingDto> result = trainingService.getTraining(trainingId);

        // Zweryfikuj wynik
        assertFalse(result.isPresent());
    }

    /**
     * Test sprawdza, czy metoda getAllTrainings zwraca pustą listę, gdy nie ma treningów.
     */
    @Test
    void getTrainingsByUserId_shouldReturnEmptyList_whenUserHasNoTrainings() {
        Long userId = 1L;

        // Skonfiguruj mocki
        when(trainingRepository.findByUserId(userId)).thenReturn(Collections.emptyList());

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getTrainingsByUserId(userId);

        // Zweryfikuj wynik
        assertTrue(result.isEmpty());
    }
    /**
     * Test sprawdza, czy metoda getTrainingsByEndDateAfter zwraca pustą listę, gdy nie ma treningów zakończonych po podanej dacie.
     */
    @Test
    void getTrainingsByEndDateAfter_shouldReturnEmptyList_whenNoTrainingsAfterDate() {
        Date endDate = new Date();

        // Skonfiguruj mocki
        when(trainingRepository.findByEndTimeAfter(endDate)).thenReturn(Collections.emptyList());

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getTrainingsByEndDateAfter(endDate);

        // Zweryfikuj wynik
        assertTrue(result.isEmpty());
    }

    /**
     * Test sprawdza, czy metoda getTrainingsByActivityType zwraca pustą listę, gdy nie ma treningów dla podanego typu aktywności.
     */
    @Test
    void getTrainingsByActivityType_shouldReturnEmptyList_whenNoTrainingsForActivityType() {
        ActivityType activityType = ActivityType.RUNNING;

        // Skonfiguruj mocki
        when(trainingRepository.findByActivityType(activityType)).thenReturn(Collections.emptyList());

        // Wykonaj metodę
        List<TrainingDto> result = trainingService.getTrainingsByActivityType(activityType);

        // Zweryfikuj wynik
        assertTrue(result.isEmpty());
    }

    /**
     * Test sprawdza, czy metoda createTraining rzuca wyjątek, gdy TrainingDto ma nieprawidłowe dane.
     */
    @Test
    void createTraining_shouldThrowException_whenTrainingDtoHasInvalidData() {
        Long userId = 1L;

        // Skonfiguruj mocki
        User user = User.createTestUser(userId, "John", "Doe", LocalDate.of(1990, 1, 1), "john.doe@example.com");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Testuj przypadki z nieprawidłowymi danymi
        TrainingDto trainingDtoWithNullActivityType = new TrainingDto(null, userId, new Date(), new Date(), null, 10.0, 5.0);
        Exception exception1 = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.createTraining(trainingDtoWithNullActivityType);
        });
        assertEquals("Nieprawidłowe dane treningu", exception1.getMessage());

        TrainingDto trainingDtoWithNullStartTime = new TrainingDto(null, userId, null, new Date(), ActivityType.RUNNING, 10.0, 5.0);
        Exception exception2 = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.createTraining(trainingDtoWithNullStartTime);
        });
        assertEquals("Nieprawidłowe dane treningu", exception2.getMessage());

        TrainingDto trainingDtoWithNullEndTime = new TrainingDto(null, userId, new Date(), null, ActivityType.RUNNING, 10.0, 5.0);
        Exception exception3 = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.createTraining(trainingDtoWithNullEndTime);
        });
        assertEquals("Nieprawidłowe dane treningu", exception3.getMessage());
    }

    /**
     * Test sprawdza, czy metoda updateTraining rzuca wyjątek, gdy trening ma nieprawidłowe dane.
     */
    @Test
    void deleteTraining_shouldNotDeleteTraining_whenTrainingHasLinkedData() {
        Long trainingId = 1L;
        Training training = mock(Training.class);

        // Skonfiguruj mocki
        when(trainingRepository.findById(trainingId)).thenReturn(Optional.of(training));
        doThrow(new IllegalArgumentException("Trening ma powiązane dane i nie może zostać usunięty")).when(trainingRepository).delete(training);

        // Wykonaj metodę i zweryfikuj wynik
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            trainingService.deleteTraining(trainingId);
        });

        assertEquals("Trening ma powiązane dane i nie może zostać usunięty", exception.getMessage());
    }


}
