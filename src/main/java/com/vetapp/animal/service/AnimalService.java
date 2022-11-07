package com.vetapp.animal.service;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.repository.AnimalRepository;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@NoArgsConstructor
public class AnimalService {
    private AnimalRepository animalRepository;

    @Inject
    public AnimalService(AnimalRepository animalRepository){
        this.animalRepository = animalRepository;
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
        animalRepository.delete(animal);
    }
}
