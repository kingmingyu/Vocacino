package com.example.voca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VocaController {

    @GetMapping("voca")
    public String vocaP(){

        return "voca";
    }
}
