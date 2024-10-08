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

import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_DELETAR)
@Story(DELETAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeletarProdutos {

    private static final ProdutosClient produtosClient = new ProdutosClient();
    private static String produtoId;

    @BeforeEach
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

    @Test
    @Order(1)
    @Description(CE_DELETAR_PRODUTOS_001)
    public void testDeletarProdutoComSucesso() {
        ProdutosResponse responseDelete = produtosClient.deletarProdutoPorId(produtoId)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertEquals("Registro excluído com sucesso", responseDelete.getMessage(), "Mensagem inesperada na exclusão.");
    }

    @Test
    @Order(2)
    @Description(CE_DELETAR_PRODUTOS_002)
    public void testDeletarProdutoQueEstaNoCarrinho() {
        ProdutosResponse response = produtosClient.deletarProdutoQueEstaNoCarrinho()
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertEquals(
                "Não é permitido excluir produto que faz parte de carrinho",
                response.getMessage(),
                "Mensagem inesperada ao tentar excluir produto no carrinho."
        );
    }

    @Test
    @Order(3)
    @Description(CE_DELETAR_PRODUTOS_003)
    public void testDeletarProdutoComIdInexistente() {
        String idInexistente = "9999";

        ProdutosResponse response = produtosClient.deletarProdutoPorId(idInexistente)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_OK)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertEquals(
                "Nenhum registro excluído",
                response.getMessage(),
                "Mensagem inesperada ao tentar excluir produto com ID inexistente."
        );
    }
}
