package com.eshop.controller;

import com.eshop.entity.Client;
import com.eshop.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients/kk")
@CrossOrigin(origins = "*") // important pour Next.js
public class ClientController {

    @Autowired
    private final ClientService service;

    public ClientController(ClientService service) {
        this.service = service;
    }

    // GET ALL
    @GetMapping
    public List<Client> getAll() {
        return service.findAll();
    }

    // POST ADD
    @PostMapping
    public Client create(@RequestBody Client client) {
        return service.save(client);
    }
}