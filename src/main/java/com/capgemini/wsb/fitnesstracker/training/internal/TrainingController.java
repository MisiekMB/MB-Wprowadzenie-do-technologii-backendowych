package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Kontroler obsługujący żądania dotyczące treningów.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    /**
     * Pobiera wszystkie treningi.
     *
     * @return lista obiektów TrainingDto
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    /**
     * Pobiera wszystkie treningi użytkownika na podstawie jego ID.
     *
     * @param userId identyfikator użytkownika
     * @return lista obiektów TrainingDto dla użytkownika
     */
    @GetMapping("/user/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId);
    }

    /**
     * Pobiera wszystkie treningi zakończone po określonej dacie.
     *
     * @param endDate data zakończenia
     * @return lista obiektów TrainingDto zakończonych po określonej dacie
     */
    @GetMapping("/ended-after")
    public List<TrainingDto> getTrainingsByEndDateAfter(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return trainingService.getTrainingsByEndDateAfter(endDate);
    }

    /**
     * Pobiera wszystkie treningi dla określonego typu aktywności.
     *
     * @param activityType typ aktywności
     * @return lista obiektów TrainingDto dla określonego typu aktywności
     */
    @GetMapping("/activity")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam("type") ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType);
    }

    /**
     * Tworzy nowy trening dla użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @param trainingDto obiekt TrainingDto reprezentujący nowy trening
     * @return utworzony obiekt TrainingDto
     */
    @PostMapping("/{userId}")
    public TrainingDto createTraining(@PathVariable Long userId, @RequestBody TrainingDto trainingDto) {
        TrainingDto newTrainingDto = new TrainingDto(
                null, // ID zostanie wygenerowane przez bazę danych
                userId,
                trainingDto.startTime(),
                trainingDto.endTime(),
                trainingDto.activityType(),
                trainingDto.distance(),
                trainingDto.averageSpeed()
        );
        return trainingService.createTraining(newTrainingDto);
    }

    /**
     * Aktualizuje istniejący trening na podstawie jego ID.
     *
     * @param trainingId identyfikator treningu
     * @param trainingDto obiekt TrainingDto reprezentujący zaktualizowany trening
     * @return zaktualizowany obiekt TrainingDto
     */
    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable Long trainingId, @RequestBody TrainingDto trainingDto) {
        return trainingService.updateTraining(trainingId, trainingDto);
    }
}
