package com.vetapp.visit.repository;

import com.vetapp.DataStore;
import com.vetapp.animal.entity.Animal;
import com.vetapp.serialization.CloningUtility;
import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Dependent
public class VisitRepository {
    private DataStore dataStore;

    @Inject
    public VisitRepository(DataStore dataStore){
        this.dataStore = dataStore;
    }
    public void create(Visit visit) {
        dataStore.createVisit(visit);
    }

    public void delete(Visit visit) {
        dataStore.deleteVisit(visit);
    }

    public void update(Visit visit) {
        dataStore.updateVisit(visit);
    }

    public Optional<Visit> find(Integer id) {
        return dataStore.findVisit(id);
    }

    public List<Visit> findAllByAnimal(Animal animal) {
        return dataStore.findAllVisits().stream()
                .filter(visit -> visit.getAnimal().equals(animal))
                .map(CloningUtility::clone)
                .collect(Collectors.toList());
    }
}
