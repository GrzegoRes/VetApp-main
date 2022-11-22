package com.vetapp.visit.service;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.repository.AnimalRepository;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class VisitService {
    private VisitRepository visitRepository;
    private AnimalRepository animalRepository;
    private VetRepository vetRepository;

    @Inject
    public VisitService(VisitRepository visitRepository, AnimalRepository animalRepository, VetRepository vetRepository){
        this.visitRepository = visitRepository;
        this.animalRepository = animalRepository;
        this.vetRepository = vetRepository;
    }


    public void create2(Visit visit) {
        visitRepository.create(visit);
    }

    //public void create(Visit visit) {
    //    visitRepository.create(visit);
//
    //    //Vet vet = visit.getVet();
    //    //vet.addVisits(visit);
//
    //    vetRepository.update(vet);
    //}


    public void update(Visit visit){
        visitRepository.update(visit);
    }

    public Optional<Visit> find(Integer id){
        return visitRepository.find(id);
    }


    public void delete(Visit visit){
        visitRepository.delete(visit);
    }

    public Optional<List<Visit>> findByAnimal(Integer idAnimal) {
        Optional<Animal> animal = animalRepository.find(idAnimal);
        if (animal.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(visitRepository.findAllByAnimal(animal.get()));
    }

    public Optional<List<Visit>> findByVet(String idVet) {
        Optional<Vet> vet = vetRepository.find(idVet);
        if (vet.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(visitRepository.findAllByVet(vet.get()));
    }
}