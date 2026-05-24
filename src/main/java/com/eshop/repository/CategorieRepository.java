package com.eshop.repository;

/**
 * @author $ {USERS}
 **/
import com.eshop.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}