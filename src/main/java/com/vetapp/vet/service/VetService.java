package com.vetapp.vet.service;

import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Stateless
@LocalBean
@NoArgsConstructor
public class VetService {
    private VetRepository vetRepository;
    private VisitRepository visitRepository;

    @Inject
    public VetService(VetRepository vetRepository, VisitRepository visitRepository){
        this.vetRepository = vetRepository;
        this.visitRepository = visitRepository;
    }

    public Optional<Vet> find(String login) {
        return vetRepository.find(login);
    }


    public void create(Vet user) {
        user.setRoles(List.of(Role.USER));
        vetRepository.create(user);
    }

    public List<Vet> findAll() {
        return vetRepository.findAll();
    }


    public void delete(Vet vet) {
        List<Visit> visits = visitRepository.findAllByVet(vet);
        visits.forEach(visitRepository::delete);
        vetRepository.delete(vet);
    }


    public void update(Vet vet) {
        vetRepository.update(vet);
    }
}
