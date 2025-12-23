-- Initial Schema for Source-Me Application
-- Users (관리자 계정) Table

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN')),
    is_active BOOLEAN NOT NULL DEFAULT true,
    last_login_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

-- Create indexes
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_is_active ON users(is_active);

-- Add comments
COMMENT ON TABLE users IS '관리자 계정 정보';
COMMENT ON COLUMN users.id IS '관리자 식별자';
COMMENT ON COLUMN users.username IS '로그인 ID';
COMMENT ON COLUMN users.password_hash IS 'BCrypt 해시 비밀번호';
COMMENT ON COLUMN users.role IS '역할 (ADMIN만 허용)';
COMMENT ON COLUMN users.is_active IS '계정 활성화 여부';
COMMENT ON COLUMN users.last_login_at IS '마지막 로그인 시간';
COMMENT ON COLUMN users.created_at IS '생성일시';
COMMENT ON COLUMN users.updated_at IS '수정일시';
