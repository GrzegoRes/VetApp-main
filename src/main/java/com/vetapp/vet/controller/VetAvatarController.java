package com.vetapp.vet.controller;

import com.vetapp.MimeTypes;
import com.vetapp.vet.dto.response1.GetVetResponse;
import com.vetapp.vet.dto.response1.GetVetsResponse;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Optional;

@Path("/vet/{login}/avatar")
public class VetAvatarController {
    private VetService vetService;

    public VetAvatarController(){};


    @EJB
    void setVetService(VetService vetService){
        this.vetService = vetService;
    }

    @GET
    @Produces(MimeTypes.IMAGE_PNG)
    public Response getAvatar(@PathParam("login") String login){
        Optional<Vet> vet = vetService.find(login);
        if (vet.isPresent()) {
            return Response
                    .ok(vet.get().getAvatar())
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @DELETE
    public Response deleteAvatar(@PathParam("login") String login){
        Optional<Vet> vet = vetService.find(login);
        if(vet.isPresent()){
            vetService.delete(vet.get());
            return Response
                    .ok()
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }

    @PUT
    //@Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateAvatar(@PathParam("login") String login){
        Optional<Vet> vet = vetService.find(login);
        if(vet.isPresent()){
            vetService.update(vet.get());
            return Response
                    .ok()
                    .build();
        } else {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
    }
}
