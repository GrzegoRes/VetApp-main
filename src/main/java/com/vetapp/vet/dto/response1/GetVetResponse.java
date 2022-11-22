package com.vetapp.vet.dto.response1;

import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import lombok.*;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@JsonbPropertyOrder({"login","employmentDate","price"})
public class GetVetResponse {
    private String login;
    private LocalDate employmentDate;
    private double price;

    public static Function<Vet, GetVetResponse> entityToDtoMapper() {
        return vet -> GetVetResponse.builder()
                .login(vet.getLogin())
                .employmentDate(vet.getEmploymentDate())
                .price(vet.getPrice())
                .build();
    }
}
