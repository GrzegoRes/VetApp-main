package com.vetapp.animal.service;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.repository.AnimalRepository;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AnimalService {
    private AnimalRepository animalRepository;
    private VisitRepository visitRepository;

    @Inject
    public AnimalService(AnimalRepository animalRepository,VisitRepository visitRepository){
        this.animalRepository = animalRepository;
        this.visitRepository = visitRepository;
    }

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    @Transactional
    public void create(Animal animal) {
        animalRepository.create(animal);
    }

    public Optional<Animal> find(Integer id) {
        return animalRepository.find(id);
    }

    @Transactional
    public void update(Animal animal) {
        animalRepository.update(animal);
    }

    @Transactional
    public void delete(Animal animal) {
        List<Visit> visits = visitRepository.findAllByAnimal(animal);
        visits.forEach(visitRepository::delete);
        animalRepository.delete(animal);
    }
}
