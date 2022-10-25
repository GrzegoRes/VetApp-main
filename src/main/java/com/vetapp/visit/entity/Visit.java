package com.vetapp.visit.entity;

import com.vetapp.vet.entity.Vet;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(exclude = "vet")
public class Visit implements Serializable {
    private Integer id;
    private String description;
    private double price;
    private LocalDate dateVisit;
    private Animal animal; //tmp
    private Vet vet;
}
