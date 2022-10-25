package com.vetapp;

import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.entity.Animal;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.service.VisitService;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;

@ApplicationScoped
public class InitData {
    private final VetService vetService;
    private final VisitService visitService;

    @Inject
    public InitData(VetService vetService, VisitService visitService){
        this.vetService = vetService;
        this.visitService = visitService;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init(){

        Vet login1 = Vet.builder()
                .login("login1")
                .employmentDate(LocalDate.of(2022,10,18))
                .price(500)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/zereni.png"))
                .visits(new ArrayList<>())
                .build();

        Vet login2 = Vet.builder()
                .login("login2")
                .employmentDate(LocalDate.of(2021,9,11))
                .price(1500)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/uhlbrecht.png"))
                .visits(new ArrayList<>())
                .build();

        Vet login3 = Vet.builder()
                .login("login3")
                .employmentDate(LocalDate.of(2020,7,5))
                .price(1000)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/calvian.png"))
                .visits(new ArrayList<>())
                .build();

        Vet login4 = Vet.builder()
                .login("login4")
                .employmentDate(LocalDate.of(2022,3,8))
                .price(100)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/eloise.png"))
                .visits(new ArrayList<>())
                .build();

        var visit1 = Visit.builder()
                .id(1)
                .description("description1")
                .dateVisit(LocalDate.of(2022,10,25))
                .animal(Animal.CAT)
                .price(300)
                .vet(login1)
                .build();

        var visit2 = Visit.builder()
                .id(2)
                .description("description2")
                .dateVisit(LocalDate.of(2020,10,25))
                .animal(Animal.DOG)
                .price(150)
                .vet(login1)
                .build();

        vetService.create(login1);
        vetService.create(login2);
        vetService.create(login3);
        vetService.create(login4);

        vetService.saveAvatar(login1);
        vetService.saveAvatar(login2);
        vetService.saveAvatar(login3);
        vetService.saveAvatar(login4);

        visitService.create(visit1);
        visitService.create(visit2);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
