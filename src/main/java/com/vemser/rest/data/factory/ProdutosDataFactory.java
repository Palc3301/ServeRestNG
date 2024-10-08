package com.vemser.rest.data.factory;

import com.vemser.rest.model.response.ProdutosModel;
import net.datafaker.Faker;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;
import java.util.Random;

public class ProdutosDataFactory {

    private static Faker faker = new Faker(new Locale("pt-BR"));
    private static Random geradorBoolean = new Random();

    public static ProdutosModel produtoValido() {
        return novoProduto();
    }

    public static ProdutosModel produtoComNomeUtilizado() {
        ProdutosModel produto = new ProdutosModel();
        produto.setNome("Logitech");
        produto.setPreco(String.valueOf(faker.number().numberBetween(1, 100)));
        produto.setDescricao(faker.lorem().sentence());
        produto.setQuantidade(String.valueOf(faker.number().numberBetween(1, 100)));

        return produto;
    }

    public static ProdutosModel produtoComNomeEmBranco() {
        ProdutosModel produto = novoProduto();
        produto.setNome(StringUtils.EMPTY);
        return produto;
    }

    public static ProdutosModel produtoComDescricaoEmBranco() {
        ProdutosModel produto = novoProduto();
        produto.setDescricao(StringUtils.EMPTY);
        return produto;
    }

    public static ProdutosModel produtoComPrecoEmBranco() {
        ProdutosModel produto = novoProduto();
        produto.setPreco(StringUtils.EMPTY);
        return produto;
    }

    public static ProdutosModel produtoComQuantidadeEmBranco() {
        ProdutosModel produto = novoProduto();
        produto.setQuantidade(StringUtils.EMPTY);
        return produto;
    }

    public static ProdutosModel produtoComPrecoNegativo() {
        ProdutosModel produto = novoProduto();
        produto.setPreco(String.valueOf(-1.0));
        return produto;
    }

    public static ProdutosModel produtoComQuantidadeNegativa() {
        ProdutosModel produto = novoProduto();
        produto.setQuantidade(String.valueOf(-1));
        return produto;
    }

    public static ProdutosModel produtoComCamposVazios() {
        ProdutosModel produto = new ProdutosModel();
        produto.setNome(StringUtils.EMPTY);
        produto.setPreco(StringUtils.EMPTY);
        produto.setDescricao(StringUtils.EMPTY);
        produto.setQuantidade(StringUtils.EMPTY);
        return produto;
    }

    private static ProdutosModel novoProduto() {
        ProdutosModel produto = new ProdutosModel();
        produto.setNome(faker.commerce().productName());
        produto.setDescricao(faker.lorem().sentence());
        produto.setPreco(String.valueOf(faker.number().numberBetween(0, 100)));
        produto.setQuantidade(String.valueOf(faker.number().numberBetween(0, 100)));

        return produto;
    }
}
