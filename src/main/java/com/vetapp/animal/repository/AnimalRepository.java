package com.vetapp.animal.repository;

import com.vetapp.DataStore;
import com.vetapp.animal.entity.Animal;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@RequestScoped
public class AnimalRepository {

    private EntityManager em;

    @PersistenceContext(unitName = "VetApppPu")
    public void setEm(EntityManager em){
        this.em = em;
    }

    public List<Animal> findAll() {
        return em.createQuery("select c from Animal c", Animal.class).getResultList();
    }

    public void create(Animal animal) {

        em.persist(animal);
    }

    public Optional<Animal> find(Integer id) {
        return Optional.ofNullable(em.find(Animal.class, id));
    }

    public void update(Animal animal) {
        em.merge(animal);
    }

    public void delete(Animal animal) {
        em.remove(animal);
    }
}
