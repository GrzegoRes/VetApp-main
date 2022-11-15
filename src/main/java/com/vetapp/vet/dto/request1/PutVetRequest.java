package com.vetapp.vet.dto.request1;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PutVetRequest {
    private String login;
    private LocalDate employmentDate;
    private double price;
    private String role;
}