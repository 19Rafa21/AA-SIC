/**
 * "Visual Paradigm: DO NOT MODIFY THIS FILE!"
 * 
 * This is an automatic generated file. It will be regenerated every time 
 * you generate persistence class.
 * 
 * Modifying its content may cause the program not work, or your work may lost.
 */

/**
 * Licensee: lucas(Universidade do Minho)
 * License Type: Academic
 */
package com.tastycheck.model;
import jakarta.persistence.*;

import java.util.Set;
import java.util.HashSet;

@Entity
@DiscriminatorValue("OWNER")
public class Owner extends User {
    @OneToMany(mappedBy = "owner")
    private Set<Restaurant> restaurants = new HashSet<>();

    public Owner() {
    }

    public Set<Restaurant> getRestaurants() {
        return restaurants;
    }

    public void setRestaurants(Set<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }
    
    @Override
    public String toString() {
        return "Owner{" +
                "restaurants=" + restaurants +
                "} " + super.toString();
    }
}