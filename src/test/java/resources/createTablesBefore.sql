DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS files;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS User (
                                    id INT AUTO_INCREMENT PRIMARY KEY,
                                    mail VARCHAR(255) NOT NULL,
    name VARCHAR(255),
    number_group VARCHAR(50),
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS user_roles (
                                          user_id INT NOT NULL,
                                          roles VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, roles),
    FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE
    );

