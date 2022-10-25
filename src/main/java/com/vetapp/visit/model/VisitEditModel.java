package com.vetapp.visit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class VisitEditModel {
    private String id;
    private String description;
    private String price;
    private String login;
}