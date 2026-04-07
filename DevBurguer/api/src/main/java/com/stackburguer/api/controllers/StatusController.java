package com.stackburguer.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StatusController {

    @GetMapping("/status")
    public String checkStatus(){
        return "StackBurguer API está online e Rodando e testada";
    }
}
