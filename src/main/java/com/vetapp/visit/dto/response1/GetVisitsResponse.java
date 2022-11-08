package com.vetapp.visit.dto.response1;
import lombok.*;

import javax.json.bind.annotation.JsonbPropertyOrder;
import java.time.LocalDate;
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
public class GetVisitsResponse {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    @JsonbPropertyOrder({"id","dateVisit","vetName"})
    public static class Visit {
        private Integer id;
        private LocalDate dateVisit;
    }

    @Singular
    private List<Visit> visits;

    public static Function<Collection<com.vetapp.visit.entity.Visit>, GetVisitsResponse> entityToDtoMapper() {
        return visits -> {
            GetVisitsResponseBuilder response = GetVisitsResponse.builder();
            visits.stream()
                    .map(visit -> Visit.builder()
                            .id(visit.getId())
                            .dateVisit(visit.getDateVisit())
                            .build())
                    .forEach(response::visit);
            return response.build();
        };
    }
}