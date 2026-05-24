package com.eshop.service;

import com.eshop.entity.Produit;
import com.eshop.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author $ {USERS}
 **/
@Service
public class ProduitService {

    @Autowired
    private ProduitRepository repo;

    public List<Produit> findAll() {
        return repo.findAll();
    }

    public Produit save(Produit p) {
        return repo.save(p);
    }
}