package com.vetapp.vet.entity;

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
@EqualsAndHashCode
public class Vet implements Serializable {
    private String login;
    private LocalDate employmentDate;
    private double price;
    private Role role;
    private boolean isHaveAvatar;
    private byte[] avatar;
}
