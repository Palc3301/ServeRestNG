package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_LISTAR)
@Story(LISTAR_STORY)
public class ListarProdutos {

    private final ProdutosClient produtosClient = new ProdutosClient();

    @Test
    @Description(CE_LISTAR_PRODUTOS_001)
    public void testDeveValidarContratoTodosOsProdutos() {
        produtosClient.listarProdutos()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/produtos.json"));
    }

    @Test
    @Description
    public void testGetTodosOsProdutosPorId() {
        produtosClient.listarProdutoPorId()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .body(matchesJsonSchemaInClasspath("schemas/produtos_por_id.json"));
        }

    @Test
    @Description(CE_LISTAR_PRODUTOS_003)
    public void testGetTodosOsProdutosIdInexistente() {
        produtosClient.listarProdutoPorIdInexistente()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @Description(CE_LISTAR_PRODUTOS_004)
    public void testGetProdutosIdVazio() {
        produtosClient.listarProdutoPorIdInexistente()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST);
    }
}
