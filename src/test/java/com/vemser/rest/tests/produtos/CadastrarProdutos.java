package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.model.response.ProdutosModel;
import com.vemser.rest.model.response.produto.ProdutosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_CADASTRAR)
@Story(CADASTRAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CadastrarProdutos {

    private final ProdutosClient produtosClient = new ProdutosClient();

    @Test
    @Order(1)
    @Description(CE_CADASTRAR_PRODUTOS_001)
    public void testCriarProdutoComSucesso() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();

        produtosClient.cadastrarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body(matchesJsonSchemaInClasspath("schemas/produto_post.json"));
    }

    @Test
    @Order(2)
    @Description(CE_CADASTRAR_PRODUTOS_002)
    public void testCriarProdutoComCamposVazios() {
        ProdutosModel produto = ProdutosDataFactory.produtoComCamposVazios();

        ProdutosResponse response = produtosClient.cadastrarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        validarErrosCamposVazios(response);
    }

    @Test
    @Order(3)
    @Description(CE_CADASTRAR_PRODUTOS_003)
    public void testCriarProdutoSemAutenticacao() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();

        ProdutosResponse response = produtosClient.cadastrarProdutoSemAutenticacao(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertEquals(
                "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais",
                response.getMessage(),
                "A mensagem de erro de autenticação não é a esperada."
        );
    }

    @Test
    @Order(4)
    @Description(CE_CADASTRAR_PRODUTOS_004)
    public void testCriarProdutoComNomeJaUtilizado() {
        ProdutosModel produto = ProdutosDataFactory.produtoComNomeUtilizado();

        ProdutosResponse response = produtosClient.cadastrarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertEquals(
                "Já existe produto com esse nome",
                response.getMessage(),
                "A mensagem de erro por nome duplicado não é a esperada."
        );
    }

    private void validarErrosCamposVazios(ProdutosResponse response) {
        Assertions.assertAll(
                () -> Assertions.assertEquals("nome não pode ficar em branco", response.getNome(), "Erro na validação do campo Nome."),
                () -> Assertions.assertEquals("preço não pode ficar em branco", response.getPreco(), "Erro na validação do campo Preço."),
                () -> Assertions.assertEquals("descrição não pode ficar em branco", response.getDescricao(), "Erro na validação do campo Descrição."),
                () -> Assertions.assertEquals("quantidade não pode ficar em branco", response.getQuantidade(), "Erro na validação do campo Quantidade.")
        );
    }
}

