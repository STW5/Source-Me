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

-- ============================================================
-- 2) site_profile (메인 소개 데이터)
-- ============================================================
CREATE TABLE site_profile (
    id BIGSERIAL PRIMARY KEY,
    display_name VARCHAR(100) NOT NULL,
    headline VARCHAR(200) NOT NULL,
    bio_markdown TEXT NOT NULL,
    email VARCHAR(120),
    github_url VARCHAR(255),
    linkedin_url VARCHAR(255),
    resume_url VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

COMMENT ON TABLE site_profile IS '메인 소개 데이터 (1행만 사용)';

-- ============================================================
-- 3) media_file (업로드 파일 메타)
-- ============================================================
CREATE TABLE media_file (
    id BIGSERIAL PRIMARY KEY,
    storage_type VARCHAR(20) NOT NULL DEFAULT 'LOCAL',
    file_key VARCHAR(500) NOT NULL,
    public_url VARCHAR(700),
    original_name VARCHAR(255) NOT NULL,
    content_type VARCHAR(100),
    size_bytes BIGINT NOT NULL DEFAULT 0,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

COMMENT ON TABLE media_file IS '업로드 파일 메타 정보';
COMMENT ON COLUMN media_file.storage_type IS 'LOCAL / S3';
COMMENT ON COLUMN media_file.file_key IS '로컬 경로 또는 S3 key';

-- ============================================================
-- 4) tag (태그)
-- ============================================================
CREATE TABLE tag (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE INDEX idx_tag_name ON tag(name);

COMMENT ON TABLE tag IS '태그';

-- ============================================================
-- 5) project (포트폴리오)
-- ============================================================
CREATE TABLE project (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(220) NOT NULL UNIQUE,
    summary VARCHAR(300) NOT NULL,
    content_markdown TEXT NOT NULL,
    started_at DATE,
    ended_at DATE,
    is_published BOOLEAN NOT NULL DEFAULT true,
    is_featured BOOLEAN NOT NULL DEFAULT false,
    featured_order INT NOT NULL DEFAULT 0,
    github_url VARCHAR(255),
    demo_url VARCHAR(255),
    thumbnail_media_id BIGINT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_project_thumbnail FOREIGN KEY (thumbnail_media_id) REFERENCES media_file(id) ON DELETE SET NULL
);

CREATE UNIQUE INDEX idx_project_slug ON project(slug);
CREATE INDEX idx_project_published ON project(is_published, updated_at DESC);
CREATE INDEX idx_project_featured ON project(is_featured, featured_order ASC);

COMMENT ON TABLE project IS '포트폴리오 프로젝트';

-- ============================================================
-- 6) blog_post (기술 블로그)
-- ============================================================
CREATE TABLE blog_post (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    slug VARCHAR(220) NOT NULL UNIQUE,
    summary VARCHAR(300),
    content_markdown TEXT NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('DRAFT', 'PUBLISHED')),
    published_at TIMESTAMPTZ,
    thumbnail_media_id BIGINT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT fk_blog_post_thumbnail FOREIGN KEY (thumbnail_media_id) REFERENCES media_file(id) ON DELETE SET NULL
);

CREATE UNIQUE INDEX idx_blog_post_slug ON blog_post(slug);
CREATE INDEX idx_blog_post_status ON blog_post(status, published_at DESC);

COMMENT ON TABLE blog_post IS '기술 블로그 글';

-- ============================================================
-- 7) project_tag (프로젝트-태그 매핑)
-- ============================================================
CREATE TABLE project_tag (
    project_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (project_id, tag_id),
    CONSTRAINT fk_project_tag_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_tag_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);

CREATE INDEX idx_project_tag_tag ON project_tag(tag_id);

COMMENT ON TABLE project_tag IS '프로젝트-태그 매핑';

-- ============================================================
-- 8) blog_post_tag (글-태그 매핑)
-- ============================================================
CREATE TABLE blog_post_tag (
    blog_post_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (blog_post_id, tag_id),
    CONSTRAINT fk_blog_post_tag_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_tag_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);

CREATE INDEX idx_blog_post_tag_tag ON blog_post_tag(tag_id);

COMMENT ON TABLE blog_post_tag IS '블로그 글-태그 매핑';

-- ============================================================
-- 9) project_media (프로젝트 이미지 갤러리 - 옵션)
-- ============================================================
CREATE TABLE project_media (
    project_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    PRIMARY KEY (project_id, media_id),
    CONSTRAINT fk_project_media_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_media_media FOREIGN KEY (media_id) REFERENCES media_file(id) ON DELETE CASCADE
);

CREATE INDEX idx_project_media_sort ON project_media(project_id, sort_order ASC);

COMMENT ON TABLE project_media IS '프로젝트 이미지 갤러리';

-- ============================================================
-- 10) blog_post_media (블로그 글 이미지 갤러리 - 옵션)
-- ============================================================
CREATE TABLE blog_post_media (
    blog_post_id BIGINT NOT NULL,
    media_id BIGINT NOT NULL,
    sort_order INT NOT NULL DEFAULT 0,
    PRIMARY KEY (blog_post_id, media_id),
    CONSTRAINT fk_blog_post_media_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_media_media FOREIGN KEY (media_id) REFERENCES media_file(id) ON DELETE CASCADE
);

CREATE INDEX idx_blog_post_media_sort ON blog_post_media(blog_post_id, sort_order ASC);

COMMENT ON TABLE blog_post_media IS '블로그 글 이미지 갤러리';
