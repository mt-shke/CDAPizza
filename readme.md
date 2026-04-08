```

CDAPizza est une application web interne développée pour une pizzeria dans le cadre d'un POC CDAP. 
Elle digitalise la gestion des commandes en temps réel entre le guichet et la cuisine.

L'architecture repose sur une API REST Java Spring Boot côté back-end et une SPA React + Tailwind CSS côté front-end, avec une base de données MySQL. 
La sécurité est assurée par JWT stocké en cookie HttpOnly, BCrypt pour les mots de passe, et Spring Security pour les autorisations.

L'application gère trois rôles distincts : le client peut s'inscrire, passer des commandes et suivre leur état ; la caisse visualise toutes les commandes et fait évoluer leur statut de PAYER à LIVRER ; la cuisine fait de même et gère en plus le catalogue de pizzas.
Deux règles métier automatiques sont implémentées : une remise de 10% toutes les 3 commandes pour fidéliser les clients, et une remise de 5% dès 5 pizzas commandées, cumulables jusqu'à −15%. Les commandes de la cuisine se rafraîchissent automatiquement toutes les 5 secondes via polling.

```
