package com.vetapp.vet.service;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.repository.VetRepository;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
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

    @Resource(name = "avatars.dir")
    private String avatarsDir;

    @Inject
    public VetService(VetRepository vetRepository){
        this.vetRepository = vetRepository;
    }

    public Optional<Vet> find(String login) {
        return vetRepository.find(login);
    }

    public void create(Vet user) {
        vetRepository.create(user);
    }

    public List<Vet> findAll() {
        return vetRepository.findAll();
    }

    public void deleteAvatar(Vet vet) {
        //Path path = Path.of(avatarsDir, vet.getLogin());
        vet.setHaveAvatar(false);
        vet.setAvatar(new byte[0]);
        vetRepository.update(vet);
    }

    public void createAvatar(Vet vet, Part avatar) {
        if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
            Path path = Path.of(avatarsDir, avatar.getSubmittedFileName());
            try {
                //if (!Files.exists(path)) {
                //   Files.createFile(path);
                //    Files.write(path, avatar.getInputStream().readAllBytes(), StandardOpenOption.WRITE);
                //}
                //vet.setAvatarFileName(avatar.getSubmittedFileName());
                vet.setHaveAvatar(true);
                vet.setAvatar(avatar.getInputStream().readAllBytes());
                vetRepository.update(vet);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void updateAvatar(Vet vet, Part avatar) {
        System.out.println(avatarsDir);
        if (vet.isHaveAvatar()) {
            deleteAvatar(vet);
        }
        createAvatar(vet, avatar);
    }
}
