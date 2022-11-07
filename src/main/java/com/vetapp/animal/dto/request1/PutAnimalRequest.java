package com.vetapp.animal.dto.request1;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PutAnimalRequest {
    private Integer id;
    private String name;
    private long weight;
    private int age;
    private String typeAnimal;
}
