package com.vetapp.animal.service;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.repository.AnimalRepository;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
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


    public void create(Animal animal) {
        animalRepository.create(animal);
    }

    public Optional<Animal> find(Integer id) {
        return animalRepository.find(id);
    }


    public void update(Animal animal) {
        animalRepository.update(animal);
    }


    public void delete(Animal animal) {
        List<Visit> visits = visitRepository.findAllByAnimal(animal);
        visits.forEach(visitRepository::delete);
        animalRepository.delete(animal);
    }
}
