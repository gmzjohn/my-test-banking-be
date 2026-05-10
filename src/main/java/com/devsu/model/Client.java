package com.devsu.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Client extends Person {

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean status;

    public Client() {}

    public Client(Long id, String name, String gender, Integer age, String identification, String address, String phoneNumber, String password, Boolean status) {
        super(id, name, gender, age, identification, address, phoneNumber);
        this.password = password;
        this.status = status;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}
