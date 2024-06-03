package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import com.capgemini.wsb.fitnesstracker.user.api.UserNotFoundException;
import com.capgemini.wsb.fitnesstracker.user.api.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;
    private final TrainingMapper trainingMapper;
    private final UserService userService;

    /**
     * Zwraca listę wszystkich sesji treningowych.
     *
     * @return lista wszystkich sesji treningowych
     */
    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings().stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę sesji treningowych dla konkretnego użytkownika.
     *
     * @param userId identyfikator użytkownika
     * @return lista sesji treningowych dla danego użytkownika
     */
    @GetMapping("/{userId}")
    public List<TrainingDto> getTrainingsByUserId(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId).stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę sesji treningowych zakończonych po podanej dacie.
     *
     * @param date data, po której sesje treningowe powinny być zakończone
     * @return lista sesji treningowych zakończonych po podanej dacie
     */
    @GetMapping("/finished/{date}")
    public List<TrainingDto> getTrainingsByEndDateAfter(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        return trainingService.getTrainingsByEndDateAfter(date).stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    /**
     * Zwraca listę sesji treningowych dla danego typu aktywności.
     *
     * @param activityType typ aktywności
     * @return lista sesji treningowych dla danego typu aktywności
     */
    @GetMapping("/activityType")
    public List<TrainingDto> getTrainingsByActivityType(@RequestParam ActivityType activityType) {
        return trainingService.getTrainingsByActivityType(activityType).stream()
                .map(trainingMapper::toTrainingDto)
                .collect(Collectors.toList());
    }

    /**
     * Tworzy nową sesję treningową.
     *
     * @param trainingSupportDto obiekt DTO zawierający dane nowej sesji treningowej
     * @return odpowiedź z utworzoną sesją treningową
     */
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingSupportDto trainingSupportDto) {
        User user = userService.getUser(trainingSupportDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Użytkownik nie istnieje."));
        TrainingDto trainingDto = trainingMapper.toTrainingFromTrainingSupportDto(trainingSupportDto);
        trainingDto.setUser(user);
        Training training = trainingMapper.toTrainingEntity(trainingDto);
        TrainingDto newTraining = trainingMapper.toTrainingDto(trainingService.createTraining(training));
        return new ResponseEntity<>(newTraining, HttpStatus.CREATED);
    }


    /**
     * Aktualizuje istniejącą sesję treningową.
     *
     * @param trainingDto obiekt DTO zawierający zaktualizowane dane sesji treningowej
     * @param trainingId identyfikator sesji treningowej do aktualizacji
     * @return zaktualizowana sesja treningowa
     */
    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@RequestBody TrainingDto trainingDto, @PathVariable Long trainingId) {
        Training updatedTraining = trainingService.updateTraining(trainingId, trainingDto);
        return trainingMapper.toTrainingDto(updatedTraining);
    }

    /**
     * Obsługuje wyjątki typu UserNotFoundException.
     *
     * @param ex wyjątek
     * @return odpowiedź z komunikatem błędu i statusem NOT_FOUND
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
