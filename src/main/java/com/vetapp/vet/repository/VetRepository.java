package com.vetapp.vet.repository;

import com.vetapp.DataStore;
import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.repository.AnimalRepository;
import com.vetapp.vet.dto.response1.GetVetsResponse;
import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Dependent
public class VetRepository {

    private EntityManager em;

    @PersistenceContext(unitName = "VetApppPu")
    public void setEm(EntityManager em){
        this.em = em;
    }

    public List<Vet> findAll() {
        return em.createQuery("select c from Vet c", Vet.class).getResultList();
    }

    public void create(Vet vet) {
        em.persist(vet);
    }

    public Optional<Vet> find(String login) {
        return Optional.ofNullable(em.find(Vet.class, login));
    }

    public void update(Vet vet) {
        em.merge(vet);
    }

    public void delete(Vet vet) {
        Vet vet1 = em.find(Vet.class, vet.getLogin());
        em.remove(vet1);
    }
}