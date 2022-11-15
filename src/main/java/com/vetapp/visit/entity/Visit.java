package com.vetapp.visit.entity;

import com.vetapp.animal.entity.Animal;
import com.vetapp.vet.entity.Vet;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(exclude ={"vet" }) //, "animal"
@Entity
@Table(name = "visit")
public class Visit implements Serializable {
    @Id
    private Integer id;
    private String description;
    private double price;

    @Column(name = "date_visit")
    private LocalDate dateVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "animal")
    private Animal animal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vet")
    private Vet vet;
}
