package com.vetapp.visit.dto.request1;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PutVisitAnimalRequest {
    private Integer id;
    private String description;
    private double price;
    private LocalDate dateVisit;

    private Integer idAnimal;
}
