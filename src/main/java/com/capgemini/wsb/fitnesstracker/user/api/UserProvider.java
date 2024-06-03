package com.capgemini.wsb.fitnesstracker.user.api;

import java.util.List;
import java.util.Optional;

/**
 * Interfejs dostarczający metody do zarządzania użytkownikami w systemie.
 */
public interface UserProvider {

    /**
     * Zwraca opcjonalnego użytkownika na podstawie jego identyfikatora.
     *
     * @param userId identyfikator użytkownika
     * @return opcjonalny użytkownik
     */
    Optional<User> getUser(Long userId);

    /**
     * Zwraca opcjonalnego użytkownika na podstawie jego adresu email.
     *
     * @param email adres email użytkownika
     * @return opcjonalny użytkownik
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Zwraca listę wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    List<User> findAllUsers();
}
