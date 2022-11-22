package com.vetapp.vet.entity;

import com.vetapp.visit.entity.Visit;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "vet")
public class Vet implements Serializable {
    @Id
    private String login;
    private String password;
    @Column(name = "employment_date")
    private LocalDate employmentDate;
    private double price;

    @Column(name = "is_have_avatar")
    private boolean isHaveAvatar;

    @Lob
    private byte[] avatar;

    @OneToMany
    private List<Visit> visits;

    @CollectionTable(name = "users_roles", joinColumns = @JoinColumn(name = "user"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;


}
