package br.com.fiap.HotelHub_api.controller;

import br.com.fiap.HotelHub_api.model.UserModel;
import br.com.fiap.HotelHub_api.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository repository;

    @GetMapping
    @Operation(summary = "Listar usuarios", description = "Retorna um array com todas os usuarios")
    @Cacheable("user")
    public List<UserModel> index() {
        return repository.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserModel> get(@PathVariable Long id) {
        return ResponseEntity.ok(getUser(id));
    }


    private UserModel getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );
    }
}
