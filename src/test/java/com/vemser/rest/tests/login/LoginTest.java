package com.vemser.rest.tests.login;

import com.vemser.rest.client.LoginClient;
import com.vemser.rest.model.request.login.LoginRequest;
import com.vemser.rest.model.response.login.LoginResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vemser.rest.story.LoginStory.*;

@Epic(EPIC_LOGIN)
@Story(LOGIN_STORY)
public class LoginTest {

    private static final String SUCCESS_MESSAGE = "Login realizado com sucesso";
    private static final String EMPTY_EMAIL_MESSAGE = "email não pode ficar em branco";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Email e/ou senha inválidos";

    private LoginClient loginClient;

    @BeforeClass
    public void setUp() {
        loginClient = new LoginClient();
    }

    @Test(description = CE_LOGIN_001)
    public void testLoginComSucesso() {
        LoginRequest loginRequest = new LoginRequest("alyson@qa.com.br", "teste");

        LoginResponse response = loginClient.login(loginRequest)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(LoginResponse.class);

        Assert.assertEquals(response.getMessage(), SUCCESS_MESSAGE, "Mensagem de login incorreta");
        Assert.assertNotNull(response.getAuthorization(), "Token não deve ser nulo");
    }

    @Test(description = CE_LOGIN_002)
    public void testLoginComEmailVazio() {
        LoginRequest loginRequest = new LoginRequest("", "teste");

        LoginResponse response = loginClient.login(loginRequest)
                .then()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(LoginResponse.class);

        Assert.assertEquals(response.getEmail(), EMPTY_EMAIL_MESSAGE, "Validação de email vazio falhou");
    }

    @Test(description = CE_LOGIN_003)
    public void testLoginComUsuarioInexistente() {
        LoginRequest loginRequest = new LoginRequest("usuarioInexistente@qa.com.br", "senhaInvalida");

        LoginResponse response = loginClient.login(loginRequest)
                .then()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED)
                    .extract()
                    .as(LoginResponse.class);

        Assert.assertEquals(response.getMessage(), INVALID_CREDENTIALS_MESSAGE, "Mensagem de usuário inexistente incorreta");
    }
}
