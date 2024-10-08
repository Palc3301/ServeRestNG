package com.vemser.rest.tests.produtos;

import com.vemser.rest.client.ProdutosClient;
import com.vemser.rest.client.UsuariosClient;
import com.vemser.rest.data.factory.ProdutosDataFactory;
import com.vemser.rest.data.factory.UsuariosDataFactory;
import com.vemser.rest.model.response.ProdutosModel;
import com.vemser.rest.model.response.produto.ProdutosResponse;
import com.vemser.rest.model.response.usuario.UsuariosResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static com.vemser.rest.story.ProdutoStory.*;

@Epic(EPIC_ATUALIZAR)
@Story(ATUALIZAR_STORY)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AtualizarProdutos {

    private final ProdutosClient produtosClient = new ProdutosClient();
    private final UsuariosClient usuariosClient = new UsuariosClient();
    private String usuarioId;

    @BeforeEach
    public void setup() {
        UsuariosResponse response = usuariosClient.cadastrarUsuarios(UsuariosDataFactory.usuarioValido())
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(UsuariosResponse.class);
        usuarioId = response.getId();
    }

    @Test
    @Order(1)
    @Description(CE_ATUALIZAR_PRODUTOS_001)
    public void testAtualizarProdutoComSucesso() {
        ProdutosModel produto = ProdutosDataFactory.produtoComNomeUtilizado();

        ProdutosResponse response = produtosClient.atualizarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_CREATED)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertEquals("Registro alterado com sucesso", response.getMessage(), "A mensagem de sucesso não é a esperada.");
    }

    @Test
    @Order(2)
    @Description(CE_ATUALIZAR_PRODUTOS_002)
    public void testAtualizarProdutoComTokenInvalido() {
        ProdutosModel produto = ProdutosDataFactory.produtoValido();

        ProdutosResponse response = produtosClient.atualizarProdutoSemAutenticacao(produto)
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
    @Order(3)
    @Description(CE_ATUALIZAR_PRODUTOS_003)
    public void testAtualizarProdutoComCamposVazios() {
        ProdutosModel produto = ProdutosDataFactory.produtoComCamposVazios();

        ProdutosResponse response = produtosClient.atualizarProduto(produto)
                .then()
                    .log().all()
                    .statusCode(HttpStatus.SC_BAD_REQUEST)
                    .extract()
                    .as(ProdutosResponse.class);

        Assertions.assertAll(
                () -> Assertions.assertEquals("nome não pode ficar em branco", response.getNome(), "Validação falhou para o campo Nome."),
                () -> Assertions.assertEquals("preco deve ser um número", response.getPreco(), "Validação falhou para o campo Preço."),
                () -> Assertions.assertEquals("descrição não pode ficar em branco", response.getDescricao(), "Validação falhou para o campo Descrição."),
                () -> Assertions.assertEquals("quantidade não pode ficar em branco", response.getQuantidade(), "Validação falhou para o campo Quantidade.")
        );
    }

}
