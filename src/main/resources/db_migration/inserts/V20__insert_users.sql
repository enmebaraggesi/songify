INSERT INTO users (email, password, authorities, enabled)
VALUES
    ('bartek', '$2a$10$jytkSF01F5F4qNEXuhED8O5.CTlJQJqhszpffdeRPqLoVgFBlt9TG', '{ROLE_ADMIN, ROLE_USER}', true),
    ('john', '12345', '{ROLE_USER}', true);