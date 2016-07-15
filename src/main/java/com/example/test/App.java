package com.example.test;

import com.example.Config;
import com.example.DemoApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Created by toktar on 14.07.2016.
 */

@SpringBootApplication
@ComponentScan
@EnableAutoConfiguration
public class App {

 /*   @Autowired
    private Test test;*/



    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        SpringApplication.run(App.class, args);
        ApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
      // hello = (Hello) context.getBean(Hello.class);
        //hello.say();

  /*      Test test = (Test) context.getBean(Test.class);
        test.run();*/

    }

}
