package com.vetapp;

import com.vetapp.animal.entity.Animal;
import com.vetapp.serialization.CloningUtility;
import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
public class DataStore {
    private Set<Vet> vets = new HashSet<>();
    private Set<Visit> visits = new HashSet<>();
    private Set<Animal> animals = new HashSet<>();

    /////////////////////////////////////
    ///          Vet
    ////////////////////////////////////

    public synchronized Optional<Vet> findVet(String login) {
        return vets.stream()
                .filter(character -> character.getLogin().equals(login))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized List<Vet> findAllVet() {
        return vets.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createUser(Vet vet) throws IllegalArgumentException {
        findVet(vet.getLogin()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", vet.getLogin()));
                },
                () -> vets.add(CloningUtility.clone(vet)));
    }

    public void updateVet(Vet vet) {
        findVet(vet.getLogin()).ifPresentOrElse(
                original -> {
                    vets.remove(original);
                    vets.add(CloningUtility.clone(vet));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The character with id \"%s\" does not exist", vet.getLogin()));
                });
    }

    public void deleteVet(Vet vet) {
        vets.remove(vet);
    }

    /////////////////////////////////////
    ///          Visit
    ////////////////////////////////////

    public synchronized List<Visit> findAllVisits() {
        return visits.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Visit> findVisit(Integer id) {
        return visits.stream()
                .filter(character -> character.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createVisit(Visit visit) throws IllegalArgumentException {
        findVisit(visit.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The user login \"%s\" is not unique", visit.getId()));
                },
                () -> visits.add(CloningUtility.clone(visit)));
    }

    public void updateVisit(Visit visit){
        findVisit(visit.getId()).ifPresentOrElse(
                original -> {
                    visits.remove(original);
                    visits.add(CloningUtility.clone(visit));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The character with id \"%d\" does not exist", visit.getId()));
                });
    }

    public void deleteVisit(Visit visit) {
        visits.remove(visit);
    }

    /////////////////////////////////////
    ///          ANIMAL
    ////////////////////////////////////

    public synchronized List<Animal> findAllAnimal() {
        return animals.stream()
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized Optional<Animal> findAnimal(Integer id) {
        return animals.stream()
                .filter(animal -> animal.getId().equals(id))
                .findFirst()
                .map(CloningUtility::clone);
    }

    public synchronized void createAnimal(Animal animal) throws IllegalArgumentException {
        findAnimal(animal.getId()).ifPresentOrElse(
                original -> {
                    throw new IllegalArgumentException(
                            String.format("The animal id \"%s\" is not unique", animal.getId()));
                },
                () -> animals.add(CloningUtility.clone(animal)));
    }

    public void updateAnimal(Animal animal) {
        findAnimal(animal.getId()).ifPresentOrElse(
                original -> {
                    animals.remove(original);
                    animals.add(CloningUtility.clone(animal));
                },
                () -> {
                    throw new IllegalArgumentException(
                            String.format("The character with id \"%d\" does not exist", animal.getId()));
                });
    }

    public void deleteAnimal(Animal animal) {
        animals.remove(animal);
    }
}
