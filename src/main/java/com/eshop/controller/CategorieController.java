package com.eshop.controller;

/**
 * @author $ {USERS}
 **/



import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eshop.entity.Categorie;
import com.eshop.service.CategorieService;

@RestController
@RequestMapping("/api/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    // GET all categories
    @GetMapping
    public List<Categorie> getAll() {
        return categorieService.findAll();
    }

    // POST add category
    @PostMapping
    public Categorie add(@RequestBody Categorie categorie) {
        return categorieService.save(categorie);
    }
}