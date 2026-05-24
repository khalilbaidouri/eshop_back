package com.eshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.*;

import jakarta.persistence.*;

@Entity
@Table(name = "CLIENTS")
public class Client {

    @Id
    @Column(name = "IDCLIENT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_seq")
    @SequenceGenerator(
            name = "client_seq",
            sequenceName = "SEQ_CLIENT",
            allocationSize = 1
    )
    private Long idclient;

    @Column(name = "CODECLIENT")
    private String codeclient;

    @Column(name = "SOCIETE")
    private String societe;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "ADRESSE")
    private String adresse;

    @Column(name = "VILLE")
    private String ville;

    @Column(name = "PAYS")
    private String pays;

    @Column(name = "CODEPOSTAL")
    private String codePostal;

    @Column(name = "TELEPHONE")
    private String telephone;

    // getters & setters

    public Long getIdclient() { return idclient; }
    public void setIdclient(Long idclient) { this.idclient = idclient; }

    public String getCodeclient() { return codeclient; }
    public void setCodeclient(String codeclient) { this.codeclient = codeclient; }

    public String getSociete() { return societe; }
    public void setSociete(String societe) { this.societe = societe; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getPays() { return pays; }
    public void setPays(String pays) { this.pays = pays; }

    public String getCodePostal() { return codePostal; }
    public void setCodePostal(String codePostal) { this.codePostal = codePostal; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
}