CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    phone VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    birthday DATE NOT NULL
);

CREATE TABLE properties (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    address VARCHAR(100) NOT NULL,
    price DECIMAL(8,2) NOT NULL,
    description VARCHAR NOT NULL,
    is_available BOOLEAN
);

CREATE TABLE reservations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    user_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_property FOREIGN KEY (property_id) REFERENCES properties(id)
);

CREATE TABLE reviews (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rating INT NOT NULL,
    comment VARCHAR,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT NOT NULL,
    property_id BIGINT NOT NULL,

    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_property FOREIGN KEY (property_id) REFERENCES properties(id)
);