package com.example.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by toktar on 14.07.2016.
 */

@Service
public class Test {

   // @Autowired
    //public Test test(){
    //    return new Test();
    //}

   @Autowired
    @Qualifier("hello")
    Hello hello;

    public void run() throws IOException, SAXException, ParserConfigurationException {
    hello.say();
}
}
