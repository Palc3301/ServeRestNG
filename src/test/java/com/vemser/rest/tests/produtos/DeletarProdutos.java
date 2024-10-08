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

import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_DELETAR)
@Story(DELETAR_STORY)
@Test
public class DeletarProdutos {

    private static final ProdutosClient produtosClient = new ProdutosClient();
    private static String produtoId;

    @BeforeMethod
    public void setup() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();
        ProdutosResponse responseCadastro = produtosClient.cadastrarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProdutosResponse.class);
        produtoId = responseCadastro.getId();
    }

    @Test(description = CE_DELETAR_PRODUTOS_001)
    public void testDeletarProdutoComSucesso() {
        ProdutosResponse responseDelete = produtosClient.deletarProdutoPorId(produtoId)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(responseDelete.getMessage(), "Registro excluído com sucesso", "Mensagem inesperada na exclusão.");
    }

    @Test(description = CE_DELETAR_PRODUTOS_002)
    public void testDeletarProdutoQueEstaNoCarrinho() {
        ProdutosResponse response = produtosClient.deletarProdutoQueEstaNoCarrinho()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(
                response.getMessage(),
                "Não é permitido excluir produto que faz parte de carrinho",
                "Mensagem inesperada ao tentar excluir produto no carrinho."
        );
    }

    @Test(description = CE_DELETAR_PRODUTOS_003)
    public void testDeletarProdutoComIdInexistente() {
        String idInexistente = "9999";

        ProdutosResponse response = produtosClient.deletarProdutoPorId(idInexistente)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(
                response.getMessage(),
                "Nenhum registro excluído",
                "Mensagem inesperada ao tentar excluir produto com ID inexistente."
        );
    }
}
