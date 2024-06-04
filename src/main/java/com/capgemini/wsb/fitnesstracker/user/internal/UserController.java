package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler REST do zarządzania użytkownikami.
 *
 * <p>Ten kontroler obsługuje żądania HTTP związane z użytkownikami.</p>
 */
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    /**
     * Pobiera listę wszystkich użytkowników.
     *
     * @return lista wszystkich użytkowników
     */
    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    /**
     * Dodaje nowego użytkownika.
     *
     * @param userDto dane nowego użytkownika
     * @return odpowiedź z dodanym użytkownikiem i statusem HTTP 201
     */
    @PostMapping
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        // Wypisujemy informacje o użytkowniku
        System.out.println("User with e-mail: " + userDto.email() + " passed to the request");

        // Konwertuje DTO na encję
        User user = userMapper.toEntity(userDto);

        // Zapisuje użytkownika
        User savedUser = userService.createUser(user);

        // Konwertuje encję na DTO i zwraca
        UserDto savedUserDto = userMapper.toDto(savedUser);
        return new ResponseEntity<>(savedUserDto, HttpStatus.CREATED);
    }

    /**
     * Pobiera użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     * @return dane użytkownika
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony."));
    }

    /**
     * Pobiera szczegóły użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     * @return szczegółowe dane użytkownika
     */
    @GetMapping("/details/{id}")
    public UserDto getUserDetails(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Usuwa użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     * @return odpowiedź z pustym ciałem i statusem HTTP 204
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        System.out.println("Użytkownik z ID: " + id + " usunięty.");
        return ResponseEntity.noContent().build();
    }

    /**
     * Pobiera podstawowe informacje o wszystkich użytkownikach.
     *
     * @return lista podstawowych informacji o użytkownikach
     */
    @GetMapping("/basic-info")
    public List<BasicUserInfoDto> getAllBasicUserInfo() {
        return userService.findAllBasicUserInfo();
    }

    /**
     * Wyszukuje użytkowników na podstawie adresu e-mail.
     *
     * @param email adres e-mail do wyszukania
     * @return lista użytkowników z dopasowanym adresem e-mail
     */
    @GetMapping("/search")
    public List<BasicUserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmail(email);
    }

    /**
     * Wyszukuje użytkowników starszych niż podany wiek.
     *
     * @param age wiek do porównania
     * @return lista użytkowników starszych niż podany wiek
     */
    @GetMapping("/older-than/{age}")
    public List<UserDto> findUsersOlderThan(@PathVariable int age) {
        LocalDate date = LocalDate.now().minusYears(age);
        return userService.findAllUsersOlderThen(date).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Aktualizuje dane użytkownika na podstawie ID.
     *
     * @param id identyfikator użytkownika
     * @param updateUserDto dane do aktualizacji
     * @return zaktualizowane dane użytkownika
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        User updatedUser = userService.updateUser(id, updateUserDto);
        return userMapper.toDto(updatedUser);
    }

    /**
     * Pobiera użytkowników na podstawie adresu e-mail.
     *
     * @param email adres e-mail do wyszukania
     * @return lista użytkowników z dopasowanym adresem e-mail
     */
    @GetMapping("/email")
    public List<BasicUserEmailDto> getUserByEmail(@RequestParam String email) {
        return userService.findUsersByEmail(email);
    }

    /**
     * Pobiera uproszczoną listę użytkowników.
     *
     * @return lista podstawowych informacji o użytkownikach
     */
    @GetMapping("/simple")
    public List<BasicUserInfoDto> getSimpleUsers() {
        return userService.findAllBasicUserInfo();
    }

    /**
     * Pobiera użytkowników starszych niż podany czas.
     *
     * @param time czas w formacie "yyyy-MM-dd"
     * @return lista użytkowników starszych niż podany czas
     */
    @GetMapping("/older/{time}")
    public List<UserDto> getUsersOlderThan(@PathVariable("time") String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(time, formatter);
        return userService.findAllUsersOlderThen(date).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }
}
