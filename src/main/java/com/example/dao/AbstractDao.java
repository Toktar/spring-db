package com.example.dao;

import org.w3c.dom.Document;

import java.io.File;
import java.io.IOException;

/**
 * Created by toktar on 08.07.2016.
 */

public abstract class AbstractDao {

    public abstract File[] getFileList(String path, String mask) throws IOException;

    public abstract boolean addElement(Document element, String path);

    public abstract boolean deleteElement(String path);

    public abstract Document getElement(File file);
}
