package com.eshop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "LIGNECOMMANDES", schema = "ESHOP")
@Data
public class LigneCommande {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ligne_seq")
    @SequenceGenerator(
            name = "ligne_seq",
            sequenceName = "ESHOP.SEQ_LIGNE",
            allocationSize = 1
    )
    @Column(name = "IDLIGNECOMMANDE")
    private Long idLigneCommande;

    @Column(name = "IDCOMMANDE")
    private Long idCommande;

    @Column(name = "IDPRODUIT")
    private Long idProduit;

    @Column(name = "QUANTITE")
    private Integer quantite;

    @Column(name = "PRIXUNITAIRE")
    private Double prixUnitaire;

    @Column(name = "REMISE")
    private Double remise;
}