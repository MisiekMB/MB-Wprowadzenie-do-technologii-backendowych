package com.capgemini.wsb.fitnesstracker.training.api;

import com.capgemini.wsb.fitnesstracker.training.internal.TrainingDto;
import java.util.Date;
import java.util.List;

/**
 * Interfejs definiujący metody do zarządzania sesjami treningowymi.
 */
public interface TrainingProvider {

    /**
     * Zwraca listę wszystkich sesji treningowych.
     *
     * @return lista wszystkich sesji treningowych
     */
    List<Training> getAllTrainings();

    /**
     * Aktualizuje istniejącą sesję treningową.
     *
     * @param trainingId identyfikator sesji treningowej do aktualizacji
     * @param trainingDto obiekt DTO zawierający zaktualizowane dane sesji treningowej
     * @return zaktualizowana sesja treningowa
     */
    Training updateTraining(Long trainingId, TrainingDto trainingDto);

    /**
     * Zwraca listę sesji treningowych zakończonych po podanej dacie.
     *
     * @param endDate data, po której sesje treningowe powinny być zakończone
     * @return lista sesji treningowych zakończonych po podanej dacie
     */
    List<Training> getTrainingsByEndDateAfter(Date endDate);

    /**
     * Tworzy nową sesję treningową.
     *
     * @param training obiekt sesji treningowej do utworzenia
     * @return utworzona sesja treningowa
     */
    Training createTraining(Training training);
}
