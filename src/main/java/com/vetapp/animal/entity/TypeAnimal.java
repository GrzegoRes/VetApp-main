package com.vetapp.animal.entity;

public enum TypeAnimal {
    dog,
    cat,
    fish,
    hamster,
    other;

    public static TypeAnimal fromString(String text) {
        for (TypeAnimal b : TypeAnimal.values()) {
            if (b.toString().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return TypeAnimal.other;
    }
}
