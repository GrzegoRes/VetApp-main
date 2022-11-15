package com.vetapp.visit.repository;

import com.vetapp.DataStore;
import com.vetapp.animal.entity.Animal;
import com.vetapp.serialization.CloningUtility;
import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestScoped
public class VisitRepository {

    private EntityManager em;

    @PersistenceContext(unitName = "VetApppPu")
   public void setEm(EntityManager em){
       this.em = em;
   }

    public void create(Visit visit) {
        em.persist(visit);
    }

    public void delete(Visit visit) {
        Visit visit1 = em.find(Visit.class, visit.getId());
        em.remove(visit1);
    }

    public void update(Visit visit) {
        em.merge(visit);
    }

    public Optional<Visit> find(Integer id) {
        return Optional.ofNullable(em.find(Visit.class,id));
    }

    public List<Visit> findAllByAnimal(Animal animal) {
        return em.createQuery("select v from Visit v where v.animal = :animal", Visit.class)
                .setParameter("animal", animal)
                .getResultList();
    }

    public List<Visit> findAllByVet(Vet vet) {
        return em.createQuery("select v from Visit v where v.vet = :vet", Visit.class)
                .setParameter("vet", vet)
                .getResultList();
    }
}
