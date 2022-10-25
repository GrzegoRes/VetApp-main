package com.vetapp.vet.entity;

import com.vetapp.visit.entity.Visit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Vet implements Serializable {
    private String login;
    private LocalDate employmentDate;
    private double price;
    private Role role;
    private boolean isHaveAvatar;
    private byte[] avatar;
    private List<Visit> visits;

    public void addVisits(Visit visit){
        visits.add(visit);
    }

    public void deleteVisits(Visit visit){
        visits.remove(visit);
    }

}
