CREATE TABLE IF NOT EXISTS images
(
    id INT NOT NULL AUTO_INCREMENT,
    object_id INTEGER,
    original_id INTEGER UNIQUE,
    is_primary BOOLEAN DEFAULT TRUE NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS image_sizes
(
    image_id INTEGER,
    label VARCHAR(10) NOT NULL,
    width INTEGER,
    height INTEGER,
    url VARCHAR(100),
    PRIMARY KEY (image_id, label)
);
CREATE TABLE IF NOT EXISTS roles
(
    id INT NOT NULL AUTO_INCREMENT,
    original_id INTEGER UNIQUE,
    name VARCHAR(100),
    display_name VARCHAR(100),
    `date` VARCHAR(100),
    url VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS participants
(
    id INT NOT NULL AUTO_INCREMENT,
    original_id INTEGER UNIQUE,
    name VARCHAR(100),
    `date` VARCHAR(100),
    url VARCHAR(100),
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS participations
(
    id INT NOT NULL AUTO_INCREMENT,
    object_id INTEGER,
    participant_id INTEGER,
    role_id INTEGER,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS ch_objects
(
    id INT NOT NULL AUTO_INCREMENT,
    original_id INTEGER,
    title VARCHAR(200),
    credit_line VARCHAR(200),
    gallery_text TEXT,
    `date` VARCHAR(100),
    medium VARCHAR(200),
    description TEXT,
    PRIMARY KEY (id)
);