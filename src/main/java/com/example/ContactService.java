package com.example;

import com.example.dao.AbstractDBDao;
import com.example.dao.HibernateDao;
import com.example.dao.JdbcDao;
import com.example.dao.XmlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by toktar on 08.07.2016.
 */


@Service("contactService")
//@Transactional
public class ContactService {
    List<Contact> contactList;

    enum DBType {JDBC, Hibernate}

    private HashMap<Long, Contact> contactMap;
    protected static Logger logger = Logger.getLogger("service");

    public void setXmlDao(XmlDao xmlDao) {
        this.xmlDao = xmlDao;
    }

    //@Autowired
    //@Qualifier("xml")

    @Autowired
    private XmlDao xmlDao;

    @Autowired
    private JdbcDao jdbcDao;

    @Autowired
    private HibernateDao hibernateDao = new HibernateDao();

    public void setDbDao(DBType type) {
        if (type == DBType.Hibernate)
            dbDao = hibernateDao;
        else
            dbDao = jdbcDao;
    }

    @Autowired
    @Qualifier("jdbcDao")
    private AbstractDBDao dbDao;


    public boolean deleteContactXml(Contact contact) {
        return contact.getId() != 0 && xmlDao.deleteElement("./" + contact.getId() + "_contact.xml");
    }

    public boolean deleteContactDB(long id) {
        updateContactsList();
        Contact contact = getContactById(id);
        if (contact != null) {
            dbDao.deleteElement(contact);
            return true;
        }
        return false;
    }

    public boolean addContactXml(Contact contact) {
        boolean operationResult = false;
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder =
                    dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // root element
            Element rootElement = doc.createElement("contacts");
            doc.appendChild(rootElement);

            //  supercars element
            Element supercar = doc.createElement("contact");
            rootElement.appendChild(supercar);

            // carname element
            Element curElement = doc.createElement("id");
            curElement.appendChild(
                    doc.createTextNode(Long.toString(contact.getId())));
            supercar.appendChild(curElement);


            // carname element
            curElement = doc.createElement("name");
            curElement.appendChild(
                    doc.createTextNode(contact.getName()));
            supercar.appendChild(curElement);

            // carname element
            curElement = doc.createElement("email");
            curElement.appendChild(
                    doc.createTextNode(contact.getEmail()));
            supercar.appendChild(curElement);

            // carname element
            curElement = doc.createElement("phone");
            curElement.appendChild(
                    doc.createTextNode(contact.getPhone()));
            supercar.appendChild(curElement);


            // write the content into xml file
            operationResult = xmlDao.addElement(doc, "./" + contact.getId() + "_contact.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return operationResult;
    }

    public boolean addContactDB(Contact contact) {
        updateContactsList();
        if (contact != null && !contactList.contains(contact)) {
            dbDao.addElement(contact);
            return true;
        }
        return false;
    }

    public HashMap<Long, Contact> getContactMap() throws ParserConfigurationException, IOException {
        return getContactMapXml();
        //return getContactMapDB();
    }

    public HashMap<Long, Contact> getContactMapXml() throws ParserConfigurationException, IOException {
        HashMap<Long, Contact> contactMap = new HashMap<Long, Contact>();
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setValidating(false);
        DocumentBuilder builder = f.newDocumentBuilder();

        File[] files = xmlDao.getFileList(".", "_contact.xml");
        for (File file : files) {
            if (!file.isDirectory()) {
                Contact contact = getContactFromXml(file);
                if (contact.getId() == 0) continue;
                contactMap.put(contact.getId(), contact);
            }

        }


        return contactMap;

    }

    public List<Contact> getListDB() {
        return dbDao.getList();
    }

    public void updateContactsList() {
        contactList = getListDB();
    }


    private Contact getContactFromXml(File file) {
        String name = "",
                email = "",
                phone = "";
        int id = 0;
        try {


            Document doc = xmlDao.getElement(file);

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            //System.out.println("Root element :" + doc.getDocumentElement().getNodeName()); ???

            NodeList nList = doc.getElementsByTagName("contact");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                //  System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    id = Integer.parseInt(eElement.getElementsByTagName("id").item(0).getTextContent());
                    name = eElement.getElementsByTagName("name").item(0).getTextContent();
                    email = eElement.getElementsByTagName("email").item(0).getTextContent();
                    phone = eElement.getElementsByTagName("phone").item(0).getTextContent();

                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }

        return new Contact(id, name, email, phone);
    }

    public Contact getContactById(long id) {
        for (Contact contact : contactList) {
            if (contact.getId() == id) return contact;
        }

        return null;
    }


}
