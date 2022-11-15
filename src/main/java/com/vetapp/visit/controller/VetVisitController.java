package com.vetapp.visit.controller;
import com.vetapp.MimeTypes;
import com.vetapp.animal.dto.response1.GetVisitResponse;
import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.service.AnimalService;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.dto.request1.PutVisitAnimalRequest;
import com.vetapp.visit.dto.response1.GetVisitsResponse;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.service.VisitService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/vets/{idVet}/visits")
public class VetVisitController {
    private VetService vetService;
    private VisitService visitService;
    private AnimalService animalService;

    public VetVisitController(){};

    @Inject
    void setAnimalService(VetService vetService, AnimalService animalService){
        this.vetService = vetService;
        this.animalService = animalService;
    }

    @Inject
    void setAnimalService(VisitService visitService){
        this.visitService = visitService;
    }

    @GET
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getVisits(@PathParam("idVet") String idVet){
        Optional<List<Visit>> visits = visitService.findByVet(idVet);
        if(visits.isPresent()){
            return Response.ok(GetVisitsResponse.entityToDtoMapper().apply(visits.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getVisits(@PathParam("idVet") String idVet, @PathParam("id") Integer id){
        Optional<List<Visit>> visits = visitService.findByVet(idVet);
        if(visits.isPresent()){
            Optional<Visit> visit = visits.get().stream()
                    .filter(visit1 -> visit1.getId().equals(id))
                    .findAny();
            return Response.ok(GetVisitResponse.entityToDtoMapper().apply(visit.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response createVisit(@PathParam("idVet") String idVet, PutVisitAnimalRequest putVisitAnimalRequest){
        try {
            Optional<Vet> vet = vetService.find(idVet);
            Optional<Animal> animal = animalService.find(putVisitAnimalRequest.getIdAnimal());
            if (vet.isPresent() && animal.isPresent()) {
                visitService.create2(Visit.builder()
                        .id(putVisitAnimalRequest.getId())
                        .description(putVisitAnimalRequest.getDescription())
                        .price(putVisitAnimalRequest.getPrice())
                        .dateVisit(putVisitAnimalRequest.getDateVisit())
                        .vet(vet.get())
                        .animal(animal.get())
                        .build());
                return Response.status(Response.Status.CREATED).build();
            }
            return Response.status(Response.Status.NOT_FOUND).build();
        }catch (IllegalArgumentException ex) {
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response updateVisit(@PathParam("idVet") String idVet, @PathParam("id") Integer id, PutVisitAnimalRequest putVisitAnimalRequest){
        Optional<Vet> vet = vetService.find(idVet);
        Optional<Animal> animal = animalService.find(putVisitAnimalRequest.getIdAnimal());
        if(vet.isPresent() && animal.isPresent()){
            visitService.update(Visit.builder()
                    .id(putVisitAnimalRequest.getId())
                    .description(putVisitAnimalRequest.getDescription())
                    .price(putVisitAnimalRequest.getPrice())
                    .dateVisit(putVisitAnimalRequest.getDateVisit())
                    .vet(vet.get())
                    .animal(animal.get())
                    .build());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response deleteVisit(@PathParam("idVet") String idVet, @PathParam("id") Integer id){
        Optional<List<Visit>> visits = visitService.findByVet(idVet);
        if(visits.isPresent()){
            Optional<Visit> visit = visits.get().stream()
                    .filter(visit1 -> visit1.getId().equals(id))
                    .findAny();
            if(visit.isPresent()){
                visitService.delete(visit.get());
                return Response.status(Response.Status.OK).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}