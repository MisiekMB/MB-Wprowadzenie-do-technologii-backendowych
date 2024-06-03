package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Reprezentacja DTO z podstawowymi danymi użytkownika, zawierająca jego identyfikator oraz adres email.
 *
 * @param id identyfikator użytkownika
 * @param email adres email użytkownika
 */
public record BasicUserEmailDto(Long id, String email) {}
