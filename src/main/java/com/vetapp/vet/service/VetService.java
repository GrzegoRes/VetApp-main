package com.vetapp.vet.service;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
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

    @Transactional
    public void create(Vet user) {
        vetRepository.create(user);
    }

    public List<Vet> findAll() {
        return vetRepository.findAll();
    }

    @Transactional
    public void delete(Vet vet) {
        List<Visit> visits = visitRepository.findAllByVet(vet);
        visits.forEach(visitRepository::delete);
        vetRepository.delete(vet);
    }

    @Transactional
    public void update(Vet vet) {
        vetRepository.update(vet);
    }
}
