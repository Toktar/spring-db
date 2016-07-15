package com.example.dao;

import com.example.Contact;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by toktar on 08.07.2016.
 */

public abstract class AbstractDBDao {

    public abstract void deleteElement(Contact contact);
    public abstract void addElement(Contact contact);
    public abstract List<Contact> getList();


}
