package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static com.vemser.rest.story.UsuarioStory.*;

@Epic(EPIC_DELETAR)
@Story(DELETAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeletarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private String usuarioId;

    @BeforeEach
    public void setup() {
        UsuariosResponse response = usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(UsuariosResponse.class);
        usuarioId = response.getId();

    }

    @Test
    @Order(1)
    @Description(CE_DELETAR_USUARIOS_001)
    public void testDeletarUsuarioComSucesso() {
        usuariosClient.deletarUsuario(usuarioId)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @Order(2)
    @Description(CE_DELETAR_USUARIOS_002)
    public void testDeletarUsuarioQueNaoExiste() {
        String idInexistente = "99999";

        UsuariosResponse response = usuariosClient.deletarUsuario(idInexistente)
                .then()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(UsuariosResponse.class);

        Assertions.assertEquals("Nenhum registro excluído", response.getMessage());
    }

    @AfterEach
    public void tearDown() {
    }
}
