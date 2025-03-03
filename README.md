# Gestion de Produits - Guide d'utilisation

## Présentation de l'application

L'application **Gestion de Produits** est une solution web développée avec Spring Boot permettant de gérer facilement un inventaire de produits. Cette application offre une interface utilisateur ajouter, modifier, rechercher et supprimer des produits dans votre base de données PostgreSQL ou un hashMap (pour des données temporaires).

## Fonctionnalités principales

### 1. Gestion des produits

- **Ajouter** de nouveaux produits avec référence unique, désignation, prix et quantité
- **Modifier** les informations des produits existants
- **Supprimer** des produits
- **Rechercher** des produits par mot-clé
- **Consulter** la liste complète des produits

## Installation et configuration (pour la base de donnée)

1. Assurez-vous que PostgreSQL est installé et en cours d'exécution
2. Créez une base de données nommée `gestion_produits`
3. Configurez les identifiants de connexion dans `application.properties` :

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/gestion_produits
   spring.datasource.username=postgres
   spring.datasource.password=postgres

   ```
   
4. Accédez à l'application via votre navigateur à l'adresse : http://localhost:8080/products

## Règles de validation

Pour garantir l'intégrité des données, l'application impose certaines règles :

- La **référence** du produit doit :

  - Être unique dans la base de données
  - Ne pas être vide

- La **désignation** du produit doit :

  - Ne pas être vide
  - Être unique dans la base de données

- Le **prix** du produit doit :
  - Être supérieur ou égal à 100 F CFA

## Architecture technique

Cette application est développée avec :

- **Backend** : Spring Boot (Java)
- **Frontend** : Thymeleaf et Tailwind CSS (via leur cdn)
- **Base de données** : PostgreSQL ou HashMap
