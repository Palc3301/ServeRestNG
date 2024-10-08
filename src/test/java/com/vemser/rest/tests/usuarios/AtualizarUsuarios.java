package com.vemser.rest.tests.usuarios;

import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.UsuariosModel;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.*;

import static com.vemser.rest.story.UsuarioStory.*;

@Epic(EPIC_ATUALIZAR)
@Story(ATUALIZAR_STORY)
public class AtualizarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private UsuariosModel usuario;

    @BeforeMethod
    public void setup() {
        usuario = UsuariosDataFactory.usuarioValido();
    }

    @Test(description = CE_ATUALIZAR_USUARIOS_001)
    public void testAtualizarUsuariosComSucesso() {
        UsuariosResponse response =
                usuariosClient.atualizarUsuario(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_CREATED)
                            .extract()
                            .as(UsuariosResponse.class);
        Assert.assertNotNull(response.getId(), "O ID do usuário não deve ser nulo");
    }

    @Test(description = CE_ATUALIZAR_USUARIOS_002)
    public void testAtualizarUsuariosComCamposEmBranco() {
        UsuariosModel usuarioComEmailEmBranco = UsuariosDataFactory.usuarioComEmailEmBranco();

        UsuariosResponse response =
                usuariosClient.atualizarUsuario(usuarioComEmailEmBranco)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assert.assertEquals(response.getEmail(), "email não pode ficar em branco");
    }

    @Test(description = CE_ATUALIZAR_USUARIOS_003)
    public void testAtualizarUsuariosComEmailExistente() {
        UsuariosModel usuarioComEmailExistente = UsuariosDataFactory.usuarioComEmailExistente();

        UsuariosResponse response =
                usuariosClient.atualizarUsuario(usuarioComEmailExistente)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assert.assertEquals(response.getMessage(), "Este email já está sendo usado");
    }

    @AfterMethod
    public void tearDown() {
    }
}
