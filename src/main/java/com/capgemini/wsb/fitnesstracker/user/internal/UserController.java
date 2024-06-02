package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.BasicUserEmailDto;
import com.capgemini.wsb.fitnesstracker.user.api.BasicUserInfoDto;
import com.capgemini.wsb.fitnesstracker.user.api.UpdateUserDto;
import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
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
     * Pobiera wszystkich użytkowników.
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
     * @param userDto dane użytkownika do dodania
     * @return dodany użytkownik
     */
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        // Wypisujemy informacje o użytkowniku
        System.out.println("User with e-mail: " + userDto.email() + " passed to the request");

        // Konwertuje DTO na encję
        User user = userMapper.toEntity(userDto);

        // Zapisuje użytkownika
        User savedUser = userService.createUser(user);

        // Konwertuje encję na DTO i zwraca
        return userMapper.toDto(savedUser);
    }

    /**
     * Pobiera użytkownika po ID.
     *
     * @param id ID użytkownika do pobrania
     * @return znaleziony użytkownik
     */
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony."));
    }

    /**
     * Pobiera szczegóły użytkownika po ID.
     *
     * @param id ID użytkownika do pobrania
     * @return znaleziony użytkownik
     */
    @GetMapping("/details/{id}")
    public UserDto getUserDetails(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Usuwa użytkownika po ID.
     *
     * @param id ID użytkownika do usunięcia
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        System.out.println("Użytkownik z ID: " + id + " usunięty.");
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
     * Wyszukuje użytkowników po emailu.
     *
     * @param email email do wyszukania
     * @return lista użytkowników pasujących do emailu
     */
    @GetMapping("/search")
    public List<BasicUserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmail(email);
    }

    /**
     * Wyszukuje użytkowników starszych niż określony wiek.
     *
     * @param age wiek do porównania
     * @return lista użytkowników starszych niż określony wiek
     */
    @GetMapping("/older-than/{age}")
    public List<UserDto> findUsersOlderThan(@PathVariable int age) {
        LocalDate date = LocalDate.now().minusYears(age);
        return userService.findAllUsersOlderThen(date).stream()
                .map(user -> new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getBirthdate(), user.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Aktualizuje użytkownika.
     *
     * @param id ID użytkownika do aktualizacji
     * @param updateUserDto dane użytkownika do aktualizacji
     * @return zaktualizowany użytkownik
     */
    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        User updatedUser = userService.updateUser(id, updateUserDto);
        return userMapper.toDto(updatedUser);
    }
}