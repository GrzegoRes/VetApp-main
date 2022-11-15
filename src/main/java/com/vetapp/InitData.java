package com.vetapp;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import com.vetapp.animal.service.AnimalService;
import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.service.VisitService;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.control.RequestContextController;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;

@ApplicationScoped
public class InitData {
    private final VetService vetService;
    private final VisitService visitService;

    private final AnimalService animalService;

    private RequestContextController requestContextController;

    @Inject
    public InitData(VetService vetService, VisitService visitService, AnimalService animalService, RequestContextController requestContextController){
        this.vetService = vetService;
        this.visitService = visitService;
        this.animalService = animalService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init(){
        requestContextController.activate();// start request scope in order to inject request scoped repositories

        Animal animal1 = Animal.builder()
                .id(1)
                .name("animal1")
                .weight(10)
                .age(4)
                .typeAnimal(TypeAnimal.hamster)
                .build();

        Animal animal2 = Animal.builder()
                .id(2)
                .name("animal2")
                .weight(15)
                .age(2)
                .typeAnimal(TypeAnimal.dog)
                .build();

        Animal animal3 = Animal.builder()
                .id(3)
                .name("animal3")
                .weight(12)
                .age(8)
                .typeAnimal(TypeAnimal.cat)
                .build();

        animalService.create(animal1);
        animalService.create(animal2);
        animalService.create(animal3);

        Vet login1 = Vet.builder()
                .login("login1")
                .employmentDate(LocalDate.of(2022,10,18))
                .price(500)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/zereni.png"))
                //.visits(new ArrayList<>())
                .build();

        Vet login2 = Vet.builder()
                .login("login2")
                .employmentDate(LocalDate.of(2021,9,11))
                .price(1500)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/uhlbrecht.png"))
                //.visits(new ArrayList<>())
                .build();

        Vet login3 = Vet.builder()
                .login("login3")
                .employmentDate(LocalDate.of(2020,7,5))
                .price(1000)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/calvian.png"))
                //.visits(new ArrayList<>())
                .build();

        Vet login4 = Vet.builder()
                .login("login4")
                .employmentDate(LocalDate.of(2022,3,8))
                .price(100)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/eloise.png"))
                //.visits(new ArrayList<>())
                .build();

        var visit1 = Visit.builder()
                .id(1)
                .description("description1")
                .dateVisit(LocalDate.of(2022,10,25))
                .animal(animal1)
                .price(300)
                .vet(login1)
                .build();

        var visit2 = Visit.builder()
                .id(2)
                .description("description2")
                .dateVisit(LocalDate.of(2020,10,25))
                .animal(animal1)
                .price(150)
                .vet(login1)
                .build();

        vetService.create(login1);
        vetService.create(login2);
        vetService.create(login3);
        vetService.create(login4);

        //vetService.saveAvatar(login1);
        //vetService.saveAvatar(login2);
        //vetService.saveAvatar(login3);
        //vetService.saveAvatar(login4);

        visitService.create2(visit1);
        visitService.create2(visit2);

        requestContextController.deactivate();
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
