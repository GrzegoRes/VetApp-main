package com.vetapp.vet.entity;

import com.vetapp.animal.entity.TypeAnimal;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String text) {
        for (Role b : Role.values()) {
            if (b.toString().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return Role.USER;
    }
}
