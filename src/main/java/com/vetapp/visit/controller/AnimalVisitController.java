package com.vetapp.visit.controller;
import com.vetapp.MimeTypes;
import com.vetapp.animal.dto.response1.GetVisitResponse;
import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.service.AnimalService;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.dto.request1.PutVisitAnimalRequest;
import com.vetapp.visit.dto.request1.PutVisitVetRequest;
import com.vetapp.visit.dto.response1.GetVisitsResponse;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.service.VisitService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/animals/{idAnimal}/visits")
public class AnimalVisitController {
    private AnimalService animalService;
    private VisitService visitService;
    private VetService vetService;

    public AnimalVisitController(){};

    @Inject
    void setAnimalService(AnimalService animalService, VetService vetService){
        this.animalService = animalService;
        this.vetService = vetService;
    }

    @Inject
    void setAnimalService(VisitService visitService){
        this.visitService = visitService;
    }

    @GET
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getVisits(@PathParam("idAnimal") Integer idAnimal){
        Optional<List<Visit>> visits = visitService.findByAnimal(idAnimal);
        if(visits.isPresent()){
            return Response.ok(GetVisitsResponse.entityToDtoMapper().apply(visits.get())).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getVisits(@PathParam("idAnimal") Integer idAnimal, @PathParam("id") Integer id){
        Optional<List<Visit>> visits = visitService.findByAnimal(idAnimal);
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
    public Response createVisit(@PathParam("idAnimal") Integer idAnimal, PutVisitVetRequest putVisitVetRequest){
        try {
            Optional<Animal> animal = animalService.find(idAnimal);
            Optional<Vet> vet = vetService.find(putVisitVetRequest.getIdVet());
            if (animal.isPresent()) {
                visitService.create2(Visit.builder()
                        .id(putVisitVetRequest.getId())
                        .description(putVisitVetRequest.getDescription())
                        .price(putVisitVetRequest.getPrice())
                        .dateVisit(putVisitVetRequest.getDateVisit())
                        .animal(animal.get())
                        .vet(vet.get())
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
    public Response updateVisit(@PathParam("idAnimal") Integer idAnimal, @PathParam("id") Integer id, PutVisitVetRequest putVisitVetRequest){
        Optional<Animal> animal = animalService.find(idAnimal);
        Optional<Vet> vet = vetService.find(putVisitVetRequest.getIdVet());
        if(animal.isPresent() && vet.isPresent()){
            visitService.update(Visit.builder()
                    .id(id)
                    .description(putVisitVetRequest.getDescription())
                    .price(putVisitVetRequest.getPrice())
                    .dateVisit(putVisitVetRequest.getDateVisit())
                    .animal(animal.get())
                    .vet(vet.get())
                    .build());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response deleteVisit(@PathParam("idAnimal") Integer idAnimal, @PathParam("id") Integer id){
        Optional<List<Visit>> visits = visitService.findByAnimal(idAnimal);
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