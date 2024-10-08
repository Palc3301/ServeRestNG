package com.vemser.rest.data.factory;

import com.vemser.rest.model.LoginModel;

public class LoginDataFactory {

    public static LoginModel loginValido() {
        return new LoginModel("alyson@qa.com.br", "teste");
    }

    public static LoginModel loginComEmailVazio() {
        return new LoginModel("", "teste");
    }

    public static LoginModel loginComUsuarioInexistente() {
        return new LoginModel("usuarioInexistente@qa.com.br", "senhaInvalida");
    }
}
