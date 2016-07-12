package com.example;

import com.example.dao.JdbsDao;
import com.example.dao.XmlDao;
import org.springframework.stereotype.Component;
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
import java.util.Map;

/**
 * Created by toktar on 08.07.2016.
 */
@Component
public class ContactService {
    private HashMap<Long, Contact> contactMap;

    public void setXmlDao(XmlDao xmlDao) {
        this.xmlDao = xmlDao;
    }

    //@Autowired
    //@Qualifier("xml")
    private XmlDao xmlDao = new XmlDao();
    private JdbsDao jdbsDao = new JdbsDao();

    public boolean deleteContactXml(Contact contact) {
        return  contact.getId() != 0 && xmlDao.deleteElement("./" + contact.getId()+ "_contact.xml");
    }
    public boolean deleteContactDB(Contact contact) {
        jdbsDao.deleteElement(contact.getId());
        return true;
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
            operationResult = xmlDao.addElement(doc, "./"+contact.getId()+"_contact.xml");

        } catch (Exception e) {
            e.printStackTrace();
        }



        return  operationResult;
    }
    public boolean addContactDB(Contact contact) {
        long id = jdbsDao.addElement(contact);
        contact.setId(id);
        return id!=0;
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

        File[] files = xmlDao.getFileList(".","_contact.xml");
        for (File file : files) {
            if (!file.isDirectory()) {
                Contact contact = getContactFromXml(file);
                if(contact.getId() == 0) continue;
                contactMap.put(contact.getId(), contact);
            }

        }


        return contactMap;

    }
    public HashMap<Long,Contact> getContactMapDB() {
        HashMap<Long, Contact> contactMap = new HashMap<Long, Contact>();
        for (Map<String, Object> element : jdbsDao.getList()) {
            Contact contact = new  Contact();
            contact.setId(Long.parseLong(element.get("id").toString()));
            contact.setName(element.get("name").toString());
            contact.setEmail(element.get("email").toString());
            contact.setPhone(element.get("phone").toString());
            contactMap.put(Long.parseLong(element.get("id").toString()), contact);
        }
        return contactMap;
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



}
