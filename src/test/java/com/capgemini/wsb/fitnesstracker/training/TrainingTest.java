package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.Training;
import com.capgemini.wsb.fitnesstracker.training.internal.ActivityType;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Klasa testowa dla {@link Training}.
 */
class TrainingTest {

    /**
     * Test sprawdza, czy konstruktor z parametrem ID poprawnie tworzy obiekt Training.
     */
    @Test
    void testTrainingConstructorWithId() {
        User user = new User("First", "Last", null, "email@example.com");
        Date startTime = new Date();
        Date endTime = new Date();
        Training training = new Training(1L, user, startTime, endTime, ActivityType.RUNNING, 5.0, 10.0);

        // Zweryfikuj poprawność danych obiektu
        assertEquals(1L, training.getId());
        assertEquals(user, training.getUser());
        assertEquals(startTime, training.getStartTime());
        assertEquals(endTime, training.getEndTime());
        assertEquals(ActivityType.RUNNING, training.getActivityType());
        assertEquals(5.0, training.getDistance());
        assertEquals(10.0, training.getAverageSpeed());
    }

    /**
     * Test sprawdza, czy konstruktor bez parametru ID poprawnie tworzy obiekt Training.
     */
    @Test
    void testTrainingConstructorWithoutId() {
        User user = new User("First", "Last", null, "email@example.com");
        Date startTime = new Date();
        Date endTime = new Date();
        Training training = new Training(user, startTime, endTime, ActivityType.RUNNING, 5.0, 10.0);

        // Zweryfikuj poprawność danych obiektu
        assertNull(training.getId());
        assertEquals(user, training.getUser());
        assertEquals(startTime, training.getStartTime());
        assertEquals(endTime, training.getEndTime());
        assertEquals(ActivityType.RUNNING, training.getActivityType());
        assertEquals(5.0, training.getDistance());
        assertEquals(10.0, training.getAverageSpeed());
    }
}
