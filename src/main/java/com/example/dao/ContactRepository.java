package com.example.dao;

import com.example.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by toktar on 13.07.2016.
 */
//@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {



}
