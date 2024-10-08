package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.model.response.ProdutosModel;
import com.vemser.rest.model.response.produto.ProdutosResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_CADASTRAR)
@Story(CADASTRAR_STORY)
@Test
public class CadastrarProdutos {

    private final ProdutosClient produtosClient = new ProdutosClient();

    @Test(description = CE_CADASTRAR_PRODUTOS_001)
    public void testCriarProdutoComSucesso() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();

        produtosClient.cadastrarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .body(matchesJsonSchemaInClasspath("schemas/produto_post.json"));
    }

    @Test(description = CE_CADASTRAR_PRODUTOS_002)
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

    @Test(description = CE_CADASTRAR_PRODUTOS_003)
    public void testCriarProdutoSemAutenticacao() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();

        ProdutosResponse response = produtosClient.cadastrarProdutoSemAutenticacao(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_UNAUTHORIZED)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(
                response.getMessage(),
                "Token de acesso ausente, inválido, expirado ou usuário do token não existe mais",
                "A mensagem de erro de autenticação não é a esperada."
        );
    }

    @Test(description = CE_CADASTRAR_PRODUTOS_004)
    public void testCriarProdutoComNomeJaUtilizado() {
        ProdutosModel produto = ProdutosDataFactory.produtoComNomeUtilizado();

        ProdutosResponse response = produtosClient.cadastrarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(
                response.getMessage(),
                "Já existe produto com esse nome",
                "A mensagem de erro por nome duplicado não é a esperada."
        );
    }

    private void validarErrosCamposVazios(ProdutosResponse response) {
        Assert.assertEquals(response.getNome(), "nome não pode ficar em branco", "Erro na validação do campo Nome.");
        Assert.assertEquals(response.getPreco(), "preço não pode ficar em branco", "Erro na validação do campo Preço.");
        Assert.assertEquals(response.getDescricao(), "descrição não pode ficar em branco", "Erro na validação do campo Descrição.");
        Assert.assertEquals(response.getQuantidade(), "quantidade não pode ficar em branco", "Erro na validação do campo Quantidade.");
    }
}
