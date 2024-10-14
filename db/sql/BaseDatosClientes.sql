CREATE TABLE IF NOT EXISTS client (
    client_id SERIAL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    status BOOLEAN NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender VARCHAR(50),
    age INTEGER NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone_number VARCHAR(25),
    identification VARCHAR(25) NOT NULL
);

INSERT INTO client (password, status, name, gender, age, address, phone_number, identification)
VALUES
    ('1234', TRUE, 'Jose Lema', 'MALE', 30, 'Otavalo sn y principal', '098254785', '1206015505'),
    ('5678', TRUE, 'Marielena Montalvo', 'FEMALE', 28, 'Amazonas y NNUU', '097548965', '1206015503'),
    ('1245', TRUE, 'Juan Osorio', 'MALE', 35, '13 de Junio y Equinoccial', '098874587', '1206015501');
ON CONFLICT (client_id) DO NOTHING;

