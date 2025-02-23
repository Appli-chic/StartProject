INSERT INTO users (id, name, age, created_at, updated_at) VALUES
(1, 'John Doe', 25, '2024-02-15 10:30:00', '2024-03-11 15:45:00'),
(2, 'Jane Smith', 30, '2024-02-20 08:15:00', NULL),
(3, 'Bob Johnson', 45, '2024-03-16 12:00:00', '2024-03-14 09:20:00');

SELECT setval(pg_get_serial_sequence('users', 'id'), (SELECT MAX(id) FROM users));
