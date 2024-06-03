package com.capgemini.wsb.fitnesstracker.user.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interfejs serwisu do zarządzania użytkownikami w systemie.
 */
public interface UserService {

    /**
     * Tworzy nowego użytkownika.
     *
     * @param user obiekt użytkownika do utworzenia
     * @return utworzony użytkownik
     */
    User createUser(User user);

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

    /**
     * Usuwa użytkownika na podstawie jego identyfikatora.
     *
     * @param id identyfikator użytkownika
     */
    void deleteUser(Long id);

    /**
     * Zwraca listę wszystkich użytkowników zawierających podstawowe informacje.
     *
     * @return lista podstawowych informacji o wszystkich użytkownikach
     */
    List<BasicUserInfoDto> findAllBasicUserInfo();

    /**
     * Znajduje użytkowników na podstawie fragmentu ich adresu email.
     *
     * @param email fragment adresu email
     * @return lista użytkowników pasujących do podanego fragmentu email
     */
    List<BasicUserEmailDto> findUsersByEmail(String email);

    /**
     * Zwraca listę wszystkich użytkowników starszych niż podana data.
     *
     * @param time data graniczna
     * @return lista użytkowników starszych niż podana data
     */
    List<User> findAllUsersOlderThen(LocalDate time);

    /**
     * Aktualizuje dane użytkownika na podstawie jego identyfikatora i obiektu DTO zawierającego nowe dane.
     *
     * @param id identyfikator użytkownika
     * @param updateUserDto obiekt DTO zawierający nowe dane użytkownika
     * @return zaktualizowany użytkownik
     */
    User updateUser(Long id, UpdateUserDto updateUserDto);
}
