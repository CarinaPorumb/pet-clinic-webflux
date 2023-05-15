package com.example.petClinic.startup;

import com.example.petClinic.domain.Pet;
import com.example.petClinic.repository.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class Startup implements CommandLineRunner {

    private final PetRepository petRepository;

    @Override
    public void run(String... args) throws Exception {

        loadData();
        petRepository.count().subscribe(count -> {
            System.out.println("Count is: " + count);
        });
    }


    private void loadData() {

        petRepository.count().subscribe(count -> {
            if (count == 0) {
                Pet pet1 = Pet.builder()
                        .name("Rocco")
                        .petType("Cat")
                        .age(2)
                        .weight(4.5)
                        .birthdate(LocalDate.now())
                        .build();

                Pet pet2 = Pet.builder()
                        .name("Yoda")
                        .petType("Bird")
                        .age(5)
                        .weight(0.8)
                        .birthdate(LocalDate.now())
                        .build();

                Pet pet3 = Pet.builder()
                        .name("Nyx")
                        .petType("Dog")
                        .age(3)
                        .weight(35.0)
                        .birthdate(LocalDate.now())
                        .build();

                petRepository.save(pet1).subscribe();
                petRepository.save(pet2).subscribe();
                petRepository.save(pet3).subscribe();
            }
        });
    }

}