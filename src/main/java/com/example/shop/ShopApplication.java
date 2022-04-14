package com.example.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ShopApplication {

    private static Application app;

    @Autowired
    public ShopApplication(Application app) {
        ShopApplication.app = app;
    }

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
        app.start();
    }
}
