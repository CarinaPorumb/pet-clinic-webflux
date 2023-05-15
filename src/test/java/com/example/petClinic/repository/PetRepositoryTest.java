package com.example.petClinic.repository;

import com.example.petClinic.config.DatabaseConfig;
import com.example.petClinic.domain.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

@DataR2dbcTest
@Import(DatabaseConfig.class)
class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    @Test
    void saveNewPet() {
        petRepository.save(createTestPet())
                .subscribe(pet -> {
                    System.out.println(pet.toString());
                });
    }

    Pet createTestPet() {
        return Pet.builder()
                .name("TestPet")
                .petType("Bird")
                .age(3)
                .weight(10.0)
                .build();
    }

}