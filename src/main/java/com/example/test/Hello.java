package com.example.test;

import com.example.dao.HibernateDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by toktar on 14.07.2016.
 */

@Service
public class Hello {

   /* @Autowired
    public Hello hello() {
        return new Hello();
    }*/


    /*public Hello() {
        System.out.println("Hello!");
    }*/

  /*  @Autowired
    HibernateDao hibernateDao;*/

    public void say() {
        System.out.println("123245456567");

    }
}
