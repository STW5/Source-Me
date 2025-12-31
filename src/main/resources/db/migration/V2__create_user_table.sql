-- Create user table for authentication
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL,
    enabled BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create index for username lookup
CREATE INDEX idx_users_username ON users(username);

-- Insert default admin user (password: admin123)
-- BCrypt hash of "admin123"
INSERT INTO users (username, password, email, enabled)
VALUES ('admin', '$2a$10$Znj6d5HKNDglA.sEpat7FuSGGNBB8gN8bI96YS.3isTe/QDW4344K', 'admin@example.com', true);
