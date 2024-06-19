INSERT INTO Login (id, username, firstName, lastName, password, dateOfBirth, role)
VALUES (1001, 'john.doe', 'John', 'Doe', '$2a$10$I8TrCWJrPb7AxldXP4528e7ZvuOFhFqge9A3sx79PdKvgIekmkQaq', '1980-01-01 00:00:00', 'user'), -- strong-password
       (1002, 'jane.smith', 'Jane', 'Smith', '$2a$10$Hho1k.8IULBi8hrzj1NpEuNMKqOS6ZDXetdp/O4ZbL9dNZoxj3lWa', '1985-07-14 00:00:00', 'user'), -- you-dont-guess-me
       (1002, 'jimmy.allen', 'Jimmy', 'Allen', '$2a$10$kLuUFZC0fnLHK7XuwWc/k.eDC769uEggOpSVImB3DTGSofa7Ux5KO', '1990-04-27 00:00:00', 'admin'); -- secure-secret

INSERT INTO Account (iban, balance, login_id)
VALUES ('CH9300762011623852957', 1234.56, 1001),
       ('CH5604835012345678009', 2345.67, 1001),
       ('CH1204835012345678009', 3456.78, 1002);

INSERT INTO Transaction (id, transactionId, description, fromIban, toIban, amount)
VALUES (1001, 'b1d025e0-77c8-422b-80a4-d59e4ef3e121', 'Payment for invoice #1234', 'CH9300762011623852957', 'CH1204835012345678009', 250.75),
       (1002, '2782f1c9-3e2d-4a1b-a00c-d1f23b4f12ab', 'Salary for May', 'CH5604835012345678009', 'CH9300762011623852957', 3500.00),
       (1003, '1e4f12a3-1e1e-4211-84b9-278e21cf432b', 'Refund for order #5678', 'CH1204835012345678009', 'CH9300762011623852957', 45.00),
       (1004, '08b17a21-1f92-4412-a987-12b78e2f34aa', 'Payment for subscription', 'CH9300762011623852957', 'CH5604835012345678009', 15.99),
       (1005, 'f23e1b42-9c12-4e0b-b872-a1c34df12e01', 'Transfer to savings', 'CH5604835012345678009', 'CH5604835012345678009', 1000.00),
       (1006, 'a47c211e-b8f1-4123-9210-b34e12f3a09b', 'Gift to friend', 'CH1204835012345678009', 'CH5604835012345678009', 150.00);

INSERT INTO SupportContactInformation (id, email, phone)
VALUES (1001, 'user1@example.com', '555-0101'),
       (1002, 'user2@example.com', '555-0102'),
       (1003, 'user3@example.com', '555-0103'),
       (1004, 'user4@example.com', '555-0104'),
       (1005, 'user5@example.com', '555-0105');
