package com.vetapp.animal.dto.response1;

import com.vetapp.animal.entity.TypeAnimal;
import lombok.*;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode

public class GetAnimalsResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    @JsonbPropertyOrder({"id","name","typeAnimal"})
    public static class Animal {
        private Integer id;
        private String name;
        private TypeAnimal typeAnimal;
    }

    @Singular
    private List<Animal> animals;

    public static Function<Collection<com.vetapp.animal.entity.Animal>, GetAnimalsResponse> entityToDtoMapper() {
        return animals -> {
            GetAnimalsResponseBuilder response = GetAnimalsResponse.builder();
            animals.stream()
                    .map(animal -> Animal.builder()
                            .id(animal.getId())
                            .name(animal.getName())
                            .typeAnimal(animal.getTypeAnimal())
                            .build())
                    .forEach(response::animal);
            return response.build();
        };
    }
}
