package com.example.dao;


import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by toktar on 08.07.2016.
 */

@Service
public class XmlDao {
    public File[] getFileList(String path, String mask) throws IOException {
        File f = new File(path); // current directory

        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(mask)) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        File[] files = f.listFiles(textFilter);
        return files;
    }

    public boolean addElement(Document element, String path) {
        try {
            TransformerFactory transformerFactory =
                    TransformerFactory.newInstance();
            Transformer transformer =
                    transformerFactory.newTransformer();
            DOMSource source = new DOMSource(element);
            StreamResult result =
                    new StreamResult(new File(path));
            transformer.transform(source, result);
          /*  // Output to console for testing
            StreamResult consoleResult =
                    new StreamResult(System.out);
            transformer.transform(source, consoleResult);*/
            return true;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return false;

    }

    public boolean deleteElement(String path) {
        File contact = new File(path);
        return contact.delete();
    }

    public Document getElement(File file) {
        Document doc = null;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return doc;

    }


}
