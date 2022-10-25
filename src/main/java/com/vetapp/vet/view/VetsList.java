package com.vetapp.vet.view;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@RequestScoped
@Named
public class VetsList {
    private VetService vetService;
    private List<Vet> vets;

    @Inject
    public VetsList(VetService vetService){
        this.vetService = vetService;
    }

    public List<Vet> getVets(){
        vets = vetService.findAll();
        return vets;
    }

    public String deleteAction(Vet vet){
        vetService.delete(vet);
        return "vet_list_view?faces-reedirect=true";
    }
}
