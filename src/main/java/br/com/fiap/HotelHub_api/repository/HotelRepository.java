package br.com.fiap.HotelHub_api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.HotelHub_api.model.Hotel;
import br.com.fiap.HotelHub_api.model.User;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

    List<Hotel> findByUser(User user);

}
