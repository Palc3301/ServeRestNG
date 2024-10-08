package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_LISTAR)
@Story(LISTAR_STORY)
public class ListarProdutos {

    private final ProdutosClient produtosClient = new ProdutosClient();

    @Test(description = CE_LISTAR_PRODUTOS_001)
    public void testDeveValidarContratoTodosOsProdutos() {
        produtosClient.listarProdutos()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/produtos.json"));
    }

    @Test(description = "Testa a listagem de todos os produtos por ID.")
    public void testGetTodosOsProdutosPorId() {
        produtosClient.listarProdutoPorId()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"));
    }

    @Test(description = CE_LISTAR_PRODUTOS_003)
    public void testGetTodosOsProdutosIdInexistente() {
        produtosClient.listarProdutoPorIdInexistente()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test(description = CE_LISTAR_PRODUTOS_004)
    public void testGetProdutosIdVazio() {
        produtosClient.listarProdutoPorIdInexistente()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
