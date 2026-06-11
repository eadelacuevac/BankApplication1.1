package com.devsu.hackerearth.backend.account.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    
    // REQUERIMIENTO F3: Captura y control profesional de errores
    // Se implementó este interceptor global de excepciones.
    // Escuchar cualquier RuntimeException lanzada en el sistema. 
    // Si el mensaje es "Saldo no disponible", transforma la respuesta automáticamente
    // en un HTTP 400 (Bad Request) estructurado en formato JSON.
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", ex.getMessage());
        
        // Operador ternario para decidir el código de estado adecuado
        HttpStatus status = ex.getMessage().equals("Saldo no disponible") 
                            ? HttpStatus.BAD_REQUEST 
                            : HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(response);
    }
}