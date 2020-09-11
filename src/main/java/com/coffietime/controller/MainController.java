package com.coffietime.controller;

import com.coffietime.dao.ProductRepo;
import com.coffietime.domain.Product;
import com.coffietime.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private ProductRepo productRepo;

    @GetMapping("/")
    public String greeting(Map<String, Object> model)
    {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String, Object> model)
    {
        Iterable<Product> products = productRepo.findAll();
        model.put("products", products);
        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String productName,
            @RequestParam String price, Map<String, Object> model) {
        Product product = new Product(productName, Double.parseDouble(price), user);
        productRepo.save(product);

        Iterable<Product> products = productRepo.findAll();
        model.put("products", products);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String productName, Map<String, Object> model) {
        Iterable<Product> products;
        if(productName != null && !productName.isEmpty()) {
            products = productRepo.findByProductName(productName);
        } else {
            products = productRepo.findAll();
        }

        model.put("products", products);
        return "main";
    }
}