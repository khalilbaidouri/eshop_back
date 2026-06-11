package com.eshop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name = "CATEGORIES")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "categorie_seq")
    @SequenceGenerator(name = "categorie_seq", sequenceName = "SEQ_CATEGORIE", allocationSize = 1)
    @Column(name = "IDCATEGORIE")
    private Long idCategorie;

    @Column(name = "DESIGNATION", nullable = false)
    private String designation;

    public Categorie() {}

    @JsonProperty("idCategorie")
    public Long getIdCategorie() { return idCategorie; }
    public void setIdCategorie(Long idCategorie) { this.idCategorie = idCategorie; }

    @JsonProperty("designation")
    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }
}