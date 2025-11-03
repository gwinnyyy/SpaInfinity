-- Spa Booking System Database Schema

-- 1. Services Table (already exists as product_data, just modify)
ALTER TABLE product_data 
ADD COLUMN duration_in_minutes INT DEFAULT 60,
ADD COLUMN available BIT DEFAULT 1,
ADD COLUMN max_bookings_per_day INT DEFAULT 10;

-- 2. Therapists Table
CREATE TABLE therapist_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    specialization VARCHAR(255),
    phone VARCHAR(20),
    email VARCHAR(100),
    available BIT DEFAULT 1,
    created DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Time Slots Table
CREATE TABLE time_slot_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    slot_date DATE NOT NULL,
    slot_time TIME NOT NULL,
    therapist_id INT,
    is_available BIT DEFAULT 1,
    created DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (therapist_id) REFERENCES therapist_data(id),
    UNIQUE KEY unique_slot (slot_date, slot_time, therapist_id)
);

-- 4. Customer Table (enhance existing)
CREATE TABLE IF NOT EXISTS customer_data (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    date_of_birth DATE,
    gender VARCHAR(10),
    address TEXT,
    created DATETIME DEFAULT CURRENT_TIMESTAMP,
    last_updated DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 5. Bookings Table (already exists, enhance it)
ALTER TABLE booking_data 
ADD COLUMN customer_id INT,
ADD COLUMN therapist_id INT,
ADD COLUMN time_slot_id INT,
ADD COLUMN status VARCHAR(20) DEFAULT 'PENDING',
ADD COLUMN total_amount DECIMAL(10,2),
ADD COLUMN notes TEXT,
ADD COLUMN payment_status VARCHAR(20) DEFAULT 'UNPAID',
ADD FOREIGN KEY (customer_id) REFERENCES customer_data(id),
ADD FOREIGN KEY (therapist_id) REFERENCES therapist_data(id),
ADD FOREIGN KEY (time_slot_id) REFERENCES time_slot_data(id);

-- 6. Booking Items Table (already exists, modify)
ALTER TABLE booking_item_data
ADD COLUMN service_id INT,
ADD COLUMN duration_minutes INT DEFAULT 60,
ADD COLUMN therapist_id INT,
ADD FOREIGN KEY (service_id) REFERENCES product_data(id),
ADD FOREIGN KEY (therapist_id) REFERENCES therapist_data(id);

-- 7. Insert Sample Services
INSERT INTO product_data (name, description, category_name, price, duration_in_minutes, image_file, created, last_updated) VALUES
('Swedish Massage', 'Relaxing full body massage', 'Massage', '1200.00', 60, 'swedish_massage', NOW(), NOW()),
('Deep Tissue Massage', 'Therapeutic deep tissue massage', 'Massage', '1500.00', 90, 'deep_tissue', NOW(), NOW()),
('Facial Treatment', 'Rejuvenating facial treatment', 'Facial', '800.00', 45, 'facial', NOW(), NOW()),
('Hot Stone Therapy', 'Heated stone massage therapy', 'Massage', '1800.00', 75, 'hot_stone', NOW(), NOW()),
('Aromatherapy', 'Essential oil massage therapy', 'Massage', '1400.00', 60, 'aromatherapy', NOW(), NOW()),
('Body Scrub', 'Exfoliating body treatment', 'Body Treatment', '900.00', 45, 'body_scrub', NOW(), NOW());

-- 8. Insert Sample Therapists
INSERT INTO therapist_data (first_name, last_name, specialization, phone, email) VALUES
('Maria', 'Santos', 'Swedish Massage, Aromatherapy', '0917-123-4567', 'maria.santos@spa.com'),
('John', 'Cruz', 'Deep Tissue, Sports Massage', '0918-234-5678', 'john.cruz@spa.com'),
('Ana', 'Reyes', 'Facial Treatments', '0919-345-6789', 'ana.reyes@spa.com'),
('Pedro', 'Garcia', 'Hot Stone, Thai Massage', '0920-456-7890', 'pedro.garcia@spa.com');

-- 9. Generate Time Slots (9 AM to 7 PM, hourly)
INSERT INTO time_slot_data (slot_date, slot_time, therapist_id, is_available)
SELECT 
    CURDATE() + INTERVAL n DAY as slot_date,
    TIME(CONCAT(9 + (slot_num), ':00:00')) as slot_time,
    therapist.id as therapist_id,
    1 as is_available
FROM 
    (SELECT 0 as n UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6) days
CROSS JOIN
    (SELECT 0 as slot_num UNION SELECT 1 UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 
     UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) slots
CROSS JOIN
    therapist_data therapist
WHERE 9 + slot_num <= 19; -- Up to 7 PM

-- 10. Sample Customer
INSERT INTO customer_data (first_name, last_name, email, phone, gender) VALUES
('Juan', 'Dela Cruz', 'juan.delacruz@email.com', '0921-567-8901', 'Male'),
('Maria', 'Clara', 'maria.clara@email.com', '0922-678-9012', 'Female');