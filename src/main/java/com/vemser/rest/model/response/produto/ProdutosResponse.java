package com.vemser.rest.model.response.produto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProdutosResponse {

    private String nome;
    private String preco;
    private String descricao;
    private String quantidade;
    @JsonProperty("_id")
    private String id;
    private String message;
    private List<String> idCarrinhos; // Change from String to List<String>

    public ProdutosResponse() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getIdCarrinhos() {
        return idCarrinhos;
    }

    public void setIdCarrinhos(List<String> idCarrinhos) {
        this.idCarrinhos = idCarrinhos;
    }

    @Override
    public String toString() {
        return "ProdutosResponse{" +
                "nome='" + nome + '\'' +
                ", preco='" + preco + '\'' +
                ", descricao='" + descricao + '\'' +
                ", quantidade='" + quantidade + '\'' +
                ", id='" + id + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
