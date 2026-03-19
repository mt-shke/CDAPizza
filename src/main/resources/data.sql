-- Users
INSERT INTO user (username, password, email, role) VALUES
                                                       ('client',  '$2a$10$nIrt2s7AvOSgKQrRvJcQau.lD0kL.886FNRh23K3OUgxvaRL2JSzW', 'client@email.com',  'client'),
                                                       ('caisse',  '$2a$10$s8dVMwv3uKq/ySwonm2IIeBGh2QBvERG5UmIxE9dS4U/QjwKCQ.Nm', 'caisse@email.com',  'caisse'),
                                                       ('cuisine', '$2a$10$d07nMNs6BdtHqvxkqzCSquixM1.AwFkW2DurckQEShX534fERkomC', 'cuisine@email.com', 'cuisine'),
                                                       ('Michel',  '$2a$10$nIrt2s7AvOSgKQrRvJcQau.lD0kL.886FNRh23K3OUgxvaRL2JSzW', 'michel@email.com',  'client'),
                                                       ('Sophie',  '$2a$10$nIrt2s7AvOSgKQrRvJcQau.lD0kL.886FNRh23K3OUgxvaRL2JSzW', 'sophie@email.com',  'client');

-- Pizzas
INSERT INTO pizza (nom, prix) VALUES
                                  ('Margherita',   8.50),
                                  ('4 Fromages',  10.00),
                                  ('Reine',        9.50),
                                  ('Pepperoni',   11.00),
                                  ('Végétarienne', 9.00),
                                  ('4 saisons',    9.50),
                                  ('Napolitaine', 10.50),
                                  ('Romaine',     10.00),
                                  ('Savoyarde',   11.00),
                                  ('Landaise',     9.50);

-- Commandes
-- client (id=1) : 3 commandes dont 1 avec >5 pizzas
-- Michel (id=4) : 3 commandes dont 1 avec >5 pizzas ET remise fidélité cumulée
-- Sophie (id=5) : 3 commandes dont 1 avec >5 pizzas

INSERT INTO commande (date, montant, etat, Id_user) VALUES
-- client
('2026-03-10 09:00:00', 18.00,  'LIVRER',      1),  -- commande 1 : normal
('2026-03-12 11:00:00', 55.58,  'LIVRER',      1),  -- commande 2 : >5 pizzas -5%
('2026-03-17 09:15:00', 17.55,  'PREPARATION', 1),  -- commande 3 : 3ème -10%
-- Michel
('2026-03-11 10:00:00', 20.00,  'LIVRER',      4),  -- commande 4 : normal
('2026-03-14 12:30:00', 52.68,  'LIVRER',      4),  -- commande 5 : >5 pizzas -5%
('2026-03-17 10:30:00', 30.83,  'PRETE',       4),  -- commande 6 : 3ème -10% ET >5 pizzas -5% = -15%
-- Sophie
('2026-03-10 14:00:00', 9.50,   'LIVRER',      5),  -- commande 7 : normal
('2026-03-13 16:00:00', 57.00,  'LIVRER',      5),  -- commande 8 : >5 pizzas -5%
('2026-03-17 11:45:00', 17.55,  'PAYER',       5);  -- commande 9 : 3ème -10%

-- Commande 1 (client) : Margherita x1 + Reine x1 = 18.00 ✓
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (1, 1, 1),
                                                           (1, 3, 1);

-- Commande 2 (client) : 6 pizzas → -5%
-- Margherita x2 + Pepperoni x2 + Reine x1 + Végétarienne x1 = 17+22+9.50+9 = 57.50 * 0.95 = 54.63
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (2, 1, 2),
                                                           (2, 4, 2),
                                                           (2, 3, 1),
                                                           (2, 5, 1);

-- Commande 3 (client) : 3ème commande → -10%
-- Pepperoni x1 + Reine x1 = 19.50 * 0.90 = 17.55 ✓
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (3, 4, 1),
                                                           (3, 3, 1);

-- Commande 4 (Michel) : 4 Fromages x2 = 20.00 ✓
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
    (4, 2, 2);

-- Commande 5 (Michel) : 7 pizzas → -5%
-- Margherita x2 + 4 Fromages x2 + Reine x1 + Napolitaine x1 + Savoyarde x1 = 17+20+9.50+10.50+11 = 68 * 0.95 = 64.60
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (5, 1, 2),
                                                           (5, 2, 2),
                                                           (5, 3, 1),
                                                           (5, 7, 1),
                                                           (5, 9, 1);

-- Commande 6 (Michel) : 3ème commande ET >5 pizzas → -15%
-- Pepperoni x2 + Reine x2 + Végétarienne x2 = 22+19+18 = 59 * 0.85 = 50.15
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (6, 4, 2),
                                                           (6, 3, 2),
                                                           (6, 5, 2);

-- Commande 7 (Sophie) : Reine x1 = 9.50 ✓
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
    (7, 3, 1);

-- Commande 8 (Sophie) : 6 pizzas → -5%
-- Margherita x2 + Pepperoni x2 + 4 saisons x1 + Landaise x1 = 17+22+9.50+9.50 = 58 * 0.95 = 55.10
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (8, 1, 2),
                                                           (8, 4, 2),
                                                           (8, 6, 1),
                                                           (8, 10, 1);

-- Commande 9 (Sophie) : 3ème commande → -10%
-- Margherita x1 + Reine x1 = 19.50 * 0.90 = 17.55 ✓
INSERT INTO contenir (Id_commande, Id_pizza, quantite) VALUES
                                                           (9, 1, 1),
                                                           (9, 3, 1);