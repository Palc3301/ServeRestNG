package com.vemser.rest.client;

import com.vemser.rest.model.UsuariosModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UsuariosClient extends BaseClient {

    private final String USUARIOS_ENDPOINT = "/usuarios";
    private final String EXISTING_USER_ID = "r7uVjURO9VevVGVT";

    public Response cadastrarUsuarios(UsuariosModel usuario) {
        return
                given()
                            .spec(super.set())
                            .contentType(ContentType.JSON)
                            .body(usuario)
                        .when()
                            .post(USUARIOS_ENDPOINT);
    }

    public Response atualizarUsuario(UsuariosModel usuario) {
        return
                given()
                            .spec(super.set())
                            .contentType(ContentType.JSON)
                            .body(usuario)
                            .pathParam("id", EXISTING_USER_ID)
                        .when()
                            .put(USUARIOS_ENDPOINT + "/{id}");
    }

    public Response listarUsuarios() {
        return
                given()
                            .spec(super.set())
                        .when()
                            .get(USUARIOS_ENDPOINT);
    }

    public Response listarUsuarioPorId(String id) {
        return
                given()
                            .pathParam("id", id)
                        .when()
                            .get("/usuarios/{id}");
    }

    public Response listarUsuariosPorNome(String nome) {
        return
                given()
                            .spec(super.set())
                            .queryParam("nome", nome)
                        .when()
                            .get(USUARIOS_ENDPOINT);
    }

    public Response deletarUsuarioPorId(String id) {
        return
                given()
                            .spec(super.set())
                            .pathParam("id", id)
                        .when()
                            .delete(USUARIOS_ENDPOINT + "/{id}");
    }

    public Response deletarUsuario(String usuarioId) {
        return
                given()
                            .spec(super.set())
                            .pathParam("id", usuarioId)
                        .when()
                            .delete(USUARIOS_ENDPOINT + "/{id}");
    }
}
