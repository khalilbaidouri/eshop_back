# EShop Multibase — Backend

> API REST du système de base de données distribuée EShop.  
> Built with **Spring Boot 3.2** · **Oracle JDBC** · **JPA**

---

## Prérequis

- Java JDK 17+
- Maven 3.8+ (ou utiliser le wrapper `mvnw` inclus)
- Les **conteneurs Oracle** doivent être démarrés sur les ports 1524, 1522, 1523

---

## Installation

```bash
git clone https://github.com/khalilbaidouri/eshop_back.git
cd eshop_back
```

---

## Configuration

Modifier `src/main/resources/application.properties` si nécessaire :

```properties
spring.application.name=eshop-app
server.port=8080

# Connexion Oracle Global
spring.datasource.url=jdbc:oracle:thin:@localhost:1524/XEPDB1
spring.datasource.username=eshop
spring.datasource.password=Eshop123
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver

spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
spring.jpa.show-sql=true

# CORS pour Next.js
spring.mvc.cors.allowed-origins=http://localhost:3000
```

---

## Démarrage

```bash
# Linux / Mac
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

Le backend démarre sur **http://localhost:8080**

---

## Test rapide

```bash
curl http://localhost:8080/api/compteurs
# Résultat attendu : {"global":8,"site1":4,"site2":4}
```

---

## Endpoints API

### Compteurs et affichage

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/compteurs` | Compteurs LigneCommandes des 3 sites |
| GET | `/api/global` | LigneCommandes Site Global |
| GET | `/api/site1` | LigneCommandes Site1 via DB Link |
| GET | `/api/site2` | LigneCommandes Site2 via DB Link |
| GET | `/api/clients` | Liste des clients |
| GET | `/api/produits` | Catalogue produits |
| GET | `/api/commandes` | Liste des commandes |
| GET | `/api/stats/ca` | CA distribué par catégorie 2026 |

### Listes déroulantes (pour les formulaires)

| Méthode | URL | Description |
|---|---|---|
| GET | `/api/global/commandes-list` | Commandes pour sélection |
| GET | `/api/global/produits-list` | Produits pour sélection |
| GET | `/api/site1/commandes` | Commandes Site1 |
| GET | `/api/site1/produits` | Produits Site1 |
| GET | `/api/site2/commandes` | Commandes Site2 |
| GET | `/api/site2/produits` | Produits Site2 |

### INSERT

| Méthode | URL | Description |
|---|---|---|
| POST | `/api/global/lignes` | INSERT dans Global → trigger auto-route vers Site1 ou Site2 |
| POST | `/api/site1/lignes` | INSERT direct Site1 (Quantite ≥ 100) → propagé vers Global |
| POST | `/api/site2/lignes` | INSERT direct Site2 (Quantite < 100) → propagé vers Global |

Corps de la requête (JSON) :
```json
{
  "idCommande": 1,
  "idProduit": 1,
  "quantite": 150,
  "prixUnitaire": 899.99,
  "remise": 10
}
```

### UPDATE

| Méthode | URL | Description |
|---|---|---|
| PUT | `/api/global/lignes/{id}` | UPDATE Global → migration automatique si seuil changé |
| PUT | `/api/site1/lignes/{id}` | UPDATE Site1 → propagé vers Global |
| PUT | `/api/site2/lignes/{id}` | UPDATE Site2 → propagé vers Global |

Corps de la requête (JSON) :
```json
{
  "idProduit": 1,
  "quantite": 200,
  "prixUnitaire": 899.99,
  "remise": 5
}
```

### DELETE

| Méthode | URL | Description |
|---|---|---|
| DELETE | `/api/global/lignes/{id}` | DELETE Global → propagé vers le bon site |
| DELETE | `/api/site1/lignes/{id}` | DELETE Site1 → propagé vers Global |
| DELETE | `/api/site2/lignes/{id}` | DELETE Site2 → propagé vers Global |

---

## Structure du projet

```
src/main/java/com/eshop/
├── controller/
│   └── EshopController.java     ← Tous les endpoints REST
├── service/
│   └── EshopService.java        ← Logique métier
├── entity/
│   └── LigneCommande.java       ← Entité JPA
├── repository/
│   └── LigneCommandeRepository.java  ← Requêtes natives Oracle
└── config/
    └── CorsConfig.java          ← Configuration CORS
```

---

## Règles métier

| Site | Contrainte | Comportement |
|---|---|---|
| Site1 | Quantite ≥ 100 | Refus si Quantite < 100 avec message d'erreur |
| Site2 | Quantite < 100 | Refus si Quantite ≥ 100 avec message d'erreur |
| Global | Aucune | Trigger Oracle route automatiquement vers le bon site |

---

## Séquences Oracle utilisées

```java
// ID auto-généré via séquence Oracle
"SELECT ESHOP.SEQ_LIGNE.NEXTVAL FROM dual"
"SELECT ESHOP.SEQ_COMMANDE.NEXTVAL FROM dual"
```

---

## Technologies

| Dépendance | Version | Rôle |
|---|---|---|
| Spring Boot | 3.2.x | Framework principal |
| Spring Data JPA | — | ORM pour entités |
| Spring Web | — | API REST |
| Oracle JDBC | 21.x | Connexion Oracle |
| JdbcTemplate | — | Requêtes natives (DB Links, séquences) |
| Lombok | — | Réduction du code boilerplate |

---

## Dépôts liés

- **Frontend** : https://github.com/khalilbaidouri/eshop_front.git
- **Base de données** : https://github.com/khalilbaidouri/Bases-de-donn-es-distribu-es.git
