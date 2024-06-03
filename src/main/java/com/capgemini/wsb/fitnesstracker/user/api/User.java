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
 * Reprezentuje użytkownika w systemie.
 *
 * <p>Ta encja przechowuje podstawowe informacje o użytkowniku.</p>
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Tworzy nowego użytkownika z określonymi danymi.
     *
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     * @param birthdate data urodzenia użytkownika
     * @param email email użytkownika
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
     * Tworzy nowego użytkownika do celów testowych.
     *
     * @param id identyfikator użytkownika
     * @param firstName imię użytkownika
     * @param lastName nazwisko użytkownika
     * @param birthdate data urodzenia użytkownika
     * @param email email użytkownika
     * @return nowy obiekt użytkownika
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
    private void setId(Long id) {
        this.id = id;
    }
}
