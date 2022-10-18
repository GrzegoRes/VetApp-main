package com.vetapp.vet.servlet;


import com.vetapp.MimeTypes;
import com.vetapp.ServletUtility;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.xml.registry.infomodel.User;
import java.io.IOException;
import java.util.Optional;

@WebServlet(urlPatterns = VetAvatarServlet.Paths.AVATARS + "/*")
@MultipartConfig(maxFileSize = 200 * 1024)
public class VetAvatarServlet extends HttpServlet {
    private VetService vetService;

    @Inject
    public VetAvatarServlet(VetService vetService) {
        this.vetService = vetService;
    }

    public static class Paths {
        public static final String AVATARS = "/api/avatars";
    }

    public static class Patterns {
        public static final String AVATAR = "^/[a-zA-Z0-9]+/?$";
    }

    static class Parameters {
        static final String AVATAR = "avatar";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.AVATARS.equals(servletPath)) {
            if (path.matches(Patterns.AVATAR)) {
                getAvatar(request, response);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private void getAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String path = ServletUtility.parseRequestPath(request);
        String login = path.replaceAll("/", "");
        Optional<Vet> vet = vetService.find(login);
        System.out.println(vet.get().getLogin());
        if (vet.isPresent()) {
            byte[] avatar = vet.get().getAvatar();
            response.addHeader(HttpHeaders.CONTENT_TYPE, MimeTypes.IMAGE_PNG);
            response.setContentLength(avatar.length);
            response.getOutputStream().write(avatar);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();

        if (Paths.AVATARS.equals(servletPath) && path.matches(Patterns.AVATAR)) {
            deleteAvatar(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void deleteAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String path = ServletUtility.parseRequestPath(request);
        String login = path.replaceAll("/", "");
        Optional<Vet> vet = vetService.find(login);
        if(vet.isPresent()) {
            vetService.deleteAvatar(vet.get());
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();

        if (Paths.AVATARS.equals(servletPath) && path.matches(Patterns.AVATAR)) {
            putAvatar(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void putAvatar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        String login = path.replaceAll("/", "");
        Optional<Vet> vet = vetService.find(login);

        if (vet.isPresent()) {
            vetService.updateAvatar(vet.get(), request.getPart(Parameters.AVATAR));
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String path = ServletUtility.parseRequestPath(request);
        String servletPath = request.getServletPath();

        if (Paths.AVATARS.equals(servletPath) && path.matches(Patterns.AVATAR)) {
            postAvatarForUser(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void postAvatarForUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = ServletUtility.parseRequestPath(request);
        String login = path.replaceAll("/", "");
        Optional<Vet> vet = vetService.find(login);

        if (vet.isPresent()) {
            if (!vet.get().isHaveAvatar()) {
                vetService.createAvatar(vet.get(), request.getPart(Parameters.AVATAR));
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
