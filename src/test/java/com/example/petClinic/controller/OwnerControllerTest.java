package com.example.petClinic.controller;

import com.example.petClinic.domain.Owner;
import com.example.petClinic.model.OwnerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static com.example.petClinic.controller.OwnerController.OWNER_PATH;
import static com.example.petClinic.controller.OwnerController.OWNER_PATH_ID;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@AutoConfigureWebTestClient
@SpringBootTest
class OwnerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    void listOwners() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(OWNER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

    @Test
    void getOwnerById() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(OWNER_PATH_ID, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(OwnerDTO.class);
    }

    @Test
    void getOwnerByIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(OWNER_PATH_ID, 10)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createNewOwner() {
        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(OWNER_PATH)
                .body(Mono.just(createTestOwner()), OwnerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8082/api/v2/owner/4");
    }

    @Test
    void createNewOwnerBadRequest() {
        Owner testOwner = createTestOwner();
        testOwner.setName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(OWNER_PATH)
                .body(Mono.just(testOwner), OwnerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    void updateOwner() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(OWNER_PATH_ID, 1)
                .body(Mono.just(createTestOwner()), OwnerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void updateOwnerNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(OWNER_PATH_ID, 10)
                .body(Mono.just(createTestOwner()), OwnerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void updateOwnerBadRequest() {
        Owner testOwner = createTestOwner();
        testOwner.setName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(OWNER_PATH_ID, 1)
                .body(Mono.just(testOwner), OwnerDTO.class)
                .exchange()
                .expectStatus().isBadRequest();

    }

    @Test
    void deleteOwner() {
        webTestClient.mutateWith(mockOAuth2Login())
                .delete().uri(OWNER_PATH_ID, 1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void deletePetNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .delete().uri(OWNER_PATH_ID, 10)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void patchOwner() {
        webTestClient.mutateWith(mockOAuth2Login())
                .patch().uri(OWNER_PATH_ID, 3)
                .body(Mono.just(createTestOwner()), OwnerDTO.class)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void patchOwnerNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .patch().uri(OWNER_PATH_ID, 10)
                .body(Mono.just(createTestOwner()), OwnerDTO.class)
                .exchange()
                .expectStatus().isNotFound();
    }

    Owner createTestOwner() {
        return Owner.builder()
                .name("TestOwner")
                .address("Amboise")
                .telephone("012345")
                .build();
    }

}