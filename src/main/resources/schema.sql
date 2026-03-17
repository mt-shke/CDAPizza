DROP TABLE IF EXISTS commande;
DROP TABLE IF EXISTS pizza;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS contenir;


CREATE TABLE IF NOT EXISTS user(
    Id_user    INT AUTO_INCREMENT PRIMARY KEY,
    pseudo     VARCHAR(50) NOT NULL,
    password   VARCHAR(50) NOT NULL,
    email      VARCHAR(50) NOT NULL,
    role       VARCHAR(50) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS pizza(
                                        Id_pizza   INT AUTO_INCREMENT PRIMARY KEY,
                                        nom        VARCHAR(50) NOT NULL,
    prix       DECIMAL(15,2) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS commande(
                                    Id_commande INT AUTO_INCREMENT PRIMARY KEY,
                                    date       DATETIME NOT NULL DEFAULT NOW(),
    montant     DECIMAL(15,2) NOT NULL,
    etat        VARCHAR(50) NOT NULL DEFAULT 'PAYER',
    Id_user     INT NOT NULL,
    FOREIGN KEY (Id_user) REFERENCES user(Id_user)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS contenir(
                                       Id_commande INT NOT NULL,
                                       Id_pizza    INT NOT NULL,
                                       quantite    INT NOT NULL DEFAULT 1,
                                       PRIMARY KEY (Id_commande, Id_pizza),
    FOREIGN KEY (Id_commande) REFERENCES commande(Id_commande) ON DELETE CASCADE,
    FOREIGN KEY (Id_pizza)    REFERENCES pizza(Id_pizza)
    ) ENGINE=InnoDB;