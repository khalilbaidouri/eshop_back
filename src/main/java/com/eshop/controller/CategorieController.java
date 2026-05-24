package com.eshop.controller;

/**
 * @author $ {USERS}
 **/



import com.eshop.entity.Categorie;
import com.eshop.service.CategorieService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories/kk")
@CrossOrigin(origins = "*")
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