package com.example.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by toktar on 15.07.2016.
 */
@Component
public class Serv implements CommandLineRunner  {

    @Autowired
    Test test;

    @Override
    public void run(String... strings) throws Exception {
        System.out.print("ggggg");
        test.run();
    }
}
