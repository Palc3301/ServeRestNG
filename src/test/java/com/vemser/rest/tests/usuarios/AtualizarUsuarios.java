package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static com.vemser.rest.story.UsuarioStory.*;

@Epic(EPIC_ATUALIZAR)
@Story(ATUALIZAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AtualizarUsuarios {


    private final UsuariosClient usuariosClient = new UsuariosClient();
    private UsuariosModel usuario;

    @BeforeEach
    public void setup() {
        usuario = UsuariosDataFactory.usuarioValido();
    }

    @Test
    @Order(1)
    @Description(CE_ATUALIZAR_USUARIOS_001)
    public void testAtualizarUsuariosComSucesso() {
        UsuariosResponse response =
                usuariosClient.atualizarUsuario(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_CREATED)
                            .extract()
                            .as(UsuariosResponse.class);
        Assertions.assertNotNull(response.getId(), "O ID do usuário não deve ser nulo");
    }

    @Test
    @Order(2)
    @Description(CE_ATUALIZAR_USUARIOS_002)
    public void testAtualizarUsuariosComCamposEmBranco() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComEmailEmBranco();

        UsuariosResponse response =
                usuariosClient.atualizarUsuario(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assertions.assertEquals("email não pode ficar em branco",
                response.getEmail());
    }

    @Test
    @Order(3)
    @Description(CE_ATUALIZAR_USUARIOS_003)
    public void testAtualizarUsuariosComEmailExistente() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComEmailExistente();

        UsuariosResponse response =
                usuariosClient.atualizarUsuario(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

    Assertions.assertEquals("Este email já está sendo usado", response.getMessage());
    }

    @AfterEach
    public void tearDown() {
    }
}


