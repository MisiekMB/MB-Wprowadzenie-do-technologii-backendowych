package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import java.util.List;

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
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        System.out.println("Użytkownik z ID: " + id + " usunięty.");
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User updatedUser = userService.updateUser(id, user);
        return userMapper.toDto(updatedUser);
    }

}