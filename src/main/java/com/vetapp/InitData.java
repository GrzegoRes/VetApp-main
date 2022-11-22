package com.vetapp;

import com.vetapp.animal.entity.Animal;
import com.vetapp.animal.entity.TypeAnimal;
import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.visit.entity.Visit;
import lombok.SneakyThrows;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

@Singleton
@Startup
public class InitData {
    private EntityManager em;
    @PersistenceContext(unitName = "VetApppPu")
    public void setEm(EntityManager em){
        this.em = em;
    }

    private Pbkdf2PasswordHash pbkdf;

    @Inject
    public void setPbkdf(Pbkdf2PasswordHash pbkdf) {
        this.pbkdf = pbkdf;
    }

    public InitData() {}

    @PostConstruct
    private synchronized void init(){
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

        em.persist(animal1);
        em.persist(animal2);
        em.persist(animal3);

        Vet login1 = Vet.builder()
                .login("login1")
                .employmentDate(LocalDate.of(2022,10,18))
                .price(500)
                .roles(List.of(Role.USER, Role.ADMIN))
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/zereni.png"))
                //.visits(new ArrayList<>())
                .password(pbkdf.generate("login1".toCharArray()))
                .build();

        Vet login2 = Vet.builder()
                .login("login2")
                .employmentDate(LocalDate.of(2021,9,11))
                .price(1500)
                .roles(List.of(Role.USER))
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/uhlbrecht.png"))
                //.visits(new ArrayList<>())
                .password(pbkdf.generate("login2".toCharArray()))
                .build();

        Vet login3 = Vet.builder()
                .login("login3")
                .employmentDate(LocalDate.of(2020,7,5))
                .price(1000)
                .roles(List.of(Role.USER))
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/calvian.png"))
                //.visits(new ArrayList<>())
                .password(pbkdf.generate("login3".toCharArray()))
                .build();

        Vet login4 = Vet.builder()
                .login("login4")
                .employmentDate(LocalDate.of(2022,3,8))
                .price(100)
                .roles(List.of(Role.USER))
                .isHaveAvatar(true)
                .avatar(getResourceAsByteArray("avatar/eloise.png"))
                //.visits(new ArrayList<>())
                .password(pbkdf.generate("login4".toCharArray()))
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

        em.persist(login1);
        em.persist(login2);
        em.persist(login3);
        em.persist(login4);

        //vetService.saveAvatar(login1);
        //vetService.saveAvatar(login2);
        //vetService.saveAvatar(login3);
        //vetService.saveAvatar(login4);

        em.persist(visit1);
        em.persist(visit2);
    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
