package br.com.crud.gestaousuario;

import br.com.crud.gestaousuario.model.User;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
public class UserApiTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static Long createdUserId;

    @Test
    @Order(1)
    public void testCreateUser() {
        User user = new User(null, "Test User", "password", "test@test.com", "123456789");
        ResponseEntity<User> response = restTemplate.postForEntity("/api/users", user, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getNome()).isEqualTo("Test User");
        assertThat(response.getBody().getId()).isNotNull();

        createdUserId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void testListUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("/api/users", User[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    @Order(3)
    public void testGetUserById() {
        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/" + createdUserId, User.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getId()).isEqualTo(createdUserId);
    }

    @Test
    @Order(4)
    public void testUpdateUser() {
        User user = new User(createdUserId, "Updated Name", "newPassword", "updated@test.com", "987654321");
        restTemplate.put("/api/users/" + createdUserId, user);

        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/" + createdUserId, User.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getNome()).isEqualTo("Updated Name");
    }

    @Test
    @Order(5)
    public void testDeleteUser() {
        restTemplate.delete("/api/users/" + createdUserId);

        ResponseEntity<User> response = restTemplate.getForEntity("/api/users/" + createdUserId, User.class);
        assertThat(response.getBody()).isNull();
    }
}
