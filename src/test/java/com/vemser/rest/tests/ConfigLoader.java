package com.vemser.rest.tests;

import java.io.InputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {

    private Properties properties = new Properties();

    public ConfigLoader() {
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Desculpe, não foi possível encontrar o arquivo config.properties");
                return;
            }

            properties.load(input);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static void main(String[] args) {
        ConfigLoader configLoader = new ConfigLoader();
        System.out.println("URL do Banco de Dados: " + configLoader.getProperty("db.url"));
        System.out.println("Usuário do Banco de Dados: " + configLoader.getProperty("db.user"));
        System.out.println("Senha do Banco de Dados: " + configLoader.getProperty("db.password"));
    }

    public String getPassword() {
        return this.getProperty("password");
    }

    public String getEmail() {
        return this.getProperty("email");
    }
}