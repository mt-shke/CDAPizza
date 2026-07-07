# CDAPizza API avec Spring Boot

API REST faite avec Spring Boot, MySQL et Docker Compose.

## Technos

- Java 25 / Spring Boot
- Spring Data JPA + Hibernate 7
- MySQL 8 (via Docker)
- phpMyAdmin (via Docker)
- Maven (wrapper inclus)

## Prérequis


- [JDK 21+](https://adoptium.net) installé et `JAVA_HOME` configuré
- [Docker Desktop](https://www.docker.com/products/docker-desktop/) installé
- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (recommandé)

## Lancer le projet

Lancer : `docker compose up -d`


### Infos

CDAPizza est une application web interne développée pour une pizzeria dans le cadre d'un POC CDAP. 
Elle digitalise la gestion des commandes en temps réel entre le guichet et la cuisine.

L'architecture repose sur une API REST Java Spring Boot côté back-end et une SPA React + Tailwind CSS côté front-end, avec une base de données MySQL. 
La sécurité est assurée par JWT stocké en cookie HttpOnly, BCrypt pour les mots de passe, et Spring Security pour les autorisations.

L'application gère trois rôles distincts : le client peut s'inscrire, passer des commandes et suivre leur état ; la caisse visualise toutes les commandes et fait évoluer leur statut de PAYER à LIVRER ; la cuisine fait de même et gère en plus le catalogue de pizzas.
Deux règles métier automatiques sont implémentées : une remise de 10% toutes les 3 commandes pour fidéliser les clients, et une remise de 5% dès 5 pizzas commandées, cumulables jusqu'à −15%. Les commandes de la cuisine se rafraîchissent automatiquement toutes les 5 secondes via polling.

