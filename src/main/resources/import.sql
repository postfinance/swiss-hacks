INSERT INTO Transaction (id, description, fromIban, toIban, amount)
VALUES (29837, 'Payment for invoice #1234', 'CH9300762011623852957', 'CH9300762011623852957', 250.75),
       (98632, 'Salary for May', 'CH5604835012345678009', 'CH9300762011623852957', 3500.00),
       (1805363, 'Refund for order #5678', 'CH9300762011623852957', 'CH1204835012345678009', 45.00),
       (2707554, 'Payment for subscription', 'CH9300762011623852957', 'CH5604835012345678009', 15.99),
       (2343, 'Transfer to savings', 'CH9300762011623852957', 'CH9300762011623852957', 1000.00),
       (15661697, 'Gift to friend', 'CH1204835012345678009', 'CH5604835012345678009', 150.00);

INSERT INTO SupportContactInformation (id, email, phone)
VALUES (1, 'user1@example.com', '555-0101'),
       (2, 'user2@example.com', '555-0102'),
       (3, 'user3@example.com', '555-0103'),
       (4, 'user4@example.com', '555-0104'),
       (5, 'user5@example.com', '555-0105');
