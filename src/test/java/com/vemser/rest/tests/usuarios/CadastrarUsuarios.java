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
import com.vemser.rest.provider.UsuariosDataProvider;

import static com.vemser.rest.story.UsuarioStory.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

@Epic(EPIC_CADASTRAR)
@Story(CADASTRAR_STORY)
public class CadastrarUsuarios {

    private final UsuariosClient usuariosClient = new UsuariosClient();
    private String usuarioId;

    @BeforeMethod
    public void setup() {
    }

    @Test(description = CE_CADASTRAR_USUARIOS_001)
    public void testValidarContratoDePostDeUsuariosComSucesso() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

        usuariosClient.cadastrarUsuarios(usuario)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body(matchesJsonSchemaInClasspath("schemas/usuarios_post.json"));
    }

    @Test(description = CE_CADASTRAR_USUARIOS_002)
    public void testCadastrarUsuarioComSucesso() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioValido();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_CREATED)
                            .extract()
                            .as(UsuariosResponse.class);

        usuarioId = response.getId();
        Assert.assertNotNull(usuarioId, "O ID do usuário não deve ser nulo");
    }

    @Test(description = CE_CADASTRAR_USUARIOS_003)
    public void testCadastrarUsuarioSemNome() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComNomeVazio();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assert.assertEquals(response.getNome(), "nome não pode ficar em branco", "Mensagem de erro esperada");
    }

    @Test(description = CE_CADASTRAR_USUARIOS_004)
    public void testCadastrarUsuarioComCampoVazio() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComEmailEmBranco();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assert.assertEquals(response.getEmail(), "email não pode ficar em branco", "Mensagem de erro esperada");
    }

    @Test(description = CE_CADASTRAR_USUARIOS_005)
    public void testCadastrarUsuarioComEmailExistente() {
        UsuariosModel usuario = UsuariosDataFactory.usuarioComEmailExistente();

        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assert.assertEquals(response.getMessage(), "Este email já está sendo usado");
    }

    @Test(dataProvider = "usuarioDataProviderNull", dataProviderClass = UsuariosDataProvider.class, description = "Testar cadastro de usuários com Data Provider")
    public void testDeveCadastrarUsuariosComDataProvider(UsuariosModel usuario, String key, String value) {
        UsuariosResponse response =
                usuariosClient.cadastrarUsuarios(usuario)
                        .then()
                            .log().all()
                            .statusCode(HttpStatus.SC_BAD_REQUEST)
                            .extract()
                            .as(UsuariosResponse.class);

        Assert.assertEquals(response.getNome(), value, "Verificando valor de retorno");
    }

    @AfterSuite
    public void tearDown() {
        if (usuarioId != null) {
            usuariosClient.deletarUsuarioPorId(usuarioId)
                    .then()
                        .log().all()
                        .statusCode(HttpStatus.SC_NO_CONTENT);
        }
    }
}
