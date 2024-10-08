package com.vemser.rest.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProdutosModel {

    private String nome;
    private String preco;
    private String descricao;
    private String quantidade;
}
