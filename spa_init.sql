CREATE DATABASE IF NOT EXISTS ecom;
USE ecom;

DROP TABLE IF EXISTS bookings;
DROP TABLE IF EXISTS available_timeslots;
DROP TABLE IF EXISTS spa_services;
DROP TABLE IF EXISTS work_schedule;

CREATE TABLE spa_services (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10, 2) NOT NULL,
    duration_minutes INT NOT NULL COMMENT 'Duration of the service in minutes'
);

CREATE TABLE available_timeslots (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    slot_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    is_booked BOOLEAN DEFAULT FALSE
);

CREATE TABLE bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    customer_email VARCHAR(100) NOT NULL,
    customer_phone VARCHAR(20),
    service_id BIGINT,
    timeslot_id BIGINT,
    booking_status ENUM('PENDING', 'APPROVED', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    confirmation_code VARCHAR(50) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (service_id) REFERENCES spa_services(id),
    FOREIGN KEY (timeslot_id) REFERENCES available_timeslots(id),
    UNIQUE KEY uk_timeslot_id (timeslot_id)
);

CREATE TABLE work_schedule (
    id INT AUTO_INCREMENT PRIMARY KEY,
    day_of_week INT NOT NULL UNIQUE COMMENT '0=Sunday, 1=Monday... 6=Saturday',
    is_active BOOLEAN DEFAULT FALSE,
    start_time TIME,
    end_time TIME
);

-- --- SAMPLE DATA ---
INSERT INTO spa_services (name, description, price, duration_minutes) VALUES
('Swedish Massage', 'A relaxing full-body massage.', 700.00, 60),
('Deep Tissue Massage', 'Targets deeper layers of muscle and connective tissue.', 800.00, 60),
('Hot Stone Massage', 'Uses warm stones to ease muscle tension.', 900.00, 75),
('Aromatherapy Facial', 'A relaxing facial using essential oils.', 500.00, 45);

INSERT INTO available_timeslots (slot_date, start_time, end_time, is_booked) VALUES
(CURDATE(), '09:00:00', '10:00:00', FALSE),
(CURDATE(), '10:00:00', '11:00:00', FALSE),
(CURDATE(), '11:00:00', '12:00:00', FALSE),
(CURDATE(), '13:00:00', '14:00:00', FALSE),
(CURDATE(), '14:00:00', '15:00:00', FALSE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '09:00:00', '10:00:00', FALSE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '10:00:00', '11:00:00', FALSE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '11:00:00', '12:00:00', FALSE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '13:00:00', '14:00:00', FALSE),
(DATE_ADD(CURDATE(), INTERVAL 1 DAY), '14:00:00', '15:00:00', FALSE);

INSERT INTO work_schedule (day_of_week, is_active, start_time, end_time) VALUES
(0, false, '09:00:00', '17:00:00'), 
(1, false, '09:00:00', '17:00:00'), 
(2, false, '09:00:00', '17:00:00'), 
(3, false, '09:00:00', '17:00:00'), 
(4, false, '09:00:00', '17:00:00'), 
(5, false, '09:00:00', '17:00:00'), 
(6, false, '09:00:00', '17:00:00'); 