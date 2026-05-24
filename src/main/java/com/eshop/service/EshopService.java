package com.eshop.service;

import com.eshop.entity.LigneCommande;
import com.eshop.repository.LigneCommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EshopService {

    private final LigneCommandeRepository repo;

    public Map<String, Long> getCompteurs() {
        Map<String, Long> map = new HashMap<>();
        map.put("global", repo.countGlobal());
        map.put("site1", repo.countSite1());
        map.put("site2", repo.countSite2());
        return map;
    }

    public List<LigneCommande> getAllLignes() {
        return repo.findAll();
    }

    public LigneCommande saveLigne(LigneCommande ligne) {
        return repo.save(ligne);
    }

    public void deleteLigne(Long id) {
        repo.deleteById(id);
    }

    public List<Map<String, Object>> getCaParCategorie() {
        List<Object[]> rows = repo.caParCategorie();
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> m = new HashMap<>();
            m.put("categorie", row[0]);
            m.put("ca", row[1]);
            result.add(m);
        }
        return result;
    }
    private List<Map<String, Object>> rowsToList(List<Object[]> rows, String... keys) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object[] row : rows) {
            Map<String, Object> m = new LinkedHashMap<>();
            for (int i = 0; i < keys.length && i < row.length; i++) {
                m.put(keys[i], row[i]);
            }
            result.add(m);
        }
        return result;
    }

    public List<Map<String, Object>> getLignesGlobal() {
        return rowsToList(repo.findAllGlobal(),
                "idLigneCommande","idCommande","idProduit","quantite","prixUnitaire","remise");
    }

    public List<Map<String, Object>> getLignesSite1() {
        return rowsToList(repo.findAllSite1(),
                "idLigneCommande","idCommande","idProduit","quantite","prixUnitaire","remise");
    }

    public List<Map<String, Object>> getLignesSite2() {
        return rowsToList(repo.findAllSite2(),
                "idLigneCommande","idCommande","idProduit","quantite","prixUnitaire","remise");
    }

    public List<Map<String, Object>> getClients() {
        return rowsToList(repo.findAllClients(),
                "idClient","societe","codeClient","ville");
    }

    public List<Map<String, Object>> getProduits() {
        return rowsToList(repo.findAllProduits(),
                "idProduit","designation","prixUnitaire","idCategorie");
    }

    public List<Map<String, Object>> getCommandes() {
        return rowsToList(repo.findAllCommandes(),
                "idCommande","idClient","idEmploye","dateCommande");
    }
}