package com.eshop.entity;

/**
 * @author $ {USERS}
 **/

import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorie_seq")
    @SequenceGenerator(
            name = "categorie_seq",
            sequenceName = "SEQ_CATEGORIES",
            allocationSize = 1
    )
    @Column(name = "IDCATEGORIE")
    private Long idCategorie;

    @Column(name = "DESIGNATION", nullable = false)
    private String designation;

    public Categorie() {}

    public Long getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Long idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
