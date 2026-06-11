package com.eshop.controller;

import com.eshop.entity.Produit;
import com.eshop.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author $ {USERS}
 **/
@RestController
@RequestMapping("/api/produits")
@CrossOrigin("*")
public class ProduitController {

    @Autowired
    private ProduitService service;
/**
    @GetMapping
    public List<Produit> getAll() {
        return service.findAll();
    } **/

    @PostMapping
    public Produit create(@RequestBody Produit p) {
        return service.save(p);
    }
}