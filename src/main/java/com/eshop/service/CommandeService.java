package com.eshop.service;

/**
 * @author $ {USERS}
 **/



import com.eshop.entity.Commande;
import com.eshop.repository.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandeService {

    private final CommandeRepository repo;

    public CommandeService(CommandeRepository repo) {
        this.repo = repo;
    }

    public List<Commande> findAll() {
        return repo.findAll();
    }

    public Commande save(Commande c) {
        return repo.save(c);
    }
}