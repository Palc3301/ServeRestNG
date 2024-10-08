package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;

import static com.vemser.rest.story.UsuarioStory.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(EPIC_LISTAR)
@Story(LISTAR_STORY)
public class ListarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private final String usuarioId = "4Rj3vrHUKwYtfPrl"; // ID de exemplo
    private final String nomeUsuario = "Alyson";

    @BeforeMethod
    public void setup() {
        usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED);
    }

    @Test(description = CE_LISTAR_USUARIOS_001)
    public void testDeveValidarContratoUsuariosPorIDComSucesso() {
        usuariosClient.listarUsuarioPorId(usuarioId)
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios_por_id.json"));
    }

    @Test(description = CE_LISTAR_USUARIOS_002)
    public void testDeveValidarContratoUsuariosComSucesso() {
        usuariosClient.listarUsuarios()
                .then()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios_get.json"));
    }

    @Test(description = CE_LISTAR_USUARIOS_003) // Corrigido para um identificador único
    public void testDeveListarTodosOsUsuariosComSucesso() {
        List<UsuariosResponse> response = usuariosClient.listarUsuarios()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .jsonPath()
                    .getList("usuarios", UsuariosResponse.class);

        Assert.assertFalse(response.isEmpty(), "A lista de usuários não deve estar vazia");
    }

    @Test(description = CE_LISTAR_USUARIOS_004) // Corrigido para um identificador único
    public void testListarUsuariosPorNomeComSucesso() {
        List<Map<String, ?>> response = usuariosClient.listarUsuariosPorNome(nomeUsuario)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .jsonPath()
                    .getList("usuarios");

        Assert.assertFalse(response.isEmpty(), "A lista de usuários não deve estar vazia");
        Assert.assertTrue(response.stream().anyMatch(user -> ((String) user.get("nome")).equals(nomeUsuario)),
                "A lista deve conter um usuário com o nome " + nomeUsuario);
    }

    @Test(description = CE_LISTAR_USUARIOS_005)
    public void testListarUsuariosVerificaAtributos() {
        List<UsuariosResponse> response = usuariosClient.listarUsuarios()
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .jsonPath()
                .getList("usuarios", UsuariosResponse.class);

        response.forEach(usuario -> {
            Assert.assertNotNull(usuario.getId(), "O ID do usuário não deve ser nulo");
            Assert.assertNotNull(usuario.getNome(), "O nome do usuário não deve ser nulo");
        });
    }

    @AfterMethod
    public void tearDown() {
    }
}
