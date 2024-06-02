package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.*;
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

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

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

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Użytkownik nie znaleziony."));
    }

    @GetMapping("/details/{id}")
    public UserDto getUserDetails(@PathVariable Long id) {
        return userService.getUser(id)
                .map(userMapper::toDto)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        System.out.println("Użytkownik z ID: " + id + " usunięty.");
    }

    @GetMapping("/basic-info")
    public List<BasicUserInfoDto> getAllBasicUserInfo() {
        return userService.findAllBasicUserInfo();
    }

    @GetMapping("/search")
    public List<BasicUserEmailDto> findUsersByEmail(@RequestParam String email) {
        return userService.findUsersByEmail(email);
    }

    @GetMapping("/older-than/{age}")
    public List<UserDto> findUsersOlderThan(@PathVariable int age) {
        LocalDate date = LocalDate.now().minusYears(age);
        return userService.findAllUsersOlderThen(date).stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UpdateUserDto updateUserDto) {
        User updatedUser = userService.updateUser(id, updateUserDto);
        return userMapper.toDto(updatedUser);
    }
}
