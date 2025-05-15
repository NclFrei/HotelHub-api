package br.com.fiap.HotelHub_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

import br.com.fiap.HotelHub_api.model.Quarto;
import br.com.fiap.HotelHub_api.model.User;
import br.com.fiap.HotelHub_api.repository.HotelRepository;
import br.com.fiap.HotelHub_api.repository.QuartoRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/hotel/{hotelId}/quartos")
public class QuartoController {
    
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @GetMapping
    public List<Quarto> index(@PathVariable Long hotelId, @AuthenticationPrincipal User user) {
        var hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!hotel.getUser().equals(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return quartoRepository.findByHotel(hotel);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quarto create(@PathVariable Long hotelId, @RequestBody @Valid Quarto quarto, @AuthenticationPrincipal User user) {
        var hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!hotel.getUser().equals(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        quarto.setHotel(hotel);
        return quartoRepository.save(quarto);
    }

    @GetMapping("{id}")
    public Quarto show(@PathVariable Long hotelId, @PathVariable Long id, @AuthenticationPrincipal User user) {
        var hotel = getHotelDoUsuario(hotelId, user);
        var quarto = quartoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!quarto.getHotel().equals(hotel))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        return quarto;
    }

    @PutMapping("{id}")
    public Quarto update(@PathVariable Long hotelId, @PathVariable Long id,
                    @RequestBody @Valid Quarto atualizado,
                    @AuthenticationPrincipal User user) {
        var hotel = getHotelDoUsuario(hotelId, user);
        var quarto = quartoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!quarto.getHotel().equals(hotel))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        quarto.setNumero(atualizado.getNumero());
        quarto.setTipo(atualizado.getTipo());
        quarto.setPrecoDiaria(atualizado.getPrecoDiaria());

        return quartoRepository.save(quarto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long hotelId, @PathVariable Long id,
                        @AuthenticationPrincipal User user) {
        var hotel = getHotelDoUsuario(hotelId, user);
        var quarto = quartoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!quarto.getHotel().equals(hotel))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        quartoRepository.delete(quarto);
    }

    // Método utilitário para evitar repetição
    private br.com.fiap.HotelHub_api.model.Hotel getHotelDoUsuario(Long hotelId, User user) {
        var hotel = hotelRepository.findById(hotelId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel não encontrado"));
        if (!hotel.getUser().equals(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso negado ao hotel");
        return hotel;
    }
}