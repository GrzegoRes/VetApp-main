package com.vetapp.animal.entity;

import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "animal")
public class Animal implements Serializable {
    @Id
    private Integer id;
    private String name;
    private long weight;
    private int age;
    @Column(name = "type_animal")
    private TypeAnimal typeAnimal;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Visit> visits;


    public void addVisits(Visit visit){
        visits.add(visit);
    }

    public void deleteVisits(Visit visit){
        visits.remove(visit);
    }
}
