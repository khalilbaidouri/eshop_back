package com.eshop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "COMMANDES")
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cmd_seq")
    @SequenceGenerator(name = "cmd_seq", sequenceName = "SEQ_COMMANDE", allocationSize = 1)
    private Long idcommande;

    @Column(name = "IDCLIENT", nullable = false)
    private Long idclient;

    @Column(name = "IDEMPLOYE")
    private Long idemploye;

    @Column(name = "DATECOMMANDE", updatable = false)
    private LocalDate dateCommande;

    @Column(name = "DATELIVRAISON")
    private LocalDate dateLivraison;

    public Commande() {}

    @PrePersist
    public void prePersist() {
        this.dateCommande = LocalDate.now();
    }

    // getters & setters...
}