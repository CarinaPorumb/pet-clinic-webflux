package com.example.petClinic.startup;

import com.example.petClinic.domain.Owner;
import com.example.petClinic.domain.Pet;
import com.example.petClinic.domain.Vet;
import com.example.petClinic.domain.Visit;
import com.example.petClinic.repository.OwnerRepository;
import com.example.petClinic.repository.PetRepository;
import com.example.petClinic.repository.VetRepository;
import com.example.petClinic.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class Startup implements CommandLineRunner {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;
    private final VetRepository vetRepository;
    private final VisitRepository visitRepository;

    @Override
    public void run(String... args) {
        loadPetData();
        loadOwnerData();
        loadVetData();
        loadVisitData();

    }

    private void loadPetData() {
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

    private void loadOwnerData() {
        ownerRepository.count().subscribe(count -> {
            if (count == 0) {
                Owner owner1 = Owner.builder()
                        .name("Owner 1")
                        .address("Florence")
                        .telephone("01234")
                        .build();

                Owner owner2 = Owner.builder()
                        .name("Owner 2")
                        .address("Sintra")
                        .telephone("12345")
                        .build();

                Owner owner3 = Owner.builder()
                        .name("Owner 3")
                        .address("Ibiza")
                        .telephone("001122")
                        .build();

                ownerRepository.save(owner1).subscribe();
                ownerRepository.save(owner2).subscribe();
                ownerRepository.save(owner3).subscribe();
            }
        });
    }

    private void loadVetData() {
        vetRepository.count().subscribe(count -> {
            if (count == 0) {
                Vet vet1 = Vet.builder()
                        .name("Vet 1")
                        .speciality("Surgery")
                        .build();

                Vet vet2 = Vet.builder()
                        .name("Vet 2")
                        .speciality("Nutrition")
                        .build();

                Vet vet3 = Vet.builder()
                        .name("Vet 3")
                        .speciality("Radiology")
                        .build();

                Vet vet4 = Vet.builder()
                        .name("Vet 4")
                        .speciality("Toxicology")
                        .build();

                vetRepository.save(vet1).subscribe();
                vetRepository.save(vet2).subscribe();
                vetRepository.save(vet3).subscribe();
                vetRepository.save(vet4).subscribe();
            }
        });
    }

    private void loadVisitData() {
        visitRepository.count().subscribe(count -> {
            if (count == 0) {
                Visit visit1 = Visit.builder()
                        .diagnosis("Cold")
                        .price(85)
                        .date(LocalDate.now())
                        .build();


                Visit visit2 = Visit.builder()
                        .diagnosis("Kidney stones")
                        .price(310)
                        .date(LocalDate.now())
                        .build();

                visitRepository.save(visit1).subscribe();
                visitRepository.save(visit2).subscribe();
            }
        });
    }

}