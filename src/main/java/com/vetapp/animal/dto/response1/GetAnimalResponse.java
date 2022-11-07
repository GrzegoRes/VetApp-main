package com.vetapp.animal.dto.response1;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import lombok.*;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@JsonbPropertyOrder({"id","name","weight","age","typeAnimal"})
public class GetAnimalResponse{
    private Integer id;
    private String name;
    private long weight;
    private int age;
    private TypeAnimal typeAnimal;

    public static Function<Animal, GetAnimalResponse> entityToDtoMapper() {
        return animal -> GetAnimalResponse.builder()
                .id(animal.getId())
                .name(animal.getName())
                .age(animal.getAge())
                .weight(animal.getWeight())
                .typeAnimal(animal.getTypeAnimal())
                .build();

    }
}
