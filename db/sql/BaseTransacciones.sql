
CREATE TABLE IF NOT EXISTS account (
    id_account SERIAL PRIMARY KEY,
    account_number VARCHAR(20) NOT NULL UNIQUE,
    account_type VARCHAR(255) NOT NULL,
    init_balance DECIMAL(10, 2) NOT NULL,
    status BOOLEAN NOT NULL,
    id_client BIGINT
    );


CREATE TABLE IF NOT EXISTS movement (
    id_movement SERIAL PRIMARY KEY,
    date_movement TIMESTAMP NOT NULL,
    movement_type VARCHAR(50) NOT NULL,
    balance DECIMAL(10, 2) NOT NULL,
    movement DECIMAL(10, 2) NOT NULL,
    id_account BIGINT NOT NULL,
    CONSTRAINT fk_account FOREIGN KEY (id_account)
    REFERENCES account(id_account) ON DELETE CASCADE
    );

INSERT INTO account (account_number, account_type, init_balance, status, id_client)
VALUES
    ('478758', 'AHORRO', 2000.00, TRUE, 1),
    ('225487', 'CORRIENTE', 100.00, TRUE, 2),
    ('495878', 'AHORRO', 0.00, TRUE, 3),
    ('496825', 'AHORRO', 540.00, TRUE, 2),
    ('585545', 'CORRIENTE', 1000.00, TRUE, 1);

INSERT INTO movement (date_movement, movement_type, balance, movement, id_account)
VALUES
    ('2024-10-14 10:00:00', 'RETIRO', 2000.00, 575.00, 1),
    ('2024-10-15 11:30:00', 'DEPOSITO', 100.00, 600.00, 2),
    ('2024-10-16 09:15:00', 'DEPOSITO', 0.00, 150.00, 3),
    ('2024-10-17 08:45:00', 'RETIRO', 540.00, 540.00, 4);
