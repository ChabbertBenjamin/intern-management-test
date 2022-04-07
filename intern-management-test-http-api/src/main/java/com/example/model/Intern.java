package com.example.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;



@RedisHash(value = "Intern",timeToLive = 15)
public class Intern {
    @Id
    private int idIntern;
    private String firstName;
    private String lastName;

    public int getIdIntern() {
        return idIntern;
    }

    public void setIdIntern(int idIntern) {
        this.idIntern = idIntern;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
