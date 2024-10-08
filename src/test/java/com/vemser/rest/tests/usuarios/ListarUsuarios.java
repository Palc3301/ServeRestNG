package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.vemser.rest.story.UsuarioStory.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.*;

@Epic(EPIC_LISTAR)
@Story(LISTAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ListarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private final String usuarioId = "4Rj3vrHUKwYtfPrl"; // ID de exemplo
    private final String nomeUsuario = "Alyson";

    @BeforeEach
    public void setup() {
        usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED);
    }

    @Test
    @Order(1)
    @Description(CE_LISTAR_USUARIOS_001)
    public void testDeveValidarContratoUsuariosPorIDComSucesso() {
        usuariosClient.listarUsuarioPorId(usuarioId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"));
    }

    @Test
    @Order(2)
    @Description(CE_LISTAR_USUARIOS_002)
    public void testDeveValidarContratoUsuariosComSucesso() {
        usuariosClient.listarUsuarios()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios_get.json"));
    }

    @Test
    @Order(3)
    @Description(CE_LISTAR_USUARIOS_001)
    public void testDeveListarTodosOsUsuariosComSucesso() {
        List<UsuariosResponse> response = usuariosClient.listarUsuarios()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .jsonPath()
                    .getList("usuarios", UsuariosResponse.class);

        assertFalse(response.isEmpty(), "A lista de usuários não deve estar vazia");
    }

    @Test
    @Order(4)
    @Description(CE_LISTAR_USUARIOS_004)
    public void testListarUsuariosPorNomeComSucesso() {
        List<Map<String, ?>> response = usuariosClient.listarUsuariosPorNome(nomeUsuario)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .jsonPath()
                    .getList("usuarios");

        assertFalse(response.isEmpty(), "A lista de usuários não deve estar vazia");
        assertTrue(response.stream().anyMatch(user -> ((String) user.get("nome")).equals(nomeUsuario)),
                "A lista deve conter um usuário com o nome " + nomeUsuario);
    }

}
