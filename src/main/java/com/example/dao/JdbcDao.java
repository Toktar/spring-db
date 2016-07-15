package com.example.dao;

import com.example.Contact;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by toktar on 11.07.2016.
 */
@Component("jdbcDao")
public class JdbcDao extends AbstractDBDao {


    /*   private JdbcTemplate jdbcTemplate;

       @Autowired
       public void setDataSource(DataSource dataSource) {
           this.jdbcTemplate = new JdbcTemplate(dataSource);
       }*/
    private String tableName = "contacts", name = "name", email = "email", phone = "phone";

    @Autowired
    public JdbcTemplate jdbcTemplate;

    public void createTable() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS " + tableName + "");
        StringBuffer query = new StringBuffer("CREATE TABLE " + tableName + "(" +
                "id SERIAL");

        String[] colomnsName = {name, email, phone};
        for (String name : colomnsName) {
            query.append(", " + name + " VARCHAR(255)");
        }
        query.append(")");
        jdbcTemplate.execute(query.toString());
    }

    @Override
    public void addElement(Contact element) {
        List<Object[]> elementInList = element.toList();
        jdbcTemplate.batchUpdate("INSERT INTO " + tableName + "(" + name + ", " + email + ", " + phone + ") VALUES (?,?,?)", elementInList);
    }


    @Override
    public void deleteElement(Contact contact) {
        jdbcTemplate.batchUpdate("DELETE FROM " + tableName + " WHERE id = " + contact.getId());
    }

    @Override
    public List<Contact> getList() {
        List<Contact> contactMap = new ArrayList<>();
        for (Map<String, Object> element : getListOfMap()) {
            Contact contact = new Contact();
            contact.setId(Long.parseLong(element.get("id").toString()));
            contact.setName(element.get(name).toString());
            contact.setEmail(element.get(email).toString());
            contact.setPhone(element.get(phone).toString());
            contactMap.add(contact);
        }
        return contactMap;
    }

    public List<Map<String, Object>> getListOfMap() {
        List<Map<String, Object>> elementsList = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
        return elementsList;
        //  return  null;
    }
}
