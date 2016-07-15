package com.example;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by toktar on 12.07.2016.
 */

@Entity
@Table(name = "Contacts")
public class Contact {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }



    public Contact(long id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public Contact() {}





    @Override
    public String toString() {
        return String.format(
                "Customer[id=%d, Name='%s', email='%s', phone='%s']",
                id, name, email, phone);
    }

    public List<Object[]> toList() {
        List<Object[]> contactsList = new ArrayList<>();
        contactsList.add(new String[]{this.name, this.email, this.phone});
        return contactsList;
    }

    // getters & setters omitted for brevity
}