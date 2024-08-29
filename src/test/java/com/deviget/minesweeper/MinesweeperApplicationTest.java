package com.deviget.minesweeper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDateTime;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.payload.dto.GameDto;
import com.deviget.minesweeper.payload.request.LoginRequest;
import com.deviget.minesweeper.payload.request.SignupRequest;
import com.deviget.minesweeper.payload.response.LoginResponse;
import com.deviget.minesweeper.payload.response.MessageResponse;

@SpringBootTest(classes = MinesweeperApplication.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MinesweeperApplicationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private String accessToken;
    private HttpHeaders headers = new HttpHeaders();

    private GameDto gameDto;

    @Test
    @Order(1)
    public void testSignupSuccessfully() {
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<MessageResponse> responseEntity = restTemplate.postForEntity("/user/signup", new SignupRequest("test",
                "test@test.com", "testpwd"), MessageResponse.class);

        MessageResponse messageResponse = responseEntity.getBody();
        assertNotNull(messageResponse, "Message Response is empty");
        // assertThat(messageResponse.getMessage(), anyOf(is("User registered"), is("Error: Username already exists")));
    }

    @Test
    @Order(2)
    public void testLoginSuccessfully() {
        ResponseEntity<LoginResponse> responseEntity = restTemplate.postForEntity("/user/signin", new LoginRequest("test", "testpwd"),
                LoginResponse.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Unexpected Status code");

        LoginResponse loginResponse = responseEntity.getBody();
        assertNotNull(loginResponse, "Response body is empty");

        accessToken = loginResponse.getAccessToken();
        assertNotNull(accessToken, "Authorization token is not present");
    }

    @Test
    @Order(3)
    public void testSaveGameSucessfully() {
        headers.setBearerAuth(accessToken);

        Cell[][] cells = new Cell[5][5];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells.length; j++) {
                Cell cell = new Cell(i, j, "open", false, 1);
                cells[i][j] = cell;
            }
        }
        GameDto dto = new GameDto(null, "testuser", 1000l, 10, 5, cells, 2, "IN_GAME", LocalDateTime.now());
        HttpEntity<GameDto> httpEntity = new HttpEntity<>(dto, headers);

        ResponseEntity<GameDto> response = restTemplate.postForEntity("/game", httpEntity, GameDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Unexpected Status code");

        GameDto game = response.getBody();
        assertNotNull(game, "Message Response is empty");
        assertNotNull(game.getId(), "Null Game Id");
        assertNotNull(game.getCells(), "Null Game Cells");

        this.gameDto = game;
    }

    @Test
    @Order(4)
    public void testGetOneGameSucessfully() {
        HttpEntity<GameDto> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<GameDto> response = restTemplate.exchange("/game/" + gameDto.getId(), HttpMethod.GET, httpEntity, GameDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Unexpected Status code");

        GameDto game = response.getBody();
        assertNotNull(game, "Message Response is empty");
        assertEquals(game.getId(), gameDto.getId(), "Invalid Game Id");
        assertNotNull(game.getCells(), "Game Cells is empty");
    }

    @Test
    @Order(3)
    public void testUpdateGameSucessfully() {
        this.gameDto.setStatus("LOST");
        this.gameDto.setTimer(1000l);
        HttpEntity<GameDto> httpEntity = new HttpEntity<>(this.gameDto, headers);

        ResponseEntity<GameDto> response = restTemplate.postForEntity("/game", httpEntity, GameDto.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Unexpected Status code");

        GameDto game = response.getBody();
        assertNotNull(game, "Message Response is empty");
        assertEquals(game.getId(), gameDto.getId(), "Invalid Game Id");
        assertNotNull(game.getCells(), "Game Cells is empty");
        assertEquals(game.getTimer(), gameDto.getTimer(), "Invalid Timer");
        assertEquals(game.getStatus(), gameDto.getStatus(), "Invalid Status");
    }

    @Test
    @Order(5)
    public void testDeleteAllGamesSucessfully() {
        HttpEntity<GameDto> httpEntity = new HttpEntity<>(null, headers);

        ResponseEntity<MessageResponse> response = restTemplate.exchange("/game", HttpMethod.DELETE, httpEntity, MessageResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Unexpected Status code");

        MessageResponse messageResponse = response.getBody();
        assertNotNull(messageResponse, "Message Response is empty");
        assertEquals(messageResponse.getMessage(), "All Games deleted");
    }
}
