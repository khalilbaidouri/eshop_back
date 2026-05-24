package com.eshop.repository;

/**
 * @author $ {USERS}
 **/



import com.eshop.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
