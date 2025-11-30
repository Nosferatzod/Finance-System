package com.example.demo.dto;

import com.example.demo.models.RoleName;

public record CreateUserDTO (
        String name,
        String cpf,
        String email,
        String password
    )
{}
