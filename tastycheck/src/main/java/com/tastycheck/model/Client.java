package com.tastycheck.model;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User {
    public Client() {
    }
    
    @Override
    public String toString() {
        return "Client{} " + super.toString();
    }
}