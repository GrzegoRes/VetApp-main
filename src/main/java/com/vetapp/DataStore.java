package com.vetapp;

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
                            String.format("The character with id \"%d\" does not exist", vet.getLogin()));
                });
    }

    public void deleteVet(Vet vet) {
        vets.remove(vet);
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
}
