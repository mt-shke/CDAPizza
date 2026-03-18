INSERT INTO user (username, password, email, role) VALUES
                                                      ('alice',  'alice123',  'alice@cdapizza.fr',  'caisse'),
                                                      ('bob',    'bob123',    'bob@cdapizza.fr',    'cuisine'),
                                                      ('carlos', 'carlos123', 'carlos@cdapizza.fr', 'caisse');

INSERT INTO pizza (nom, prix) VALUES
                                  ('Margherita',   8.50),
                                  ('4 Fromages',  10.00),
                                  ('Reine',        9.50),
                                  ('Pepperoni',   11.00),
                                  ('Végétarienne', 9.00);

INSERT INTO commande (date, montant, etat, Id_user) VALUES
                                                         ('2026-03-17 09:15:00', 19.50, 'LIVRER',      1),
                                                         ('2026-03-17 10:30:00', 20.00, 'PRETE',       3),
                                                         ('2026-03-17 11:00:00', 27.50, 'PREPARATION', 1),
                                                         ('2026-03-17 11:45:00', 18.50, 'PAYER',       3);

INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (1, 1, 1),  -- Margherita x1 = 8.50
                                                           (1, 3, 1);  -- Reine x1      = 9.50  => total : 19.50 ✓ (+ une Reine en double ?)

INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
    (2, 2, 2);  -- 4 Fromages x2 = 20.00 ✓

-- Commande 3 : 1 Pepperoni + 1 Végétarienne + 1 Reine
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (3, 4, 1),  -- Pepperoni x1   = 11.00
                                                           (3, 5, 1),  -- Végétarienne x1 = 9.00
                                                           (3, 3, 1);  -- Reine x1        = 9.50 => total : 29.50

-- Commande 4 : 2 x Margherita
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
    (4, 1, 2);  -- Margherita x2 = 17.00