package com.vetapp.animal.repository;

import com.vetapp.DataStore;
import com.vetapp.animal.entity.Animal;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class AnimalRepository {
    private DataStore dataStore;

    @Inject
    public AnimalRepository(DataStore dataStore){
        this.dataStore = dataStore;
    }


    public List<Animal> findAll() {
        return dataStore.findAllAnimal();
    }

    public void create(Animal animal) {
        dataStore.createAnimal(animal);
    }

    public Optional<Animal> find(Integer id) {
        return dataStore.findAnimal(id);
    }

    public void update(Animal animal) {
        dataStore.updateAnimal(animal);
    }

    public void delete(Animal animal) {
        dataStore.deleteAnimal(animal);
    }
}
