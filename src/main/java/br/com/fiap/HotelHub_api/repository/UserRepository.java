package br.com.fiap.HotelHub_api.repository;

import br.com.fiap.HotelHub_api.model.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);
}
