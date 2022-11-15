package com.vetapp.vet.view;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.entity.Visit;
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
import java.util.Optional;

@SessionScoped
@Named
public class VetView implements Serializable {

    private final VetService vetService;
    private final VisitService visitService;

    @Getter
    private Vet vet;

    @Setter
    @Getter
    private String login;

    @Inject
    public VetView(VetService vetService, VisitService visitService) {
        this.vetService = vetService;
        this.visitService = visitService;
    }


    public void init() throws IOException {
        Optional<Vet> vet = vetService.find(login);
        if (vet.isPresent()) {
            this.vet = vet.get();
            this.login = this.vet.getLogin();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Not found!");
        }
    }

    public String deleteAction(Visit visit){
        visitService.delete(visit);
        //this.vet.deleteVisits(visit);
        vetService.update(this.vet);

        return "vet_view.xhtml?login=" + vet.getLogin() + "&faces-redirect=true";
    }
}
