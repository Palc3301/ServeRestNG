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

import static com.vemser.rest.story.UsuarioStory.*;

@Epic(EPIC_DELETAR)
@Story(DELETAR_STORY)
public class DeletarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private String usuarioId;

    @BeforeMethod
    public void setup() {
        UsuariosResponse response = usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(UsuariosResponse.class);
        usuarioId = response.getId();
    }

    @Test(description = CE_DELETAR_USUARIOS_001)
    public void testDeletarUsuarioComSucesso() {
        usuariosClient.deletarUsuario(usuarioId)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK);
    }

    @Test(description = CE_DELETAR_USUARIOS_002)
    public void testDeletarUsuarioQueNaoExiste() {
        String idInexistente = "99999";

        UsuariosResponse response = usuariosClient.deletarUsuario(idInexistente)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(UsuariosResponse.class);

        Assert.assertEquals("Nenhum registro excluído", response.getMessage());
    }

    @AfterMethod
    public void tearDown() {
    }
}
