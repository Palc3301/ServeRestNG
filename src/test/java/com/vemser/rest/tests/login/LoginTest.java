package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.model.request.login.LoginRequest;
import com.vemser.rest.model.response.login.LoginResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static com.vemser.rest.story.LoginStory.*;

@Epic(EPIC_LOGIN)
@Story(LOGIN_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginTest {

    private static final String SUCCESS_MESSAGE = "Login realizado com sucesso";
    private static final String EMPTY_EMAIL_MESSAGE = "email não pode ficar em branco";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Email e/ou senha inválidos";

    private final LoginClient loginClient = new LoginClient();

    @Test
    @Order(1)
    @Description(CE_LOGIN_001)
    public void testLoginComSucesso() {
        LoginRequest loginRequest = new LoginRequest("alyson@qa.com.br", "teste");

        LoginResponse response = loginClient.login(loginRequest)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(LoginResponse.class);

        Assertions.assertEquals(SUCCESS_MESSAGE, response.getMessage(), "Mensagem de login incorreta");
        Assertions.assertNotNull(response.getAuthorization(), "Token não deve ser nulo");
    }

    @Test
    @Order(2)
    @Description(CE_LOGIN_002)
    public void testLoginComEmailVazio() {
        LoginRequest loginRequest = new LoginRequest("", "teste");

        LoginResponse response = loginClient.login(loginRequest)
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .extract()
                .as(LoginResponse.class);

        Assertions.assertEquals(EMPTY_EMAIL_MESSAGE, response.getEmail(), "Validação de email vazio falhou");
    }

    @Test
    @Order(3)
    @Description(CE_LOGIN_003)
    public void testLoginComUsuarioInexistente() {
        LoginRequest loginRequest = new LoginRequest("usuarioInexistente@qa.com.br", "senhaInvalida");

        LoginResponse response = loginClient.login(loginRequest)
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED)
                .extract()
                .as(LoginResponse.class);

        Assertions.assertEquals(INVALID_CREDENTIALS_MESSAGE, response.getMessage(), "Mensagem de usuário inexistente incorreta");
    }

}
