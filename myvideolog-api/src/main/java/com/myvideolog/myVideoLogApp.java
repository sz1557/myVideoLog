package com.myvideolog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class myVideoLogApp {
    public static void main(String[] args) {
        ApplicationContext app = SpringApplication.run(myVideoLogApp.class, args);
    }
}
