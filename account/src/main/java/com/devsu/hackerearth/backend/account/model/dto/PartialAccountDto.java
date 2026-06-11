package com.devsu.hackerearth.backend.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor; // <-- AÑADIDO

@Data
@AllArgsConstructor
@NoArgsConstructor // <-- AÑADIDO
public class PartialAccountDto {
	private boolean isActive;
}