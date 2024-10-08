package com.vemser.rest.provider;

import com.vemser.rest.data.factory.ProdutosDataFactory;
import org.testng.annotations.DataProvider;

public class ProdutosDataProvider {

    private static final String KEY_NOME = "nome";
    private static final String VALUE_NOME_EM_BRANCO = "Nome não pode ficar em branco";
    private static final String KEY_PRECO = "preco";
    private static final String VALUE_PRECO_EM_BRANCO = "Preço não pode ficar em branco";
    private static final String KEY_DESCRICAO = "descricao";
    private static final String VALUE_DESCRICAO_EM_BRANCO = "Descrição não pode ficar em branco";
    private static final String KEY_QUANTIDADE = "quantidade";
    private static final String VALUE_QUANTIDADE_EM_BRANCO = "Quantidade não pode ficar em branco";

    @DataProvider(name = "produtoDataProvider")
    public static Object[][] produtoDataProvider() {
        return new Object[][]{
                {ProdutosDataFactory.produtoComNomeEmBranco(), KEY_NOME, VALUE_NOME_EM_BRANCO},
                {ProdutosDataFactory.produtoComPrecoEmBranco(), KEY_PRECO, VALUE_PRECO_EM_BRANCO},
                {ProdutosDataFactory.produtoComDescricaoEmBranco(), KEY_DESCRICAO, VALUE_DESCRICAO_EM_BRANCO},
                {ProdutosDataFactory.produtoComQuantidadeEmBranco(), KEY_QUANTIDADE, VALUE_QUANTIDADE_EM_BRANCO}
        };
    }
}
