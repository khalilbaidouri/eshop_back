package com.eshop.repository;

/**
 * @author $ {USERS}
 **/
import com.eshop.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
}