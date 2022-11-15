package com.vetapp.visit.view;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.model.VisitCreateModel;
import com.vetapp.visit.service.VisitService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Optional;

@SessionScoped
@Named
public class VisitCreate implements Serializable {

    private final VisitService visitService;

    private final VetService vetService;

    @Getter
    private Vet vet;

    @Getter
    private VisitCreateModel visitCreateModel;

    @Setter
    @Getter
    private String login;

    @Inject
    public VisitCreate(VisitService visitService, VetService vetService) {
        this.visitService = visitService;
        this.vetService = vetService;
        this.visitCreateModel = new VisitCreateModel();
    }

    public void init() throws IOException {
        Optional<Vet> vet = vetService.find(login);
        if (vet.isPresent()) {
            this.vet = vet.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Not found!");
        }
    }

    public String saveAction() {
        try {
            Visit visit = Visit.builder()
                    .id(Integer.parseInt(visitCreateModel.getId()))
                    .price(Double.parseDouble(visitCreateModel.getPrice()))
                    .description(visitCreateModel.getDescription())
                    //.vet(vetService.find(login).get())
                    .build();

            visitService.create2(visit);

            //return "vet_view.xhtml?login=" + visit.getVet().getLogin() + "&faces-redirect=true";
            return "vet_list_view?faces-reedirect=true";
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            return "visit_create.xhtml?login=" + login + "&faces-redirect=true";
        }
    }
}