package com.vetapp.vet.service;

import com.vetapp.vet.entity.Vet;
import com.vetapp.vet.repository.VetRepository;
import com.vetapp.visit.repository.VisitRepository;
import lombok.NoArgsConstructor;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.servlet.http.Part;
import java.io.IOException;

@Stateless
@LocalBean
@NoArgsConstructor
public class VetAvatarService {
    private VetRepository vetRepository;

    @Resource(name = "avatars.dir")
    private String avatarsDir;

    @Inject
    public VetAvatarService(VetRepository vetRepository, VisitRepository visitRepository){
        this.vetRepository = vetRepository;
    }

    public void deleteAvatar(Vet vet) {
        //Path path = Path.of(avatarsDir, vet.getLogin()+".png");
        //try{
        //
        //    Files.deleteIfExists(path);
        //} catch (Exception ex){

        vet.setHaveAvatar(false); // zmienic i sprwadzać czy jest pustym bajtem
        vet.setAvatar(new byte[0]);
        vetRepository.update(vet);
    }

    public void createAvatar(Vet vet, Part avatar) throws IOException { //Obsłuzyc wyjatek
        vet.setAvatar(avatar.getInputStream().readAllBytes());
        vetRepository.update(vet);

        //if (avatar != null && !avatar.getSubmittedFileName().isEmpty()) {
        //    Path path = Path.of(avatarsDir, vet.getLogin()+".png");
        //    try {
        //        if (!Files.exists(path)) {
        //            Files.createFile(path);
        //            Files.write(path, avatar.getInputStream().readAllBytes(), StandardOpenOption.WRITE);
        //            vet.setHaveAvatar(true);
        //            vet.setAvatar();
        //            vetRepository.update(vet);
        //        }
        //    } catch (IOException e) {
        //        throw new RuntimeException(e.getMessage());
        //    }
        //}
    }

    public void updateAvatar(Vet vet, Part avatar) throws IOException {
        vet.setAvatar(avatar.getInputStream().readAllBytes());
        vetRepository.update(vet);
        //System.out.println(avatarsDir);
        //if (vet.isHaveAvatar()) {
        //    deleteAvatar(vet);
        //}
        //createAvatar(vet, avatar);
    }

    //public void saveAvatar(Vet vet){
    //    Path path = Path.of(avatarsDir, vet.getLogin()+".png");
//
    //    try {
    //        if (!Files.exists(path)) {
    //            Files.createFile(path);
    //            Files.write(path, vet.getAvatar(), StandardOpenOption.WRITE);
    //        }
    //    } catch (IOException e) {
    //        throw new RuntimeException(e.getMessage());
    //    }
    //}
}
