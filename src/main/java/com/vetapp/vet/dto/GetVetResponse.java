package com.vetapp.vet.dto;

import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import lombok.*;

import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetVetResponse {
    private String login;
    private LocalDate employmentDate;
    private double price;
    private Role role;

    public static Function<Vet, GetVetResponse> entityToDtoMapper() {
        return vet -> GetVetResponse.builder()
                .login(vet.getLogin())
                .employmentDate(vet.getEmploymentDate())
                .price(vet.getPrice())
                .role(vet.getRole())
                .build();
    }
}
