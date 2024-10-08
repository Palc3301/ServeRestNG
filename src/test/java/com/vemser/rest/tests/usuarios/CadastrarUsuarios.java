package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static com.vemser.rest.story.UsuarioStory.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(EPIC_CADASTRAR)
@Story(CADASTRAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CadastrarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();

    @Test
    @Order(1)
    @Description(CE_CADASTRAR_USUARIOS_001)
    public void testValidarContratoDePostDeUsuariosComSucesso() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

        usuariosClient.cadastrarUsuarios(usuario)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios_post.json"));
    }


    @Test
    @Order(2)
    @Description(CE_CADASTRAR_USUARIOS_002)
    public void testCadastrarUsuarioComSucesso() {

        UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                .then()
                        .log().all()
                        .statusCode(HttpStatus.SC_CREATED)
                        .extract()
                        .as(UsuariosResponse.class);

        Assertions.assertNotNull(response.getId());
    }

    @Test
    @Order(3)
    @Description(CE_CADASTRAR_USUARIOS_003)
    public void testCadastrarUsuarioSemNome() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComNomeVazio();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assertions.assertAll("response",
                () -> Assertions.assertEquals("nome não pode ficar em branco", response.getNome(), "Mensagem de erro esperada")
        );
    }

    @Test
    @Order(4)
    @Description(CE_CADASTRAR_USUARIOS_004)
    public void testCadastrarUsuarioComCampoVazio() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComEmailEmBranco();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                .then()
                        .log().all()
                        .statusCode(HttpStatus.SC_BAD_REQUEST)
                        .extract()
                        .as(UsuariosResponse.class);

        System.out.println(response);
       Assertions.assertEquals("email não pode ficar em branco", response.getEmail(), "Mensagem de erro esperada");
    }


    @Test
    @Order(5)
    @Description(CE_CADASTRAR_USUARIOS_005)
    public void testCadastrarUsuarioComEmailExistente() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComEmailExistente();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assertions.assertEquals("Este email já está sendo usado", response.getMessage());
    }

    @ParameterizedTest
    @MethodSource("com.vemser.rest.provider.UsusariosDataProvider#usuarioDataProvider")
    @Order(6)
    public void testDeveCadastrarUsuariosComDataProvider(UsuariosModel usuario, String key, String valiue) {
        usuariosClient.cadastrarUsuarios(usuario)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(key, Matchers.equalTo(valiue))
                .extract()
                .as(UsuariosResponse.class);
    }

}
