package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.response.ProdutosModel;
import com.vemser.rest.model.response.produto.ProdutosResponse;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_ATUALIZAR)
@Story(ATUALIZAR_STORY)
public class AtualizarProdutos {

    private final ProdutosClient produtosClient = new ProdutosClient();
    private final UsuariosClient usuariosClient = new UsuariosClient();
    private String usuarioId;

    @BeforeClass
    public void setup() {
        UsuariosResponse response = usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(UsuariosResponse.class);
        usuarioId = response.getId();
    }

    @Test(description = CE_ATUALIZAR_PRODUTOS_001)
    public void testAtualizarProdutoComSucesso() {
        ProdutosModel produto = ProdutosDataFactory.produtoComNomeUtilizado();

        ProdutosResponse response = produtosClient.atualizarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(response.getMessage(), "Registro alterado com sucesso", "A mensagem de sucesso não é a esperada.");
    }

    @Test(description = CE_ATUALIZAR_PRODUTOS_002)
    public void testAtualizarProdutoComTokenInvalido() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();

        ProdutosResponse response = produtosClient.atualizarProdutoSemAutenticacao(produto)
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

    @Test(description = CE_ATUALIZAR_PRODUTOS_003)
    public void testAtualizarProdutoComCamposVazios() {
        ProdutosModel produto = ProdutosDataFactory.produtoComCamposVazios();

        ProdutosResponse response = produtosClient.atualizarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        Assert.assertEquals(response.getNome(), "nome não pode ficar em branco", "Validação falhou para o campo Nome.");
        Assert.assertEquals(response.getPreco(), "preco deve ser um número", "Validação falhou para o campo Preço.");
        Assert.assertEquals(response.getDescricao(), "descrição não pode ficar em branco", "Validação falhou para o campo Descrição.");
        Assert.assertEquals(response.getQuantidade(), "quantidade não pode ficar em branco", "Validação falhou para o campo Quantidade.");
    }
}
