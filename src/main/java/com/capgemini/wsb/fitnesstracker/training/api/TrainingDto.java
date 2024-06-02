package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import java.util.Date;

public record TrainingDto(
        Long id,
        Long userId,   // Ensure this field is correctly defined
        Date startTime,
        Date endTime,
        ActivityType activityType,
        double distance,
        double averageSpeed
) {}
