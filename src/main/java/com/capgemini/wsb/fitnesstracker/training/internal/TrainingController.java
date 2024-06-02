package com.capgemini.wsb.fitnesstracker.training.internal;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/training")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingServiceImpl trainingService;

    @GetMapping
    public List<TrainingDto> getAllTrainings() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public Optional<TrainingDto> getTraining(@PathVariable Long id) {
        return trainingService.getTraining(id);
    }
}
