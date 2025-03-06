package com.fiap.javaAdvancedSprint1.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.javaAdvancedSprint1.model.User;

@RestController
public class UserController {

    private List<User> repository = new ArrayList<>();

    @GetMapping("/users")
    public List<User> index() {
        return repository;
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        System.out.println("Cadastrando categoria " + user.getName());
        repository.add(user);
        return user;
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable Long id) {
        System.out.println("Buscando categoria " + id);
        var user = repository.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.get());
    }

}
