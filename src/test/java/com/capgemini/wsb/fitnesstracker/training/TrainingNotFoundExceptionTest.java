package com.capgemini.wsb.fitnesstracker.training;

import com.capgemini.wsb.fitnesstracker.training.api.TrainingNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Klasa testowa dla {@link TrainingNotFoundException}.
 */
class TrainingNotFoundExceptionTest {

    /**
     * Test sprawdza, czy wiadomość wyjątku jest poprawna.
     */
    @Test
    void testExceptionMessage() {
        Long trainingId = 1L;
        TrainingNotFoundException exception = assertThrows(TrainingNotFoundException.class, () -> {
            throw new TrainingNotFoundException(trainingId);
        });

        // Zweryfikuj, czy wiadomość wyjątku jest poprawna
        assertEquals("Training with ID=1 was not found", exception.getMessage());
    }
}
