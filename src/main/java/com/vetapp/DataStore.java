package com.vetapp;

import com.vetapp.serialization.CloningUtility;
import com.vetapp.vet.entity.Vet;
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


    public void update(Vet vet) {
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
}
