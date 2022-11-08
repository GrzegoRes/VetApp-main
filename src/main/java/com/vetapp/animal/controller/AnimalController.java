package com.vetapp.animal.controller;

import com.vetapp.MimeTypes;
import com.vetapp.animal.dto.request1.PutAnimalRequest;
import com.vetapp.animal.dto.response1.GetAnimalResponse;
import com.vetapp.animal.dto.response1.GetAnimalsResponse;
import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import com.vetapp.animal.service.AnimalService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/animals")
public class AnimalController {
    private AnimalService animalService;

    public AnimalController(){};

    @Inject
    void setAnimalService(AnimalService animalService){
        this.animalService = animalService;
    }


    @GET
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getAnimals(){
        try{
            return Response.ok(GetAnimalsResponse.entityToDtoMapper().apply(animalService.findAll())).build();
        }catch (Exception ex) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getAnimals(@PathParam("id") Integer id){
        Optional<Animal> animal = animalService.find(id);
        if (animal.isPresent()) {
            return Response
                    .ok(GetAnimalResponse.entityToDtoMapper().apply(animal.get()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response updateAnimal(@PathParam("id") Integer id, PutAnimalRequest request){
        Optional<Animal> animal = animalService.find(id);
        if(animal.isPresent()){
            animalService.update(Animal.builder()
                    .id(id)
                    .name(request.getName())
                    .weight(request.getWeight())
                    .age(request.getAge())
                    .typeAnimal(TypeAnimal.fromString(request.getTypeAnimal()))
                    .build());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response createAnimal(PutAnimalRequest request){
        try {
            animalService.create(Animal.builder()
                    .id(request.getId())
                    .name(request.getName())
                    .weight(request.getWeight())
                    .age(request.getAge())
                    .typeAnimal(TypeAnimal.fromString(request.getTypeAnimal()))
                    .build());
            return Response.status(Response.Status.CREATED).build();
        }catch (IllegalArgumentException ex){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response deleteAnimal(@PathParam("id") Integer id){
        Optional<Animal> animal = animalService.find(id);
        if(animal.isPresent()){
            animalService.delete(animal.get());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}
