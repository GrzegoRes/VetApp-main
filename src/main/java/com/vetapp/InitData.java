package com.vetapp;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import com.vetapp.animal.service.AnimalService;
import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
<<<<<<< Updated upstream
=======
import com.vetapp.visit.entity.Visit;
import com.vetapp.visit.service.VisitService;
>>>>>>> Stashed changes
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.io.InputStream;
import java.time.LocalDate;

@ApplicationScoped
public class InitData {
    private final VetService vetService;

    private final AnimalService animalService;
    @Inject
<<<<<<< Updated upstream
    public InitData(VetService vetService){
        this.vetService = vetService;
=======
    public InitData(VetService vetService, VisitService visitService, AnimalService animalService){
        this.vetService = vetService;
        this.visitService = visitService;
        this.animalService = animalService;
>>>>>>> Stashed changes
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    private synchronized void init(){
<<<<<<< Updated upstream
=======

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

>>>>>>> Stashed changes
        Vet login1 = Vet.builder()
                .login("login1")
                .employmentDate(LocalDate.of(2022,10,18))
                .price(500)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/zereni.png"))
                .build();

        Vet login2 = Vet.builder()
                .login("login2")
                .employmentDate(LocalDate.of(2021,9,11))
                .price(1500)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/uhlbrecht.png"))
                .build();

        Vet login3 = Vet.builder()
                .login("login3")
                .employmentDate(LocalDate.of(2020,7,5))
                .price(1000)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/calvian.png"))
                .build();

        Vet login4 = Vet.builder()
                .login("login4")
                .employmentDate(LocalDate.of(2022,3,8))
                .price(100)
                .role(Role.USER)
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/eloise.png"))
<<<<<<< Updated upstream
=======
                .visits(new ArrayList<>())
                .build();

        var visit1 = Visit.builder()
                .id(1)
                .description("description1")
                .dateVisit(LocalDate.of(2022,10,25))
                .animal(com.vetapp.visit.entity.Animal.CAT)
                .price(300)
                .vet(login1)
                .build();

        var visit2 = Visit.builder()
                .id(2)
                .description("description2")
                .dateVisit(LocalDate.of(2020,10,25))
                .animal(com.vetapp.visit.entity.Animal.CAT)
                .price(150)
                .vet(login1)
>>>>>>> Stashed changes
                .build();

        animalService.create(animal1);
        animalService.create(animal2);
        animalService.create(animal3);

        vetService.create(login1);
        vetService.create(login2);
        vetService.create(login3);
        vetService.create(login4);

        vetService.saveAvatar(login1);
        vetService.saveAvatar(login2);
        vetService.saveAvatar(login3);
        vetService.saveAvatar(login4);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
