package br.com.fiap.HotelHub_api.repository;

import br.com.fiap.HotelHub_api.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
}
