package com.vetapp.vet.repository;

import com.vetapp.DataStore;
import com.vetapp.vet.entity.Vet;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Dependent
public class VetRepository {
    private DataStore dataStore;

    @Inject
    public VetRepository(DataStore dataStore){
        this.dataStore = dataStore;
    }

    public List<Vet> findAll() {
        return dataStore.findAllVet();
    }

    public void create(Vet vet) {
        dataStore.createUser(vet);
    }

    public Optional<Vet> find(String login) {
        return dataStore.findVet(login);
    }
}
