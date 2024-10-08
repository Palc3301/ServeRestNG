package com.vemser.rest.client;

import com.vemser.rest.model.UsuariosModel;
import io.restassured.http.ContentType;
import io.restassured.response.*;

import static io.restassured.RestAssured.*;

public class UsuariosClient extends BaseClient {

    private final String USUARIOS = "/usuarios";
    private final String FIXED_ID = "r7uVjURO9VevVGVT";


    public Response cadastrarUsuarios(UsuariosModel usuario) {

        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .body(usuario)
                .when()
                        .post(USUARIOS);
    }

    public Response atualizarUsuario(UsuariosModel usuario) {
        return
                given()
                        .spec(super.set())
                        .contentType(ContentType.JSON)
                        .body(usuario)
                        .pathParam("id", FIXED_ID)
                .when()
                        .put(USUARIOS + "/{id}");
    }

    public Response listarUsuarios() {
        return
                given()
                        .spec(super.set())
                .when()
                        .get(USUARIOS);
    }

    public Response listarUsuarioPorId(String id) {
        return given()
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
                        .get(USUARIOS);
    }

    public Response deletarUsuarioPorId(String id) {
        return given()
                    .spec(super.set())
                    .pathParam("id", id)
                .when()
                    .delete(USUARIOS + "/{id}");
    }

    public Response deletarUsuario(String usuarioId) {
        return given()
                    .spec(super.set())
                    .pathParam("id", usuarioId)
                .when()
                    .delete(USUARIOS + "/{id}");
    }


}

