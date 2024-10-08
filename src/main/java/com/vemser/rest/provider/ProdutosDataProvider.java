package com.vemser.rest.provider;

import org.junit.jupiter.params.provider.Arguments;
import com.vemser.rest.data.factory.ProdutosDataFactory;

import java.util.stream.Stream;

public class ProdutosDataProvider {
    private static final String KEY_NOME = "nome";
    private static final String VALUE_NOME_EM_BRANCO = "Nome não pode ficar em branco";
    private static final String KEY_PRECO = "preco";
    private static final String VALUE_PRECO_EM_BRANCO = "Preço não pode ficar em branco";
    private static final String KEY_DESCRICAO = "descricao";
    private static final String VALUE_DESCRICAO_EM_BRANCO = "Descrição não pode ficar em branco";
    private static final String KEY_QUANTIDADE = "quantidade";
    private static final String VALUE_QUANTIDADE_EM_BRANCO = "Quantidade não pode ficar em branco";

    public static Stream<Arguments> produtoDataProvider() {
        return Stream.of(
                Arguments.of(ProdutosDataFactory.produtoComNomeEmBranco(), KEY_NOME, VALUE_NOME_EM_BRANCO),
                Arguments.of(ProdutosDataFactory.produtoComPrecoEmBranco(), KEY_PRECO, VALUE_PRECO_EM_BRANCO),
                Arguments.of(ProdutosDataFactory.produtoComDescricaoEmBranco(), KEY_DESCRICAO, VALUE_DESCRICAO_EM_BRANCO),
                Arguments.of(ProdutosDataFactory.produtoComQuantidadeEmBranco(), KEY_QUANTIDADE, VALUE_QUANTIDADE_EM_BRANCO)
        );
    }
}
