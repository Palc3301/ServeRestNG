package com.vemser.rest.story;

public class UsuarioStory {
    public static final String EPIC_CADASTRAR = "Cadastro de usuário";
    public static final String CADASTRAR_STORY = "Como usuário, quero cadastrar um novo usuário para que possa acessar o sistema.";
//
    public static final String CE_CADASTRAR_USUARIOS_001 = "Validar contrato de cadastro de usuário com sucesso";
    public static final String CE_CADASTRAR_USUARIOS_002 = "Cadastrar usuário com sucesso";
    public static final String CE_CADASTRAR_USUARIOS_003 = "Tentar cadastrar usuário sem nome";
    public static final String CE_CADASTRAR_USUARIOS_004 = "Tentar cadastrar usuário com campo vazio";
    public static final String CE_CADASTRAR_USUARIOS_005 = "Tentar cadastrar usuário com email já existente";
  //
    public static final String EPIC_ATUALIZAR = "Atualização de usuário";
    public static final String ATUALIZAR_STORY = "Como usuário, quero atualizar os dados do usuário para que as informações estejam sempre corretas.";
//
    public static final String CE_ATUALIZAR_USUARIOS_001 = "Atualizar usuário com sucesso";
    public static final String CE_ATUALIZAR_USUARIOS_002 = "Tentar atualizar usuário com campos em branco";
    public static final String CE_ATUALIZAR_USUARIOS_003 = "Tentar atualizar usuário com email já existente";

    public static final String EPIC_DELETAR = "Exclusão de usuário";
    public static final String DELETAR_STORY = "Como usuário, quero excluir um usuário para que não apareça mais no sistema.";

    public static final String CE_DELETAR_USUARIOS_001 = "Deletar usuário com sucesso";
    public static final String CE_DELETAR_USUARIOS_002 = "Tentar deletar usuário que não existe";

    public static final String EPIC_LISTAR = "Listagem de usuários";
    public static final String LISTAR_STORY = "Como usuário, quero listar todos os usuários para que possa verificar quem está cadastrado.";

    public static final String CE_LISTAR_USUARIOS_001 = "Listar usuário por ID com sucesso";
    public static final String CE_LISTAR_USUARIOS_002 = "Listar todos os usuários com filtro de nome";
    public static final String CE_LISTAR_USUARIOS_003 = "Listar usuário por ID que não existe";
    public static final String CE_LISTAR_USUARIOS_004 = "Listar usuário por ID com filtro de nome";
    public static final String CE_LISTAR_USUARIOS_005 = "Listar todos os usuários e verificar atributos";
}
