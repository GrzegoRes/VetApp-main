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
        Path path = Path.of(avatarsDir, vet.getLogin());
        try {
            if (Files.deleteIfExists(path)) {
                vet.setHaveAvatar(false);
                vet.setAvatar(new byte[0]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void createAvatar(Vet vet, Part avatar) {
        if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
            Path path = Path.of(avatarsDir, avatar.getSubmittedFileName());
            try {
                if (!Files.exists(path)) {
                    Files.createFile(path);
                    Files.write(path, avatar.getInputStream().readAllBytes(), StandardOpenOption.WRITE);
                }
                vet.setHaveAvatar(true);
                vet.setAvatar(avatar.getInputStream().readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    public void updateAvatar(Vet vet, Part avatar) {
        System.out.println(avatarsDir);
        if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
            if (vet.isHaveAvatar()) {
                deleteAvatar(vet);
            }
            createAvatar(vet, avatar);
        }
    }
}
