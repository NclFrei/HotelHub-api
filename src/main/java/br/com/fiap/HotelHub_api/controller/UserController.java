package br.com.fiap.HotelHub_api.controller;

import br.com.fiap.HotelHub_api.model.UserModel;
import br.com.fiap.HotelHub_api.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping
    @CacheEvict(value = "user", allEntries = true)
    @Operation(responses = @ApiResponse(responseCode = "400", description = "Validação falhou"))
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserModel create(@RequestBody @Valid UserModel user){
        log.info("Cadastrando categoria" + user.getNome());
        return repository.save(user);
    }


    private UserModel getUser(Long id) {
        return repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado")
                );
    }
}
