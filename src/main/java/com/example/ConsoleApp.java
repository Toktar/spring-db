package com.example;

import org.springframework.context.ApplicationContext;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Service;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by toktar on 07.07.2016.
 */
public class ConsoleApp {
    static ContactService contactService;
    static Map<Long, Contact> contacts = new HashMap<Long, Contact>();
    static Scanner in = new Scanner(System.in);


    public static void run(ApplicationContext context) throws IOException, SAXException, ParserConfigurationException {
        if(context==null) return;
        contactService = (ContactService) context.getBean("contactService");
        //Contact.context = context;
        boolean isNotEnd = true;

        while (isNotEnd) {
            contactsUpdate();
            System.out.println("show - Show contacts' list");
            System.out.println("del id - Delete contact with id 'id'");
            System.out.println("add - Add contact");
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
                    if (inputData.length == 2) deleteContact(Integer.parseInt(inputData[1]));
                    else
                        showErrorInput();
                    break;
                case "add":
                    addContact();
                    break;
                default:
                    showErrorInput();
                    break;
            }

        }
    }

    private static void deleteContact(Integer id) throws IOException, ParserConfigurationException {
        contactsUpdate();
        Contact contact = contacts.get(id);
        if (contactService.deleteContactDB(contact)) {
            contacts.remove(id);
        } else {
            System.out.println("Delete is fail");
        }
    }


    private static void addContact() throws ParserConfigurationException {
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
        contacts.put(contact.getId(), contact);
        contactService.addContactDB(contact);
    }

    private static void showContacts() throws ParserConfigurationException, SAXException, IOException {

        for (Map.Entry<Long, Contact> contact : contacts.entrySet()) {
            System.out.println("id:" + Long.toString(contact.getValue().getId()));
            System.out.println("name:" + contact.getValue().getName());
            if (contact.getValue().getEmail() != "") System.out.println("email:" + contact.getValue().getEmail());
            if (contact.getValue().getPhone() != "") System.out.println("phone:" + contact.getValue().getPhone());
            System.out.println("____________________");
        }
    }

    private static void contactsUpdate() throws IOException, ParserConfigurationException {
        contacts = contactService.getContactMap();

    }

    private static void showErrorInput() {
        System.out.println("Intput data is error. Try again, please.");
    }
}
