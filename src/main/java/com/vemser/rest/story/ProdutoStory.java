package com.vemser.rest.story;

public class ProdutoStory {
    // Cadastro
    public static final String EPIC_CADASTRAR = "Cadastro de produto";
    public static final String CADASTRAR_STORY = "Como usuário, quero cadastrar um novo produto para que possa ser vendido no sistema.";

    public static final String CE_CADASTRAR_PRODUTOS_001 = "Validar contrato de cadastro de produto com sucesso";
    public static final String CE_CADASTRAR_PRODUTOS_002 = "Cadastrar produto com sucesso";
    public static final String CE_CADASTRAR_PRODUTOS_003 = "Tentar cadastrar produto sem nome";
    public static final String CE_CADASTRAR_PRODUTOS_004 = "Tentar cadastrar produto com campo vazio";
    public static final String CE_CADASTRAR_PRODUTOS_005 = "Tentar cadastrar produto com SKU já existente";

    // Atualização
    public static final String EPIC_ATUALIZAR = "Atualização de produto";
    public static final String ATUALIZAR_STORY = "Como usuário, quero atualizar os dados do produto para que as informações estejam sempre corretas.";

    public static final String CE_ATUALIZAR_PRODUTOS_001 = "Atualizar produto com sucesso";
    public static final String CE_ATUALIZAR_PRODUTOS_002 = "Tentar atualizar produto com campos em branco";
    public static final String CE_ATUALIZAR_PRODUTOS_003 = "Tentar atualizar produto com SKU já existente";

    // Exclusão
    public static final String EPIC_DELETAR = "Exclusão de produto";
    public static final String DELETAR_STORY = "Como usuário, quero excluir um produto para que não apareça mais no sistema.";

    public static final String CE_DELETAR_PRODUTOS_001 = "Deletar produto com sucesso";
    public static final String CE_DELETAR_PRODUTOS_002 = "Tentar deletar produto que está no carrinho";
    public static final String CE_DELETAR_PRODUTOS_003 = "Tentar deletar produto que não existe";
    // Listagem
    public static final String EPIC_LISTAR = "Listagem de produtos";
    public static final String LISTAR_STORY = "Como usuário, quero listar todos os produtos para que possa verificar quais estão disponíveis.";

    public static final String CE_LISTAR_PRODUTOS_001 = "Listar produto por ID com sucesso";
    public static final String CE_LISTAR_PRODUTOS_002 = "Listar todos os produtos com sucesso";
    public static final String CE_LISTAR_PRODUTOS_003 = "Listar produto por ID que não existe";
    public static final String CE_LISTAR_PRODUTOS_004 = "Listar produtos com filtro de nome";
    public static final String CE_LISTAR_PRODUTOS_005 = "Listar produtos com filtro de categoria";
}
