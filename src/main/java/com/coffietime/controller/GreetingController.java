package com.coffietime.controller;

import com.coffietime.dao.UserRepo;
import com.coffietime.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World")
                                       String name, Map<String, Object> model)
    {
        model.put("name", name);
        return "greeting";
    }

    @GetMapping
    public String main(Map<String, Object> model)
    {
        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String name, @RequestParam String email, Map<String, Object> model) {
        User user = new User(name, email);
        userRepo.save(user);

        Iterable<User> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam String name, Map<String, Object> model) {
        Iterable<User> users;
        if(name != null && !name.isEmpty()) {
            users = userRepo.findByName(name);
        } else {
            users = userRepo.findAll();
        }

        model.put("users", users);
        return "main";
    }
}