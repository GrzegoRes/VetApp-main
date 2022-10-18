package com.vetapp;

import com.vetapp.vet.entity.Role;
import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.service.VetService;
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

    @Inject
    public InitData(VetService vetService){
        this.vetService = vetService;
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
                .build();

        vetService.create(login1);
        vetService.create(login2);
        vetService.create(login3);
        vetService.create(login4);

    }

    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            return is.readAllBytes();
        }
    }
}
