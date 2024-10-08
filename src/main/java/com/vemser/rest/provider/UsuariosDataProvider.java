package com.vemser.rest.provider;

import com.vemser.rest.data.factory.UsuariosDataFactory;
import org.testng.annotations.DataProvider;

public class UsuariosDataProvider {

    private static final String VALUE_NOME_EM_BRANCO = "nome não pode ficar em branco";
    private static final String VALUE_EMAIL_EM_BRANCO = "email não pode ficar em branco";
    private static final String VALUE_PASSWORD_EM_BRANCO = "password não pode ficar em branco";
    private static final String VALUE_ADMINISTRADOR_EM_BRANCO = "administrador deve ser 'true' ou 'false'";

    @DataProvider(name = "usuarioDataProvider")
    public static Object[][] usuarioDataProvider() {
        return new Object[][] {
                { UsuariosDataFactory.usuarioComNomeVazio(), "nome", VALUE_NOME_EM_BRANCO },
                { UsuariosDataFactory.usuarioComEmailEmBranco(), "email", VALUE_EMAIL_EM_BRANCO },
                { UsuariosDataFactory.usuarioComPasswordEmBranco(), "password", VALUE_PASSWORD_EM_BRANCO },
                { UsuariosDataFactory.usuarioComAdministradorEmBranco(), "administrador", VALUE_ADMINISTRADOR_EM_BRANCO }
        };
    }

    @DataProvider(name = "usuarioDataProviderNull")
    public static Object[][] usuarioDataProviderNull() {
        return new Object[][] {
                { UsuariosDataFactory.usuarioComNomeVazio(), "nome", VALUE_NOME_EM_BRANCO },
                { UsuariosDataFactory.usuarioComEmailEmBranco(), "email", null },
                { UsuariosDataFactory.usuarioComPasswordEmBranco(), "password", null },
                { UsuariosDataFactory.usuarioComAdministradorEmBranco(), "administrador", null }
        };
    }
}
