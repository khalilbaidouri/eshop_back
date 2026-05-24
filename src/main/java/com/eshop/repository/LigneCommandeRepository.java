package com.eshop.repository;

import com.eshop.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    @Query(value = "SELECT COUNT(*) FROM ESHOP.LIGNECOMMANDES", nativeQuery = true)
    Long countGlobal();

    @Query(value = "SELECT COUNT(*) FROM ESHOP1.LIGNECOMMANDES1@LINK_SITE1", nativeQuery = true)
    Long countSite1();

    @Query(value = "SELECT COUNT(*) FROM ESHOP2.LIGNECOMMANDES2@LINK_SITE2", nativeQuery = true)
    Long countSite2();

    @Query(value = """
        SELECT cat.DESIGNATION, SUM(ca) AS ca_total FROM ESHOP.CATEGORIES cat
        JOIN (
          SELECT p.IDCATEGORIE, SUM(lc.QUANTITE * lc.PRIXUNITAIRE) ca
          FROM ESHOP1.LIGNECOMMANDES1@LINK_SITE1 lc
          JOIN ESHOP1.PRODUITS1@LINK_SITE1 p ON lc.IDPRODUIT = p.IDPRODUIT
          GROUP BY p.IDCATEGORIE
          UNION ALL
          SELECT p.IDCATEGORIE, SUM(lc.QUANTITE * lc.PRIXUNITAIRE) ca
          FROM ESHOP2.LIGNECOMMANDES2@LINK_SITE2 lc
          JOIN ESHOP2.PRODUITS2@LINK_SITE2 p ON lc.IDPRODUIT = p.IDPRODUIT
          GROUP BY p.IDCATEGORIE
        ) x ON cat.IDCATEGORIE = x.IDCATEGORIE
        GROUP BY cat.DESIGNATION ORDER BY ca_total DESC
        """, nativeQuery = true)
    List<Object[]> caParCategorie();
    @Query(value = "SELECT lc.IDLIGNECOMMANDE, lc.IDCOMMANDE, lc.IDPRODUIT, lc.QUANTITE, lc.PRIXUNITAIRE, lc.REMISE FROM ESHOP.LIGNECOMMANDES lc ORDER BY lc.IDLIGNECOMMANDE", nativeQuery = true)
    List<Object[]> findAllGlobal();

    @Query(value = "SELECT lc.IDLIGNECOMMANDE, lc.IDCOMMANDE, lc.IDPRODUIT, lc.QUANTITE, lc.PRIXUNITAIRE, lc.REMISE FROM ESHOP1.LIGNECOMMANDES1@LINK_SITE1 lc ORDER BY lc.IDLIGNECOMMANDE", nativeQuery = true)
    List<Object[]> findAllSite1();

    @Query(value = "SELECT lc.IDLIGNECOMMANDE, lc.IDCOMMANDE, lc.IDPRODUIT, lc.QUANTITE, lc.PRIXUNITAIRE, lc.REMISE FROM ESHOP2.LIGNECOMMANDES2@LINK_SITE2 lc ORDER BY lc.IDLIGNECOMMANDE", nativeQuery = true)
    List<Object[]> findAllSite2();

    @Query(value = "SELECT c.IDCLIENT, c.SOCIETE, c.CODECLIENT, c.VILLE FROM ESHOP.CLIENTS c ORDER BY c.IDCLIENT", nativeQuery = true)
    List<Object[]> findAllClients();

    @Query(value = "SELECT p.IDPRODUIT, p.DESIGNATION, p.PRIXUNITAIRE, p.IDCATEGORIE FROM ESHOP.PRODUITS p ORDER BY p.IDPRODUIT", nativeQuery = true)
    List<Object[]> findAllProduits();

    @Query(value = "SELECT cmd.IDCOMMANDE, cmd.IDCLIENT, cmd.IDEMPLOYE, cmd.DATECOMMANDE FROM ESHOP.COMMANDES cmd ORDER BY cmd.IDCOMMANDE", nativeQuery = true)
    List<Object[]> findAllCommandes();
}