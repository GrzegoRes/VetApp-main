package com.vetapp.visit.view;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.model.VisitCreateModel;
import com.vetapp.visit.model.VisitEditModel;
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
import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@SessionScoped
@Named
public class VisitEdit implements Serializable {

    private final VisitService visitService;

    private final VetService vetService;

    @Getter
    private Visit visit;
    private Vet vet;

    @Getter
    private VisitEditModel visitEditModel;

    @Setter
    @Getter
    private Integer id;

    @Inject
    public VisitEdit(VisitService visitService, VetService vetService) {
        this.visitService = visitService;
        this.vetService = vetService;
    }

    public void init() throws IOException {
        Optional<Visit> visit = visitService.find(id);
        if (visit.isPresent()) {
            this.visit = visit.get();
            this.vet = vetService.find(this.visit.getVet().getLogin()).get();
            this.visitEditModel = VisitEditModel.builder()
                    .id(this.visit.getId().toString())
                    .price(Double.toString(this.visit.getPrice()))
                    .login(vet.getLogin())
                    //.dateVisit(this.visit.getDateVisit().toString())
                    .description(this.visit.getDescription())
                    //.animal(this.visit.getAnimal().toString())
                    .build();

        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Not found!");
        }
    }


    public String updateAction() {
        try {
            Visit visit = Visit.builder()
                    .id(Integer.parseInt(visitEditModel.getId()))
                    .price(Double.parseDouble(visitEditModel.getPrice()))
                    //.dateVisit(LocalDate.parse(visitEditModel.getDateVisit()))
                    //.animal(Enum.valueOf(Animal.class,visitEditModel.getAnimal()))
                    .description(visitEditModel.getDescription())
                    .vet(vetService.find(visitEditModel.getLogin()).get())
                    .build();

            visitService.delete(this.visit);
            this.vet.deleteVisits(this.visit);
            vetService.update(this.vet);

            visitService.create(visit);


            return "vet_list_view?faces-reedirect=true";
        } catch (IllegalArgumentException | NoSuchElementException | NullPointerException e) {
            return "visit_create.xhtml?login=" + this.vet.getLogin() + "&faces-redirect=true";
        }
    }
}