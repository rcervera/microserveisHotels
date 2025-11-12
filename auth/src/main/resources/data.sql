-- Esborrem el contingut anterior
DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM roles;

-- Rols
INSERT INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO roles (id, name) VALUES (2, 'ROLE_GUEST');

-- Usuaris (recorda: les contrasenyes han d'estar codificades amb bcrypt)
INSERT INTO users (id, username, password, email)
VALUES (1, 'admin', '$2a$10$PjS4CgecBxzzktS6QO6m5OIXpZIT3CkCaD7PbRtYxUn8F2e9jvToi', 'admin@example.com'),
       (2, 'user', '$2a$10$2xDh5xQqg8mNIR1HYPK6zO7XUu2AoSTtCw8M8rA8rY9df5X7eZyA2', 'user@example.com');
-- contrasenyes originals: admin / user

-- Relacions
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (2, 2);
