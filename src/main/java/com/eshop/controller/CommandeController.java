package com.eshop.controller;

/**
 * @author $ {USERS}
 **/



import com.eshop.entity.Commande;
import com.eshop.service.CommandeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commandes")
@CrossOrigin("*")
public class CommandeController {

    private final CommandeService service;

    public CommandeController(CommandeService service) {
        this.service = service;
    }

    @GetMapping
    public List<Commande> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Commande add(@RequestBody Commande c) {
        System.out.println("POST OK");
        return service.save(c);
    }
}