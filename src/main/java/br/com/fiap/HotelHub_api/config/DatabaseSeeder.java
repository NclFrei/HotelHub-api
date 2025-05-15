package br.com.fiap.HotelHub_api.config;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.fiap.HotelHub_api.model.Hotel;
import br.com.fiap.HotelHub_api.model.Quarto;
import br.com.fiap.HotelHub_api.model.User;
import br.com.fiap.HotelHub_api.model.UserStatus;
import br.com.fiap.HotelHub_api.repository.HotelRepository;
import br.com.fiap.HotelHub_api.repository.QuartoRepository;
import br.com.fiap.HotelHub_api.repository.UserRepository;
import jakarta.annotation.PostConstruct;

@Component
public class DatabaseSeeder {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private QuartoRepository quartoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        String password = passwordEncoder.encode("12345");
        var nicollas = User.builder().email("nicollas@fiap.com.br").password(password).nome("Nicollas Frei").cargo("Gerente").Status(UserStatus.Ativo).build();
        var eduardo  = User.builder().email("eduardo@fiap.com.br").password(password).nome("Eduardo ").cargo("Dono").Status(UserStatus.Ativo).build();
        userRepository.saveAll(List.of(nicollas, eduardo));
    

    var hotel1 = Hotel.builder()
                .nome("Hotel Paraiso")
                .endereco("Av. Brasil, 123")
                .telefone("11999999999")
                .user(nicollas)
                .build();

        var hotel2 = Hotel.builder()
                .nome("Hotel Tropical")
                .endereco("Rua das Flores, 456")
                .telefone("11888888888")
                .user(eduardo)
                .build();

        hotelRepository.saveAll(List.of(hotel1, hotel2));

        // Criando quartos para o hotel1
        var quarto1 = Quarto.builder()
                .numero("101")
                .tipo("Luxo")
                .precoDiaria(BigDecimal.valueOf(350.00))
                .hotel(hotel1)
                .build();

        var quarto2 = Quarto.builder()
                .numero("102")
                .tipo("Duplo")
                .precoDiaria(BigDecimal.valueOf(250.00))
                .hotel(hotel1)
                .build();

        quartoRepository.saveAll(List.of(quarto1, quarto2));
    }
}

