CREATE TABLE Account (
    account_id SERIAL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20),
    email VARCHAR(50) UNIQUE NOT NULL,
    phone_number VARCHAR(20),
    date_of_birth DATE
);

CREATE TABLE Goal (
    goal_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL REFERENCES Account(account_id) ON DELETE CASCADE,
    goal_type VARCHAR(50) NOT NULL,
    target_value NUMERIC(10,2) NOT NULL,
    start_date DATE,
    target_date DATE,
    status VARCHAR(20) CHECK (status IN ('active','completed','cancelled')) DEFAULT 'active'
);

CREATE TABLE HealthMetric (
    metric_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL REFERENCES Account(account_id) ON DELETE CASCADE,
    metric_name VARCHAR(50) NOT NULL,
    metric_value NUMERIC(10,2) NOT NULL,
recorded_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE Room (
    room_id SERIAL PRIMARY KEY,
    title VARCHAR(50) UNIQUE NOT NULL,
    capacity INT NOT NULL CHECK (capacity > 0) DEFAULT 1
);

CREATE TABLE Booking (
    booking_id SERIAL PRIMARY KEY,
    booking_type VARCHAR(50) NOT NULL CHECK	(booking_type IN ('class','pt')),
    booking_title VARCHAR(50),
    room_id INT NOT NULL REFERENCES Room(room_id) ON DELETE CASCADE,
    account_id INT NOT NULL REFERENCES Account(account_id) ON DELETE CASCADE,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    CHECK (end_time > start_time)
);

CREATE TABLE Registration (
    registration_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL REFERENCES Account(account_id) ON DELETE CASCADE,
    booking_id INT NOT NULL REFERENCES Booking(booking_id) ON DELETE CASCADE,
    UNIQUE (account_id, booking_id)
)
