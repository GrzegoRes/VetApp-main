package com.vetapp.animal.dto.response1;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;
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
@JsonbPropertyOrder({"id","name","weight","age","typeAnimal"})
public class GetVisitResponse{
    private Integer id;
    private String description;
    private double price;
    private LocalDate dateVisit;
    //private String vetName;

    public static Function<Visit, GetVisitResponse> entityToDtoMapper() {
        return visit -> GetVisitResponse.builder()
                .id(visit.getId())
                .description(visit.getDescription())
                .price(visit.getPrice())
                .dateVisit(visit.getDateVisit())
                //.vetName(visit.getVet().getLogin())
                .build();

    }
}
