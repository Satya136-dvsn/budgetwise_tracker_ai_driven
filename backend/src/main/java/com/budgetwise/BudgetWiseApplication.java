package com.budgetwise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class BudgetWiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BudgetWiseApplication.class, args);
    }
}
