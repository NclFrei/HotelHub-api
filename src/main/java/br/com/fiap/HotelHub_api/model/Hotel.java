package br.com.fiap.HotelHub_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Hotel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min= 3, max=75)
    private String nome;

    @NotBlank(message = "Endereco não pode estar em branco")
    @Size(min= 3, max=100)
    private String endereco;

    @NotBlank(message = "Telefone não pode estar em branco")
    @Size(min= 3, max=100)
    private String telefone;

    @ManyToOne
    @JsonIgnore
    private User user;
    
}
