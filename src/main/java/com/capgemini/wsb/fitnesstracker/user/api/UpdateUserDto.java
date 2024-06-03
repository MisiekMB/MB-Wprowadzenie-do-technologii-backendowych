package com.capgemini.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;

/**
 * Reprezentacja DTO do aktualizacji danych użytkownika, zawierająca jego identyfikator, imię, nazwisko, datę urodzenia oraz adres email.
 *
 * @param id identyfikator użytkownika (może być null)
 * @param firstName imię użytkownika (może być null)
 * @param lastName nazwisko użytkownika (może być null)
 * @param birthdate data urodzenia użytkownika (może być null)
 * @param email adres email użytkownika (może być null)
 */
public record UpdateUserDto(
        @Nullable Long id,
        @Nullable String firstName,
        @Nullable String lastName,
        @JsonFormat(pattern = "yyyy-MM-dd") @Nullable LocalDate birthdate,
        @Nullable String email
) {
}
