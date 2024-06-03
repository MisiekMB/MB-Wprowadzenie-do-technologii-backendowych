package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingController;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingServiceImpl;
import com.capgemini.wsb.fitnesstracker.user.internal.UserRepository;
import com.capgemini.wsb.fitnesstracker.training.internal.TrainingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasa testowa dla {@link TrainingController}.
 */
class TrainingControllerTest {

    @Mock
    private TrainingServiceImpl trainingService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TrainingRepository trainingRepository;

    @InjectMocks
    private TrainingController trainingController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(trainingController).build();
    }

    /**
     * Test sprawdza, czy metoda getAllTrainings zwraca listę TrainingDto.
     */
    @Test
    void getAllTrainings_shouldReturnListOfTrainingDtos() throws Exception {
        TrainingDto trainingDto = new TrainingDto(1L, 1L, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        List<TrainingDto> trainingDtos = Collections.singletonList(trainingDto);

        // Skonfiguruj mocki
        when(trainingService.getAllTrainings()).thenReturn(trainingDtos);

        // Wykonaj metodę i zweryfikuj wynik
        mockMvc.perform(get("/v1/trainings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingDto.id()))
                .andExpect(jsonPath("$[0].userId").value(trainingDto.userId()))
                .andExpect(jsonPath("$[0].activityType").value(trainingDto.activityType().toString()));
    }

    /**
     * Test sprawdza, czy metoda getTrainingsByUserId zwraca listę TrainingDto dla użytkownika.
     */
    @Test
    void getTrainingsByUserId_shouldReturnListOfTrainingDtosForUser() throws Exception {
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(1L, userId, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        List<TrainingDto> trainingDtos = Collections.singletonList(trainingDto);

        // Skonfiguruj mocki
        when(trainingService.getTrainingsByUserId(userId)).thenReturn(trainingDtos);

        // Wykonaj metodę i zweryfikuj wynik
        mockMvc.perform(get("/v1/trainings/user/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingDto.id()))
                .andExpect(jsonPath("$[0].userId").value(trainingDto.userId()))
                .andExpect(jsonPath("$[0].activityType").value(trainingDto.activityType().toString()));
    }

    /**
     * Test sprawdza, czy metoda getTrainingsByEndDateAfter zwraca listę TrainingDto zakończonych po danej dacie.
     */
    @Test
    void getTrainingsByEndDateAfter_shouldReturnListOfTrainingDtosAfterDate() throws Exception {
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse("2023-01-01");
        TrainingDto trainingDto = new TrainingDto(1L, 1L, new Date(), endDate, ActivityType.RUNNING, 10.0, 5.0);
        List<TrainingDto> trainingDtos = Collections.singletonList(trainingDto);

        // Skonfiguruj mocki
        when(trainingService.getTrainingsByEndDateAfter(any(Date.class))).thenReturn(trainingDtos);

        // Wykonaj metodę i zweryfikuj wynik
        mockMvc.perform(get("/v1/trainings/ended-after")
                        .param("date", "2023-01-01")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingDto.id()))
                .andExpect(jsonPath("$[0].userId").value(trainingDto.userId()))
                .andExpect(jsonPath("$[0].activityType").value(trainingDto.activityType().toString()));
    }

    /**
     * Test sprawdza, czy metoda getTrainingsByActivityType zwraca listę TrainingDto dla danego typu aktywności.
     */
    @Test
    void getTrainingsByActivityType_shouldReturnListOfTrainingDtosForActivityType() throws Exception {
        ActivityType activityType = ActivityType.RUNNING;
        TrainingDto trainingDto = new TrainingDto(1L, 1L, new Date(), new Date(), activityType, 10.0, 5.0);
        List<TrainingDto> trainingDtos = Collections.singletonList(trainingDto);

        // Skonfiguruj mocki
        when(trainingService.getTrainingsByActivityType(activityType)).thenReturn(trainingDtos);

        // Wykonaj metodę i zweryfikuj wynik
        mockMvc.perform(get("/v1/trainings/activity")
                        .param("type", activityType.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingDto.id()))
                .andExpect(jsonPath("$[0].userId").value(trainingDto.userId()))
                .andExpect(jsonPath("$[0].activityType").value(trainingDto.activityType().toString()));
    }

    /**
     * Test sprawdza, czy metoda createTraining zwraca utworzony TrainingDto.
     */
    @Test
    void createTraining_shouldReturnCreatedTrainingDto() throws Exception {
        Long userId = 1L;
        TrainingDto trainingDto = new TrainingDto(null, userId, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);
        TrainingDto createdTrainingDto = new TrainingDto(1L, userId, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);

        // Skonfiguruj mocki
        when(trainingService.createTraining(any(TrainingDto.class))).thenReturn(createdTrainingDto);

        // Wykonaj metodę i zweryfikuj wynik
        mockMvc.perform(post("/v1/trainings/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startTime\":\"2023-01-01T10:00:00.000+00:00\",\"endTime\":\"2023-01-01T11:00:00.000+00:00\",\"activityType\":\"RUNNING\",\"distance\":10.0,\"averageSpeed\":5.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(createdTrainingDto.id()))
                .andExpect(jsonPath("$.userId").value(createdTrainingDto.userId()))
                .andExpect(jsonPath("$.activityType").value(createdTrainingDto.activityType().toString()));
    }

    /**
     * Test sprawdza, czy metoda updateTraining zwraca zaktualizowany TrainingDto.
     */
    @Test
    void updateTraining_shouldReturnUpdatedTrainingDto() throws Exception {
        Long trainingId = 1L;
        TrainingDto trainingDto = new TrainingDto(trainingId, 1L, new Date(), new Date(), ActivityType.RUNNING, 10.0, 5.0);

        // Skonfiguruj mocki
        when(trainingService.updateTraining(eq(trainingId), any(TrainingDto.class))).thenReturn(trainingDto);

        // Wykonaj metodę i zweryfikuj wynik
        mockMvc.perform(put("/v1/trainings/{trainingId}", trainingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"startTime\":\"2023-01-01T10:00:00.000+00:00\",\"endTime\":\"2023-01-01T11:00:00.000+00:00\",\"activityType\":\"RUNNING\",\"distance\":10.0,\"averageSpeed\":5.0}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingDto.id()))
                .andExpect(jsonPath("$.userId").value(trainingDto.userId()))
                .andExpect(jsonPath("$.activityType").value(trainingDto.activityType().toString()));
    }
}
