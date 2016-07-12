package com.example.dao;

import com.example.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by toktar on 11.07.2016.
 */
public class JdbsDao {
    private static String tableName = "contacts", name = "name", email = "email", phone = "phone";

   // @Autowired
   static public JdbcTemplate jdbcTemplate;

    public void JdbsDao() {
        createTable();
    }
    public void createTable() {
        jdbcTemplate.execute("DROP TABLE " + tableName + " IF EXISTS");
        StringBuffer query = new StringBuffer("CREATE TABLE " + tableName + "(" +
                "id SERIAL");

        String[] colomnsName = {name, email, phone};
        for (String name : colomnsName) {
            query.append(", " + name + " VARCHAR(255)");
        }

        jdbcTemplate.execute(query.toString());
    }

    public long addElement(Contact element) {
        List<Map<String, Object>> elementInDB = getByNaturKey(element);
        if(elementInDB.size() != 0) {
            System.out.println("Element already exits");
           // return Long.parseLong(elementInDB.iterator().next().remove("id").toString());
            return 0;
        }
        List<Object[]> elementInList = element.toList();
        jdbcTemplate.batchUpdate("INSERT INTO " + tableName + "("+name + ", " + email + ", " + phone + ") VALUES (?,?,?)", elementInList);
        return Long.parseLong(getByNaturKey(element).iterator().next().remove("id").toString());
    }

    public List<Map<String, Object>> getByNaturKey(Contact element) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        StringBuffer query = new StringBuffer("SELECT * FROM " + tableName + " WHERE ");
        query.append(name + " = \"" + element.getName() +"\"");
        query.append(", " + email + " = \"" + element.getEmail() +"\"");
        query.append(", " + phone + " = \"" + element.getPhone() +"\"");
        try {
            resultList = jdbcTemplate.queryForList(query.toString());
        } catch (NullPointerException e) {
           // e.printStackTrace();
        }
        return  resultList;

    }
    public void deleteElement(long id) {
        jdbcTemplate.batchUpdate("DELETE FROM " + tableName + " WHERE id = " + id);
    }
    public List<Map<String,Object>> getList() {
        List<Map<String,Object>> elementsList = jdbcTemplate.queryForList("SELECT * FROM " + tableName);
        return  elementsList;
    }
}
