package com.vetapp.animal.entity;

import com.vetapp.visit.entity.Visit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Animal implements Serializable {
    private Integer id;
    private String name;
    private long weight;
    private int age;
    private TypeAnimal typeAnimal;
    private List<Visit> visits;

    public void addVisits(Visit visit){
        visits.add(visit);
    }

    public void deleteVisits(Visit visit){
        visits.remove(visit);
    }
}
