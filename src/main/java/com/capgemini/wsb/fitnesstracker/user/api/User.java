package com.capgemini.wsb.fitnesstracker.user.api;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Klasa encji reprezentująca użytkownika w systemie.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class User {

    /**
     * Identyfikator użytkownika.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    /**
     * Imię użytkownika.
     */
    @Column(name = "first_name", nullable = false)
    private String firstName;

    /**
     * Nazwisko użytkownika.
     */
    @Column(name = "last_name", nullable = false)
    private String lastName;

    /**
     * Data urodzenia użytkownika.
     */
    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    /**
     * Adres email użytkownika.
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Konstruktor tworzący użytkownika z podanymi danymi.
     *
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     * @param birthdate data urodzenia użytkownika
     * @param email adres email użytkownika
     */
    public User(
            final String firstName,
            final String lastName,
            final LocalDate birthdate,
            final String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
    }

    /**
     * Metoda tworząca testowego użytkownika z podanymi danymi.
     *
     * @param id identyfikator użytkownika
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     * @param birthdate data urodzenia użytkownika
     * @param email adres email użytkownika
     * @return utworzony użytkownik
     */
    public static User createTestUser(Long id, String firstName, String lastName, LocalDate birthdate, String email) {
        User user = new User(firstName, lastName, birthdate, email);
        user.setId(id);
        return user;
    }

    /**
     * Ustawia identyfikator użytkownika.
     *
     * @param id identyfikator użytkownika
     */
    public void setId(Long id) {
        this.id = id;
    }
}
