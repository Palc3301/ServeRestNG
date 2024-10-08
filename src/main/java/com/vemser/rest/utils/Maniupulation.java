package com.vemser.rest.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Maniupulation {

    private Properties Maniupulation() {
        Properties props = new Properties();
        try {
            FileInputStream file = new FileInputStream("src/main/resources/config.properties");
            props.load(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
