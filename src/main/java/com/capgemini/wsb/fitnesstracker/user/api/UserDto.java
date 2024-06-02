package com.capgemini.wsb.fitnesstracker.user.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.annotation.Nullable;

import java.time.LocalDate;


public record UserDto(@Nullable Long id, @Nullable String firstName, @Nullable String lastName,
                      @JsonFormat(pattern = "yyyy-MM-dd") @Nullable LocalDate birthdate,
                      @Nullable String email) {
}
