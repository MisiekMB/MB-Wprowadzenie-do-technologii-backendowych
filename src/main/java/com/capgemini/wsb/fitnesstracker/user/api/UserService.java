package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfejs serwisu do zarządzania użytkownikami.
 *
 * <p>Ten interfejs definiuje metody do tworzenia, pobierania, aktualizowania i usuwania użytkowników oraz inne operacje związane z użytkownikami.</p>
 */
public interface UserService {

    /**
     * Tworzy nowego użytkownika.
     *
     * @param user użytkownik do utworzenia
     * @return utworzony użytkownik
     */
    User createUser(User user);

    /**
     * Pobiera użytkownika po jego ID.
     *
     * @param userId ID użytkownika do pobrania
     * @return {@link Optional} zawierający użytkownika, jeśli znaleziony, w przeciwnym razie pusty
     */
    Optional<User> getUser(Long userId);

    /**
     * Pobiera użytkownika po jego emailu.
     *
     * @param email email użytkownika do pobrania
     * @return {@link Optional} zawierający użytkownika, jeśli znaleziony, w przeciwnym razie pusty
     */
    Optional<User> getUserByEmail(String email);

    /**
     * Pobiera wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    List<User> findAllUsers();

    /**
     * Usuwa użytkownika po jego ID.
     *
     * @param id ID użytkownika do usunięcia
     */
    void deleteUser(Long id);

    /**
     * Pobiera podstawowe informacje o wszystkich użytkownikach.
     *
     * @return lista podstawowych informacji o użytkownikach
     */
    List<BasicUserInfoDto> findAllBasicUserInfo();

    /**
     * Wyszukuje użytkowników po ich emailu.
     *
     * @param email email do wyszukania
     * @return lista użytkowników pasujących do emailu
     */
    List<BasicUserEmailDto> findUsersByEmail(String email);

    /**
     * Znajduje wszystkich użytkowników starszych niż określony wiek.
     *
     * @param time wiek do porównania
     * @return lista użytkowników starszych niż określony wiek
     */
    List<User> findAllUsersOlderThen(LocalDate time);

    /**
     * Aktualizuje użytkownika.
     *
     * @param id ID użytkownika do aktualizacji
     * @param updateUserDto dane użytkownika do aktualizacji
     * @return zaktualizowany użytkownik
     */
    User updateUser(Long id, UpdateUserDto updateUserDto);
}
