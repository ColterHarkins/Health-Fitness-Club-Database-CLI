INSERT INTO Account (first_name, last_name, email, phone_number, role)
VALUES -- IDs are sequentially numbered from 1-6
    ('John', 'Doe', 'john.trainer@example.com', '111-111-1111', 'trainer'),
    ('Jane', 'Doe', 'jane.trainer@example.com', '222-222-2222', 'trainer'),

    ('Eddie', 'Vedder', 'eddie@example.com', '333-333-3333', 'member'),
    ('Stevie', 'Nicks', 'stevie@example.com', '444-444-4444', 'member'),
    ('Gord', 'Downie Jr.', 'gord@example.com', '555-555-5555', 'member'),

    ('Linus', 'Torvalds', 'linux@example.com', '777-777-7777', 'admin');

-- IDs are sequentially numbered from 1-5
INSERT INTO Room (title, capacity)
VALUES
    ('Yoga Room', 20),
    ('Gym', 100),
    ('Spin Room', 30),
    ('Private workout room 1', 5),
    ('Private workout room 2', 5);

-- IDs are sequentially numbered from 1-5
INSERT INTO Goal (account_id, goal_type, target_value, start_date, target_date, status)
VALUES
    (3, 'Barbell Squat (lb)', 405, '2025-01-01', '2025-03-01', 'active'),
    (3, 'Bench Press (lb)', 315, '2025-01-20', '2025-03-20', 'active'),
    (4, 'Weight (lb)', 125.0, '2025-02-01', '2025-06-01', 'active'),
    (4, 'Muscle Mass (lb)', 10, '2025-03-01', '2025-07-01', 'active'),
    (5, 'Running Endurance', 120.0, '2025-01-15', '2025-04-15', 'active');

-- IDs are sequentially numbered from 1-5
INSERT INTO HealthMetric (account_id, metric_name, metric_value)
VALUES
    (3, 'Bench Press (lb)', 275),
    (3, 'Squat (lb)', 365),

    (4, 'Weight (lb)',  133.4),
    (4, 'Body Fat %', 21.8),

    (5, 'VO2 Max', 42.1);

-- IDs are sequentially numbered from 1-4
INSERT INTO Booking (booking_type, booking_title, room_id, account_id, start_time, end_time)
VALUES
    ('class', 'Morning Yoga',      1, 1, '2025-03-10 09:00:00', '2025-03-10 10:00:00'),
    ('class', 'Spin Blast',        3, 2, '2025-03-11 11:00:00', '2025-03-11 12:00:00'),
    ('pt', 'Training with Vedder', 4, 2, '2025-12-09 08:00:00', '2025-12-09 10:00:00'),
    ('class', 'Group workout',   2, 1, '2025-03-14 18:00:00', '2025-03-14 19:00:00');

-- IDs are sequentially numbered from 1-7
INSERT INTO Registration (account_id, booking_id)
VALUES
    (3, 1),
    (4, 1),

    (4, 2),
    (5, 2),

    (3, 3),

    (4, 4),
    (5, 4);