package com.vetapp.vet.dto;

import lombok.*;

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
public class GetVetsResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Vet {
        private String login;
    }

    @Singular
    private List<Vet> vets;

    public static Function<Collection<com.vetapp.vet.entity.Vet>, GetVetsResponse> entityToDtoMapper() {
        return vets -> {
            GetVetsResponse.GetVetsResponseBuilder response = GetVetsResponse.builder();
            vets.stream()
                    .map(character -> Vet.builder()
                            .login(character.getLogin())
                            .build())
                    .forEach(response::vet);
            return response.build();
        };
    }
}
