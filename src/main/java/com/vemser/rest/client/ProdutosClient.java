package com.vemser.rest.client;

import com.vemser.rest.model.response.ProdutosModel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class ProdutosClient extends BaseClient {

    private final String PRODUTOS_ENDPOINT = "/produtos";
    private final String EXISTING_PRODUCT_ID = "r7uVjURO9VevVGVT";
    private final String CART_PRODUCT_ID = "Rmhbc1gZuOvl6bbY";
    private final String INVALID_PRODUCT_ID = "Rmhb";

    public Response cadastrarProduto(ProdutosModel produto) {
        return
                given()
                            .spec(super.set())
                            .contentType(ContentType.JSON)
                            .header("Authorization", getBearerToken())
                            .body(produto)
                        .when()
                            .post(PRODUTOS_ENDPOINT);
    }

    public Response cadastrarProdutoSemAutenticacao(ProdutosModel produto) {
        return
                given()
                            .spec(super.set())
                            .contentType(ContentType.JSON)
                            .body(produto)
                        .when()
                            .post(PRODUTOS_ENDPOINT);
    }

    public Response atualizarProduto(ProdutosModel produto) {
        return
                given()
                            .spec(super.set())
                            .contentType(ContentType.JSON)
                            .header("Authorization", getBearerToken())
                            .body(produto)
                            .pathParam("id", EXISTING_PRODUCT_ID)
                        .when()
                            .put(PRODUTOS_ENDPOINT + "/{id}");
    }

    public Response atualizarProdutoSemAutenticacao(ProdutosModel produto) {
        return
                given()
                            .spec(super.set())
                            .contentType(ContentType.JSON)
                            .body(produto)
                            .pathParam("id", EXISTING_PRODUCT_ID)
                        .when()
                            .put(PRODUTOS_ENDPOINT + "/{id}");
    }

    public Response listarProdutos() {
        return
                given()
                            .spec(super.set())
                            .header("Authorization", getBearerToken())
                        .when()
                            .get(PRODUTOS_ENDPOINT);
    }

    public Response listarProdutoPorId() {
        return
                given()
                            .spec(super.set())
                            .header("Authorization", getBearerToken())
                            .pathParam("id", CART_PRODUCT_ID)
                        .when()
                            .get(PRODUTOS_ENDPOINT + "/{id}");
    }

    public Response listarProdutoPorIdInexistente() {
        return
                given()
                            .spec(super.set())
                            .header("Authorization", getBearerToken())
                            .pathParam("id", INVALID_PRODUCT_ID)
                        .when()
                            .get(PRODUTOS_ENDPOINT + "/{id}");
    }

    public Response deletarProdutoPorId(String id) {
        return
                given()
                            .spec(super.set())
                            .pathParam("id", id)
                            .header("Authorization", getBearerToken())
                        .when()
                            .delete(PRODUTOS_ENDPOINT + "/{id}");
    }

    public Response deletarProdutoQueEstaNoCarrinho() {
        return
                given()
                            .spec(super.set())
                            .pathParam("id", CART_PRODUCT_ID)
                            .header("Authorization", getBearerToken())
                        .when()
                            .delete(PRODUTOS_ENDPOINT + "/{id}");
    }

    public Response deletarProdutoPorIdInexistente() {
        return
                given()
                            .spec(super.set())
                            .pathParam("id", INVALID_PRODUCT_ID)
                            .header("Authorization", getBearerToken())
                        .when()
                          .delete(PRODUTOS_ENDPOINT + "/{id}");
    }

    public String getBearerToken() {
        String email = "alyson@qa.com.br";
        String senha = "teste";

        return
                given()
                            .spec(super.set())
                            .log().all()
                            .contentType(ContentType.JSON)
                            .body("{\"email\":\"" + email + "\",\"password\":\"" + senha + "\"}")
                        .when()
                            .post("/login")
                        .then()
                            .log().all()
                            .statusCode(200)
                            .extract()
                            .path("authorization");
    }
}
