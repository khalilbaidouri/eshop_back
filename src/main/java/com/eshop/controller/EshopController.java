package com.eshop.controller;
import org.springframework.jdbc.core.JdbcTemplate;

import com.eshop.entity.LigneCommande;
import com.eshop.service.EshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EshopController {
    private final JdbcTemplate jdbcTemplate;

    private final EshopService service;

    @GetMapping("/compteurs")
    public Map<String, Long> compteurs() {
        return service.getCompteurs();
    }

    @GetMapping("/lignes")
    public List<LigneCommande> lignes() {
        return service.getAllLignes();
    }

    @PostMapping("/lignes")
    public LigneCommande create(@RequestBody LigneCommande ligne) {
        return service.saveLigne(ligne);
    }

    @PutMapping("/lignes/{id}")
    public LigneCommande update(@PathVariable Long id, @RequestBody LigneCommande ligne) {
        ligne.setIdLigneCommande(id);
        return service.saveLigne(ligne);
    }

    @DeleteMapping("/lignes/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteLigne(id);
    }

    @GetMapping("/stats/ca")
    public List<Map<String, Object>> ca() {
        return service.getCaParCategorie();
    }
    @GetMapping("/global")
    public List<Map<String, Object>> global() { return service.getLignesGlobal(); }

    @GetMapping("/site1")
    public List<Map<String, Object>> site1() { return service.getLignesSite1(); }

    @GetMapping("/site2")
    public List<Map<String, Object>> site2() { return service.getLignesSite2(); }



    @GetMapping("/commandes")
    public List<Map<String, Object>> commandes() { return service.getCommandes(); }
    @PostMapping("/global/lignes")
    public Map<String, Object> insertGlobal(@RequestBody Map<String, Object> body) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            String sql = """
        INSERT INTO ESHOP.LIGNECOMMANDES 
        (IDLIGNECOMMANDE, IDCOMMANDE, IDPRODUIT, QUANTITE, PRIXUNITAIRE, REMISE)
        VALUES (eshop.seq_ligne.NEXTVAL, ?, ?, ?, ?, ?)
        """;

            jdbcTemplate.update(sql,
                    Long.parseLong(body.get("idCommande").toString()),
                    Long.parseLong(body.get("idProduit").toString()),
                    Integer.parseInt(body.get("quantite").toString()),
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString())
            );

            int qte = Integer.parseInt(body.get("quantite").toString());

            result.put("success", true);
            result.put("site", qte >= 100 ? "Site1" : "Site2");
            result.put("message", "Ligne insérée avec séquence + routing OK");

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }
    @PostMapping("/site1/lignes")
    public Map<String, Object> insertSite1(@RequestBody Map<String, Object> body) {

        Map<String, Object> result = new LinkedHashMap<>();

        try {
            int qte = Integer.parseInt(body.get("quantite").toString());

            if (qte < 100) {
                result.put("success", false);
                result.put("message", "Site1 accepte uniquement Quantite >= 100");
                return result;
            }

            Long id = jdbcTemplate.queryForObject(
                    "SELECT ESHOP.SEQ_LIGNE.NEXTVAL FROM dual",
                    Long.class
            );

            String sql = """
        INSERT INTO ESHOP1.LIGNECOMMANDES1@LINK_SITE1
        (IDLIGNECOMMANDE, IDCOMMANDE, IDPRODUIT, QUANTITE, PRIXUNITAIRE, REMISE)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

            jdbcTemplate.update(sql,
                    id,
                    Long.parseLong(body.get("idCommande").toString()),
                    Long.parseLong(body.get("idProduit").toString()),
                    qte,
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString())
            );

            result.put("success", true);
            result.put("id", id);
            result.put("message", "Ligne insérée sur Site1");

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
    @PostMapping("/site2/lignes")
    public Map<String, Object> insertSite2(@RequestBody Map<String, Object> body) {

        Map<String, Object> result = new LinkedHashMap<>();

        try {
            int qte = Integer.parseInt(body.get("quantite").toString());

            if (qte >= 100) {
                result.put("success", false);
                result.put("message", "Site2 accepte uniquement Quantite < 100");
                return result;
            }

            Long id = jdbcTemplate.queryForObject(
                    "SELECT ESHOP.SEQ_LIGNE.NEXTVAL FROM dual",
                    Long.class
            );

            String sql = """
        INSERT INTO ESHOP2.LIGNECOMMANDES2@LINK_SITE2
        (IDLIGNECOMMANDE, IDCOMMANDE, IDPRODUIT, QUANTITE, PRIXUNITAIRE, REMISE)
        VALUES (?, ?, ?, ?, ?, ?)
        """;

            jdbcTemplate.update(sql,
                    id,
                    Long.parseLong(body.get("idCommande").toString()),
                    Long.parseLong(body.get("idProduit").toString()),
                    qte,
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString())
            );

            result.put("success", true);
            result.put("id", id);
            result.put("message", "Ligne insérée sur Site2");

        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }


    @PostMapping("/commandes")
    public Map<String, Object> addCommande(@RequestBody Map<String, Object> body) {

        Map<String, Object> res = new HashMap<>();

        try {
            Long id = jdbcTemplate.queryForObject(
                    "SELECT eshop.seq_commande.NEXTVAL FROM dual",
                    Long.class
            );

            String sql = """
        INSERT INTO COMMANDES
        (IDCOMMANDE, IDCLIENT, IDEMPLOYE, DATECOMMANDE, DATELIVRAISON)
        VALUES (?, ?, ?, SYSDATE, SYSDATE + 5)
        """;

            jdbcTemplate.update(sql,
                    id,
                    body.get("idClient"),
                    body.get("idEmploye")
            );

            res.put("success", true);
            res.put("id", id);

        } catch (Exception e) {
            res.put("success", false);
            res.put("message", e.getMessage());
        }

        return res;
    }
}