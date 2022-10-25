package com.vetapp.visit.view;

import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.service.VisitService;
import lombok.Getter;
import lombok.Setter;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;

@RequestScoped
@Named
public class VisitView implements Serializable {

    private final VisitService visitService;

    @Getter
    private Visit visit;

    @Setter
    @Getter
    private Integer id;

    @Inject
    public VisitView(VisitService visitService) {
        this.visitService = visitService;
    }

    public void init() throws IOException {
        Optional<Visit> visit = visitService.find(id);
        if (visit.isPresent()) {
            this.visit = visit.get();
        } else {
            FacesContext.getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Present not found!");
        }
    }
}
