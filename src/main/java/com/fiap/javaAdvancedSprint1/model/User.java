package com.fiap.javaAdvancedSprint1.model;

import java.util.Random;

public class User {
    private Long id;
    private String name;
    private String email;
    private String senha;
    private String cpf;
    private Long idHotel;

    public User(Long id, String name, String email, String senha, String cpf, Long idHotel) {
        this.id = Math.abs(new Random().nextLong());
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.idHotel = null;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getCpf() {
        return cpf;
    }

    public Long getIdHotel() {
        return idHotel;
    }




}
