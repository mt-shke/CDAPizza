```

=== COMMANDES ===
GET    /api/commandes                        - Liste toutes les commandes
GET    /api/commandes/{id}                   - Récupère une commande par id
POST   /api/commandes                        - Crée une nouvelle commande
PUT    /api/commandes/{id}                   - Modifie une commande existante
DELETE /api/commandes/{id}                   - Supprime une commande

=== PIZZAS ===
GET    /api/pizzas                           - Liste toutes les pizzas
GET    /api/pizzas/{id}                      - Récupère une pizza par id
POST   /api/pizzas                           - Crée une nouvelle pizza
PUT    /api/pizzas/{id}                      - Modifie une pizza existante
DELETE /api/pizzas/{id}                      - Supprime une pizza

=== CONTENIR ===
GET    /api/contenir                                    - Liste toutes les lignes de commande
GET    /api/contenir/commande/{id_commande}             - Liste toutes les pizzas d'une commande
POST   /api/contenir                                    - Ajoute une pizza à une commande
PUT    /api/contenir/{idCommande}/{idPizza}             - Modifie la quantité d'une ligne
DELETE /api/contenir/{idCommande}/{idPizza}             - Supprime une ligne de commande

```
