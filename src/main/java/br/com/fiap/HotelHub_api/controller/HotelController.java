package br.com.fiap.HotelHub_api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.fiap.HotelHub_api.model.Hotel;
import br.com.fiap.HotelHub_api.model.User;
import br.com.fiap.HotelHub_api.repository.HotelRepository;
import br.com.fiap.HotelHub_api.repository.QuartoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("hotel")
@Slf4j
public class HotelController {


    @Autowired
    private HotelRepository repository;

    @Autowired
    private QuartoRepository quartoRepository;


    @GetMapping
    @Operation(summary = "Listar hoteis", description = "Retorna um array com todos os hoteis cadastrados")
    @Cacheable("hoteis")
    public List<Hotel> index(@AuthenticationPrincipal User user) {
        return repository.findByUser(user);
    }

    @PostMapping
    @CacheEvict(value = "Hotel", allEntries = true)
    @Operation(responses = @ApiResponse(responseCode = "400", description = "Validação falhou"))
    @ResponseStatus(code = HttpStatus.CREATED)
    public Hotel create(@RequestBody @Valid Hotel hotel, @AuthenticationPrincipal User user){
        log.info("Cadastrando hotel" + hotel.getNome());
        hotel.setUser(user);
        return repository.save(hotel);
    }

    @GetMapping("{id}")
    @Cacheable("hoteis")
    public ResponseEntity<Hotel> get(@PathVariable Long id, @AuthenticationPrincipal User user){
        log.info("Buscando hotel" + id);

        
        return ResponseEntity.ok(getHotel(id, user));
    }

    
    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal User user){
        log.info("Deletando hotel " + id);

        var hotel = getHotel(id, user);

        quartoRepository.deleteAllByHotel(hotel);


        repository.delete(hotel);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Hotel> update(@PathVariable Long id, @RequestBody @Valid Hotel hotel, @AuthenticationPrincipal User user){
        log.info("Atualizando hotel" + id + "com" + hotel);

        var oldHotel = getHotel(id, user);
        BeanUtils.copyProperties(hotel, oldHotel, "id", "user");
        repository.save(oldHotel);
        return ResponseEntity.ok(oldHotel);
    }



    private Hotel getHotel(Long id, User user) {
    var hotel = repository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado"));

    System.out.println("ID do usuário autenticado: " + user.getId());
    System.out.println("ID do dono do hotel: " + hotel.getUser().getId());

    if (!hotel.getUser().getId().equals(user.getId())) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    return hotel;
}
}
