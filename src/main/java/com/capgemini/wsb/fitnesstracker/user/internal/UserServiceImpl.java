package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacja interfejsu {@link UserService}.
 *
 * <p>Ta klasa zapewnia logikę biznesową do zarządzania użytkownikami.</p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserProvider {

    private final UserRepository userRepository;

    /**
     * Tworzy nowego użytkownika.
     *
     * @param user obiekt użytkownika do utworzenia
     * @return utworzony użytkownik
     */
    @Override
    public User createUser(final User user) {
        log.info("Tworzenie użytkownika {}", user);
        if (user.getId() != null) {
            throw new IllegalArgumentException("Użytkownik ma już ID w bazie danych, aktualizacja nie jest dozwolona!");
        }
        if (!userRepository.findByEmailContainingIgnoreCase(user.getEmail()).isEmpty()) {
            throw new IllegalArgumentException("Email jest już zajęty!");
        }
        return userRepository.save(user);
    }

    /**
     * Pobiera użytkownika na podstawie ID.
     *
     * @param userId identyfikator użytkownika
     * @return opcjonalny użytkownik
     */
    @Override
    public Optional<User> getUser(final Long userId) {
        return userRepository.findById(userId);
    }

    /**
     * Pobiera użytkownika na podstawie adresu e-mail.
     *
     * @param email adres e-mail użytkownika
     * @return opcjonalny użytkownik
     */
    @Override
    public Optional<User> getUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Pobiera listę wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Usuwa użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     */
    @Override
    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("Usunięto użytkownika z ID {}", id);
        } else {
            throw new IllegalArgumentException("Użytkownik z ID " + id + " nie istnieje.");
        }
    }

    /**
     * Pobiera listę podstawowych informacji o wszystkich użytkownikach.
     *
     * @return lista podstawowych informacji o użytkownikach
     */
    @Override
    public List<BasicUserInfoDto> findAllBasicUserInfo() {
        return userRepository.findAll().stream()
                .map(user -> new BasicUserInfoDto(user.getId(), user.getFirstName(), user.getLastName()))
                .collect(Collectors.toList());
    }

    /**
     * Wyszukuje użytkowników na podstawie adresu e-mail.
     *
     * @param email adres e-mail do wyszukania
     * @return lista użytkowników z dopasowanym adresem e-mail
     */
    @Override
    public List<BasicUserEmailDto> findUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email).stream()
                .map(user -> new BasicUserEmailDto(user.getId(), user.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Pobiera listę użytkowników starszych niż podany czas.
     *
     * @param time czas do porównania
     * @return lista użytkowników starszych niż podany czas
     */
    @Override
    public List<User> findAllUsersOlderThen(final LocalDate time) {
        return userRepository.findAllUsersOlderThen(time);
    }

    /**
     * Aktualizuje dane użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     * @param updateUserDto dane do aktualizacji
     * @return zaktualizowany użytkownik
     */
    @Override
    public User updateUser(Long id, UpdateUserDto updateUserDto) {
        Optional<User> existingUserOptional = userRepository.findById(id);

        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();

            if (updateUserDto.firstName() != null) {
                existingUser.setFirstName(updateUserDto.firstName());
            }
            if (updateUserDto.lastName() != null) {
                existingUser.setLastName(updateUserDto.lastName());
            }
            if (updateUserDto.birthdate() != null) {
                existingUser.setBirthdate(updateUserDto.birthdate());
            }
            if (updateUserDto.email() != null) {
                existingUser.setEmail(updateUserDto.email());
            }

            return userRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("Użytkownik z ID: " + id + " nie istnieje.");
        }
    }
}
