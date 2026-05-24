package com.eshop.controller;

import com.eshop.entity.Client;
import com.eshop.entity.LigneCommande;
import com.eshop.service.ClientService;
import com.eshop.service.EshopService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class EshopController {

    @Autowired
    private final ClientService clientService;
    private final JdbcTemplate jdbcTemplate;
    private final EshopService service;

    // ─── Compteurs ───────────────────────────────────────────
    @GetMapping("/compteurs")
    public Map<String, Long> compteurs() {
        return service.getCompteurs();
    }

    // ─── LigneCommandes CRUD global ──────────────────────────
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

    // ─── Stats CA ─────────────────────────────────────────────
    @GetMapping("/stats/ca")
    public List<Map<String, Object>> ca() {
        return service.getCaParCategorie();
    }

    // ─── Affichage des 3 sites ────────────────────────────────
    @GetMapping("/global")
    public List<Map<String, Object>> global() {
        return service.getLignesGlobal();
    }

    @GetMapping("/site1")
    public List<Map<String, Object>> site1() {
        return service.getLignesSite1();
    }

    @GetMapping("/site2")
    public List<Map<String, Object>> site2() {
        return service.getLignesSite2();
    }
    //add
    @PostMapping("/clients/add")
    public Client create(@RequestBody Client client) {
        return clientService.save(client);
    }

    // ─── Listes pour affichage global ────────────────────────
    @GetMapping("/clients")
    public List<Map<String, Object>> clients() {

        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDCLIENT, CODECLIENT, SOCIETE, CONTACT, ADRESSE, VILLE, PAYS, CODEPOSTAL, TELEPHONE FROM ESHOP.CLIENTS ORDER BY IDCLIENT",
                (rs, i) -> new Object[]{
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9)
                }
        );

        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idClient", r[0]);
            m.put("codeClient", r[1]);
            m.put("societe", r[2]);
            m.put("contact", r[3]);
            m.put("adresse", r[4]);
            m.put("ville", r[5]);
            m.put("pays", r[6]);
            m.put("codePostal", r[7]);
            m.put("telephone", r[8]);
            return m;
        }).toList();
    }

    @GetMapping("/produits")
    public List<Map<String, Object>> produits() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDPRODUIT, DESIGNATION, PRIXUNITAIRE, IDCATEGORIE FROM ESHOP.PRODUITS ORDER BY IDPRODUIT",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getString(2), rs.getDouble(3), rs.getLong(4)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idProduit", r[0]);
            m.put("designation", r[1]);
            m.put("prixUnitaire", r[2]);
            m.put("idCategorie", r[3]);
            return m;
        }).toList();
    }

    @GetMapping("/commandes")
    public List<Map<String, Object>> commandes() {
        return service.getCommandes();
    }

    // ─── Listes déroulantes Global ───────────────────────────
    @GetMapping("/global/commandes-list")
    public List<Map<String, Object>> commandesGlobalList() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDCOMMANDE, IDCLIENT, TO_CHAR(DATECOMMANDE,'YYYY-MM-DD') FROM ESHOP.COMMANDES ORDER BY IDCOMMANDE",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getLong(2), rs.getString(3)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idCommande", r[0]);
            m.put("idClient", r[1]);
            m.put("date", r[2]);
            return m;
        }).toList();
    }

    @GetMapping("/global/produits-list")
    public List<Map<String, Object>> produitsGlobalList() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDPRODUIT, DESIGNATION, PRIXUNITAIRE FROM ESHOP.PRODUITS ORDER BY IDPRODUIT",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getString(2), rs.getDouble(3)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idProduit", r[0]);
            m.put("designation", r[1]);
            m.put("prix", r[2]);
            return m;
        }).toList();
    }

    // ─── Listes déroulantes Site1 ────────────────────────────
    @GetMapping("/site1/commandes")
    public List<Map<String, Object>> commandesSite1() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDCOMMANDE, IDCLIENT, TO_CHAR(DATECOMMANDE,'YYYY-MM-DD') FROM ESHOP1.COMMANDES1@LINK_SITE1 ORDER BY IDCOMMANDE",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getLong(2), rs.getString(3)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idCommande", r[0]);
            m.put("idClient", r[1]);
            m.put("date", r[2]);
            return m;
        }).toList();
    }

    @GetMapping("/site1/produits")
    public List<Map<String, Object>> produitsSite1() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDPRODUIT, DESIGNATION, PRIXUNITAIRE FROM ESHOP1.PRODUITS1@LINK_SITE1 ORDER BY IDPRODUIT",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getString(2), rs.getDouble(3)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idProduit", r[0]);
            m.put("designation", r[1]);
            m.put("prix", r[2]);
            return m;
        }).toList();
    }

    // ─── Listes déroulantes Site2 ────────────────────────────
    @GetMapping("/site2/commandes")
    public List<Map<String, Object>> commandesSite2() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDCOMMANDE, IDCLIENT, TO_CHAR(DATECOMMANDE,'YYYY-MM-DD') FROM ESHOP2.COMMANDES2@LINK_SITE2 ORDER BY IDCOMMANDE",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getLong(2), rs.getString(3)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idCommande", r[0]);
            m.put("idClient", r[1]);
            m.put("date", r[2]);
            return m;
        }).toList();
    }

    @GetMapping("/site2/produits")
    public List<Map<String, Object>> produitsSite2() {
        List<Object[]> rows = jdbcTemplate.query(
                "SELECT IDPRODUIT, DESIGNATION, PRIXUNITAIRE FROM ESHOP2.PRODUITS2@LINK_SITE2 ORDER BY IDPRODUIT",
                (rs, i) -> new Object[]{rs.getLong(1), rs.getString(2), rs.getDouble(3)}
        );
        return rows.stream().map(r -> {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("idProduit", r[0]);
            m.put("designation", r[1]);
            m.put("prix", r[2]);
            return m;
        }).toList();
    }

    // ─── INSERT Global (trigger auto-route) ──────────────────
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
            result.put("message", "Ligne insérée → trigger routé vers " + (qte >= 100 ? "Site1" : "Site2"));
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ─── INSERT Site1 direct ──────────────────────────────────
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
                    "SELECT ESHOP.SEQ_LIGNE.NEXTVAL FROM dual", Long.class
            );
            jdbcTemplate.update("""
                INSERT INTO ESHOP1.LIGNECOMMANDES1@LINK_SITE1
                (IDLIGNECOMMANDE, IDCOMMANDE, IDPRODUIT, QUANTITE, PRIXUNITAIRE, REMISE)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                    id,
                    Long.parseLong(body.get("idCommande").toString()),
                    Long.parseLong(body.get("idProduit").toString()),
                    qte,
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString())
            );
            result.put("success", true);
            result.put("id", id);
            result.put("message", "Ligne #" + id + " insérée sur Site1 → propagée vers Global");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ─── INSERT Site2 direct ──────────────────────────────────
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
                    "SELECT ESHOP.SEQ_LIGNE.NEXTVAL FROM dual", Long.class
            );
            jdbcTemplate.update("""
                INSERT INTO ESHOP2.LIGNECOMMANDES2@LINK_SITE2
                (IDLIGNECOMMANDE, IDCOMMANDE, IDPRODUIT, QUANTITE, PRIXUNITAIRE, REMISE)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                    id,
                    Long.parseLong(body.get("idCommande").toString()),
                    Long.parseLong(body.get("idProduit").toString()),
                    qte,
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString())
            );
            result.put("success", true);
            result.put("id", id);
            result.put("message", "Ligne #" + id + " insérée sur Site2 → propagée vers Global");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ─── UPDATE Site1 ─────────────────────────────────────────
    @PutMapping("/site1/lignes/{id}")
    public Map<String, Object> updateSite1(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            int rows = jdbcTemplate.update("""
                UPDATE ESHOP1.LIGNECOMMANDES1@LINK_SITE1
                SET IDPRODUIT = ?, QUANTITE = ?, PRIXUNITAIRE = ?, REMISE = ?
                WHERE IDLIGNECOMMANDE = ?
                """,
                    Long.parseLong(body.get("idProduit").toString()),
                    Integer.parseInt(body.get("quantite").toString()),
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString()),
                    id
            );
            result.put("success", rows > 0);
            result.put("message", rows > 0
                    ? "Ligne " + id + " modifiée sur Site1 → propagée vers Global"
                    : "Ligne introuvable sur Site1");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ─── UPDATE Site2 ─────────────────────────────────────────
    @PutMapping("/site2/lignes/{id}")
    public Map<String, Object> updateSite2(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Map<String, Object> result = new LinkedHashMap<>();
        try {
            int rows = jdbcTemplate.update("""
                UPDATE ESHOP2.LIGNECOMMANDES2@LINK_SITE2
                SET IDPRODUIT = ?, QUANTITE = ?, PRIXUNITAIRE = ?, REMISE = ?
                WHERE IDLIGNECOMMANDE = ?
                """,
                    Long.parseLong(body.get("idProduit").toString()),
                    Integer.parseInt(body.get("quantite").toString()),
                    Double.parseDouble(body.get("prixUnitaire").toString()),
                    Double.parseDouble(body.get("remise").toString()),
                    id
            );
            result.put("success", rows > 0);
            result.put("message", rows > 0
                    ? "Ligne " + id + " modifiée sur Site2 → propagée vers Global"
                    : "Ligne introuvable sur Site2");
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", e.getMessage());
        }
        return result;
    }

    // ─── Ajouter une commande ─────────────────────────────────
    @PostMapping("/commandes")
    public Map<String, Object> addCommande(@RequestBody Map<String, Object> body) {
        Map<String, Object> res = new HashMap<>();
        try {
            Long id = jdbcTemplate.queryForObject(
                    "SELECT eshop.seq_commande.NEXTVAL FROM dual", Long.class
            );
            jdbcTemplate.update("""
                INSERT INTO ESHOP.COMMANDES
                (IDCOMMANDE, IDCLIENT, IDEMPLOYE, DATECOMMANDE, DATELIVRAISON)
                VALUES (?, ?, ?, SYSDATE, SYSDATE + 5)
                """,
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