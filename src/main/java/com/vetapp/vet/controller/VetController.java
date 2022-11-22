package com.vetapp.vet.controller;

import com.vetapp.MimeTypes;
import com.vetapp.animal.dto.request1.PutAnimalRequest;
import com.vetapp.animal.dto.response1.GetAnimalResponse;
import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import com.vetapp.vet.dto.request1.PutVetRequest;
import com.vetapp.vet.dto.response1.GetVetResponse;
import com.vetapp.vet.dto.response1.GetVetsResponse;
import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/vets")
public class VetController {
    private VetService vetService;

    public VetController(){};

    @EJB
    void setVetService(VetService vetService){
        this.vetService = vetService;
    }

    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public void setPbkdf(Pbkdf2PasswordHash pbkdf){
        this.pbkdf = pbkdf;
    }

    @GET
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getVets(){
        try{
            return Response.ok(GetVetsResponse.entityToDtoMapper().apply(vetService.findAll())).build();
        }catch (Exception ex) {
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }

    @GET
    @Path("{login}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response getVet(@PathParam("login") String login){
        Optional<Vet> vet = vetService.find(login);
        if (vet.isPresent()) {
            return Response
                    .ok(GetVetResponse.entityToDtoMapper().apply(vet.get()))
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    @Path("{login}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response updateVet(@PathParam("login") String login, PutVetRequest request){
        Optional<Vet> vet = vetService.find(login);
        if(vet.isPresent()){
            vetService.update(Vet.builder()
                    .login(login)
                    .employmentDate(request.getEmploymentDate())
                    .price(request.getPrice())
                    .roles(vet.get().getRoles())
                    .build());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response createAnimal(PutVetRequest request){
        try {
            vetService.create(Vet.builder()
                    .login(request.getLogin())
                    .employmentDate(request.getEmploymentDate())
                    .price(request.getPrice())
                    .build());
            return Response.status(Response.Status.CREATED).build();
        }catch (IllegalArgumentException ex){
            return Response.status(Response.Status.CONFLICT).build();
        }
    }

    @DELETE
    @Path("{login}")
    @Produces(MimeTypes.APPLICATION_JSON)
    public Response deleteAnimal(@PathParam("login") String login){
        Optional<Vet> vet = vetService.find(login);
        if(vet.isPresent()){
            vetService.delete(vet.get());
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}