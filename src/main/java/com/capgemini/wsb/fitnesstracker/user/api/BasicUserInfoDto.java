package com.capgemini.wsb.fitnesstracker.user.api;

/**
 * Reprezentacja DTO z podstawowymi informacjami o użytkowniku, zawierająca jego identyfikator, imię oraz nazwisko.
 *
 * @param id identyfikator użytkownika
 * @param firstName imię użytkownika
 * @param lastName nazwisko użytkownika
 */
public record BasicUserInfoDto(Long id, String firstName, String lastName) {}
