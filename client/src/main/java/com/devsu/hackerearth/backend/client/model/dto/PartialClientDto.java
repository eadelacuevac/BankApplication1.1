package com.devsu.hackerearth.backend.client.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // <-- AÑADIDO

@Data
@AllArgsConstructor
@NoArgsConstructor // <-- AÑADIDO
public class PartialClientDto {
    private boolean isActive;
}