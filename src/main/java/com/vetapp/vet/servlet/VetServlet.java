package com.vetapp.vet.servlet;

import com.vetapp.MimeTypes;
import com.vetapp.ServletUtility;
import com.vetapp.vet.dto.GetVetResponse;
import com.vetapp.vet.dto.GetVetsResponse;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;

import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = VetServlet.Paths.USERS +  "/*")
public class VetServlet extends HttpServlet {
    private VetService vetService;

    @Inject
    public VetServlet(VetService vetService) {
        this.vetService = vetService;
    }

    public static class Paths {
        public static final String USERS = "/api/vets";
    }

    public static class Patterns {
        public static final String USERS = "^/?$";
        public static final String USER = "^/[a-zA-Z0-9]+/?$";
    }

    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.USERS.equals(servletPath)) {
            if (path.matches(Patterns.USER)) {
                getVet(request, response);
                return;
            } else if (path.matches(Patterns.USERS)) {
                getVets(request, response);
                return;
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void getVet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = ServletUtility.parseRequestPath(request).replaceAll("/", "");
        Optional<Vet> character = vetService.find(login);
        if (character.isPresent()) {
            response.setContentType(MimeTypes.APPLICATION_JSON);
            response.getWriter()
                    .write(jsonb.toJson(GetVetResponse.entityToDtoMapper().apply(character.get())));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void getVets(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(MimeTypes.APPLICATION_JSON);
        response.getWriter()
                .write(jsonb.toJson(GetVetsResponse.entityToDtoMapper().apply(vetService.findAll())));
    }
}

