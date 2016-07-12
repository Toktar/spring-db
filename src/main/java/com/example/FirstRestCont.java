package com.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by toktar on 08.07.2016.
 */
@RestController
@RequestMapping(value = "/api")
public class FirstRestCont {

    @RequestMapping(value = "/ololo", method = RequestMethod.GET)
    public String getOlolo() {
        return "ololo";
    }
}
