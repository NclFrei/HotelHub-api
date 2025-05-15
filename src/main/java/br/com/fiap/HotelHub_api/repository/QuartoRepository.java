package br.com.fiap.HotelHub_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.HotelHub_api.model.Hotel;
import br.com.fiap.HotelHub_api.model.Quarto;

public interface QuartoRepository extends JpaRepository<Quarto, Long> {
    List<Quarto> findByHotel(Hotel hotel);

    void deleteAllByHotel(Hotel hotel);
}