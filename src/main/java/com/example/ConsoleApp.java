package com.example;

import com.example.dao.HibernateDao;
import com.example.dao.JdbcDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Action;
import javax.xml.ws.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by toktar on 07.07.2016.
 */

@Component
public class ConsoleApp implements CommandLineRunner {

    Scanner in = new Scanner(System.in);

    @Autowired
    ContactService contactService;
    @Autowired
    JdbcDao jdbcDao;

    @Override
    public void run(String... strings) throws IOException, SAXException, ParserConfigurationException {
        jdbcDao.createTable();
        boolean isNotEnd = true;

        while (isNotEnd) {
            System.out.println("show - Show contacts' list");
            System.out.println("del id - Delete contact with id 'id'");
            System.out.println("add - Add contact");
            System.out.println("dbt j/h - Data Base type : jdbc or hibernate");
            System.out.println("end - End application");
            String[] inputData = in.nextLine().split(" ");
            switch (inputData[0]) {
                case "end":
                    isNotEnd = false;
                    break;
                case "show":
                    showContacts();
                    break;
                case "del":
                    if (inputData.length == 2) deleteContact(Long.parseLong(inputData[1]));
                    else
                        showErrorInput();
                    break;
                case "add":
                    addContact();
                    break;
                case "dbt":
                    changeDBType(inputData[1]);
                    break;
                default:
                    showErrorInput();
                    break;
            }

        }
    }

    private void changeDBType(String type) {
        switch (type) {
            case "j":
                contactService.setDbDao(ContactService.DBType.JDBC);
                break;
            case "h":
                contactService.setDbDao(ContactService.DBType.Hibernate);
                break;
            default:
                showErrorInput();
                break;
        }
    }

    private void deleteContact(Long id) throws IOException, ParserConfigurationException {

        if (contactService.deleteContactDB(id)) {
            System.out.println("Delete is successful");
        } else {
            System.out.println("Delete is fail");
        }
    }


    private void addContact() throws ParserConfigurationException {
        String name = "",
                email = "",
                phone = "";
        System.out.print("name: ");
        name = in.next();
        System.out.print("email: ");
        email = in.next();
        System.out.print("phone: ");
        phone = in.next();
        Contact contact = new Contact(name, email, phone);
        if (
                contactService.addContactDB(contact)) {
            System.out.println("Delete is successful");
        } else {
            System.out.println("Delete is fail");
        }
    }

    private void showContacts() throws ParserConfigurationException, SAXException, IOException {
        for (Contact contact : contactService.getListDB()) {
            System.out.println("id:" + Long.toString(contact.getId()));
            System.out.println("name:" + contact.getName());
            if (!contact.getEmail().isEmpty()) System.out.println("email:" + contact.getEmail());
            if (!contact.getPhone().isEmpty()) System.out.println("phone:" + contact.getPhone());
            System.out.println("____________________");
        }
    }


    private void showErrorInput() {
        System.out.println("Intput data is error. Try again, please.");
    }
}
