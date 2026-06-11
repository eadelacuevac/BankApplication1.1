package com.devsu.hackerearth.backend.client.model;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class Client extends Person {
    private String password;
    private boolean isActive;
}