package com.eshop.entity;

import jakarta.persistence.*;

/**
 * @author $ {USERS}
 **/

import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUITS")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produit_seq")
    @SequenceGenerator(
            name = "produit_seq",
            sequenceName = "SEQ_PRODUIT",
            allocationSize = 1
    )
    @Column(name = "IDPRODUIT")
    private Long idProduit;

    @Column(name = "DESIGNATION", nullable = false)
    private String designation;

    @Column(name = "PRIXUNITAIRE")
    private Double prixUnitaire;

    @Column(name = "UNITEVENTE")
    private String uniteVente;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDCATEGORIE", nullable = false)
    private Categorie categorie;

    public Produit() {}

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getUniteVente() {
        return uniteVente;
    }

    public void setUniteVente(String uniteVente) {
        this.uniteVente = uniteVente;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}