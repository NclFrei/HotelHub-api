package br.com.fiap.HotelHub_api.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min= 3, max=75)
    private String Nome;

    @NotBlank(message = "Email não pode estar em branco")
    @Size(min= 3, max=100)
    private String Email;

    @NotBlank(message = "Senha não pode estar em branco")
    @Size(min= 3, max=150)
    private String Senha;


    private String Cargo;


    @Enumerated(EnumType.STRING)
    private UserStatus Status;

}
