package com.markovic.carfueling.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactForwardController {

    @RequestMapping({
            "/",
            "/cars/**"
    })
    public String forward() {
        return "forward:/index.html";
    }
}