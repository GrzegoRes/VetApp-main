package com.vetapp.visit.service;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class VisitService {
    private VisitRepository visitRepository;
    private VetRepository vetRepository;

    @Inject
    public VisitService(VisitRepository visitRepository, VetRepository vetRepository){
        this.visitRepository = visitRepository;
        this.vetRepository = vetRepository;
    }

    public void create(Visit visit) {
        visitRepository.create(visit);

        Vet vet = visit.getVet();
        vet.addVisits(visit);

        vetRepository.update(vet);
    }

    public void update(Visit visit){
        visitRepository.update(visit);
    }

    public Optional<Visit> find(Integer id){
        return visitRepository.find(id);
    }

    public void delete(Visit visit){
        visitRepository.delete(visit);
    }
}
