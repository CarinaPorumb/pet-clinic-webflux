package com.example.petClinic.controller;

import com.example.petClinic.domain.Pet;
import com.example.petClinic.model.PetDTO;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.example.petClinic.controller.PetController.PET_PATH;
import static com.example.petClinic.controller.PetController.PET_PATH_ID;
import static com.example.petClinic.repository.PetRepositoryTest.createTestPet;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AutoConfigureWebTestClient
@SpringBootTest
class PetControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(20)
    void deletePet() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(PET_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deletePetNotFound() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete().uri(PET_PATH_ID, 10)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(2)
    void listPets() {
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get().uri(PET_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

    @Test
    @Order(1)
    void getPetById() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(PET_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(PetDTO.class);
    }

    @Test
    void getPetByIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(PET_PATH_ID, 10)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createNewPet() {
        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(PET_PATH)
                .body(Mono.just(createTestPet()), PetDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8082/api/v2/pet/5");
    }

    @Test
    void createNewPetBadRequest() {
        Pet testPet = createTestPet();
        testPet.setName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(PET_PATH)
                .body(Mono.just(testPet), PetDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void updatePet() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(PET_PATH_ID, 1)
                .body(Mono.just(createTestPet()), PetDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updatePetNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(PET_PATH_ID, 10)
                .body(Mono.just(createTestPet()), PetDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updatePetBadRequest() {
        Pet testPet = createTestPet();
        testPet.setName(" ");

        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(PET_PATH_ID, 1)
                .body(Mono.just(testPet), PetDTO.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    @Order(5)
    void patchPet() {
        webTestClient.mutateWith(mockOAuth2Login())
                .patch().uri(PET_PATH_ID, 1)
                .body(Mono.just(createTestPet()), PetDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void patchPetNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .patch().uri(PET_PATH_ID, 10)
                .body(Mono.just(createTestPet()), PetDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

}