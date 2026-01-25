-- Source-Me: 포트폴리오 플랫폼
-- 3-Layer Architecture Migration V1

-- UUID 확장 활성화
CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;

-- ==================================================
-- TABLES
-- ==================================================

-- 미디어 파일
CREATE TABLE media_file (
    id bigserial PRIMARY KEY,
    storage_type varchar(20) NOT NULL DEFAULT 'LOCAL',
    file_key varchar(500) NOT NULL,
    public_url varchar(700),
    original_name varchar(255) NOT NULL,
    content_type varchar(100),
    size_bytes bigint NOT NULL DEFAULT 0,
    created_at timestamptz NOT NULL DEFAULT now()
);
COMMENT ON TABLE media_file IS '업로드 파일 메타 정보';
COMMENT ON COLUMN media_file.storage_type IS 'LOCAL / S3';
COMMENT ON COLUMN media_file.file_key IS '로컬 경로 또는 S3 key';

-- 태그
CREATE TABLE tag (
    id bigserial PRIMARY KEY,
    name varchar(50) NOT NULL UNIQUE
);
COMMENT ON TABLE tag IS '태그';
CREATE INDEX idx_tag_name ON tag(name);

-- 사용자
CREATE TABLE users (
    id bigserial PRIMARY KEY,
    username varchar(50) NOT NULL UNIQUE,
    password varchar(255) NOT NULL,
    email varchar(100) NOT NULL,
    enabled boolean NOT NULL DEFAULT true,
    created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_users_username ON users(username);

-- 프로필
CREATE TABLE profile (
    id bigserial PRIMARY KEY,
    display_name varchar(100) NOT NULL,
    headline varchar(200) NOT NULL,
    bio_markdown text NOT NULL,
    email varchar(120),
    github_url varchar(255),
    linkedin_url varchar(255),
    resume_url varchar(255),
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    profile_media_id bigint,
    career_goal text,
    experience_highlights text,
    skills_proficient text,
    skills_education text,
    skills_can_use text,
    backend_experience text,
    internships jsonb,
    education jsonb,
    work_history jsonb,
    publications_patents jsonb,
    certificates jsonb,
    CONSTRAINT fk_profile_media FOREIGN KEY (profile_media_id) REFERENCES media_file(id) ON DELETE SET NULL
);
COMMENT ON TABLE profile IS '메인 소개 데이터 (1행만 사용)';
COMMENT ON COLUMN profile.profile_media_id IS '프로필 사진 미디어 파일 ID';
COMMENT ON COLUMN profile.internships IS '인턴십 경험 (JSON 배열)';
COMMENT ON COLUMN profile.education IS '학력 정보 (JSON 배열)';
COMMENT ON COLUMN profile.work_history IS '경력 이력 (JSON 배열)';
COMMENT ON COLUMN profile.publications_patents IS '논문 및 특허 (JSON 배열)';
COMMENT ON COLUMN profile.certificates IS '자격증 (JSON 배열)';

-- 프로젝트
CREATE TABLE project (
    id bigserial PRIMARY KEY,
    title varchar(200) NOT NULL,
    slug varchar(220) NOT NULL UNIQUE,
    summary varchar(300) NOT NULL,
    content_markdown text NOT NULL,
    started_at date,
    ended_at date,
    is_published boolean NOT NULL DEFAULT true,
    is_featured boolean NOT NULL DEFAULT false,
    featured_order int NOT NULL DEFAULT 0,
    github_url varchar(255),
    demo_url varchar(255),
    thumbnail_media_id bigint,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    team_size varchar(100),
    project_role varchar(100),
    owned_services varchar(200),
    introduction_markdown text,
    responsibilities_markdown text,
    troubleshooting_markdown text,
    CONSTRAINT fk_project_thumbnail FOREIGN KEY (thumbnail_media_id) REFERENCES media_file(id) ON DELETE SET NULL
);
COMMENT ON TABLE project IS '포트폴리오 프로젝트';
COMMENT ON COLUMN project.team_size IS '팀 규모';
COMMENT ON COLUMN project.project_role IS '역할';
COMMENT ON COLUMN project.owned_services IS '담당 서비스';
COMMENT ON COLUMN project.introduction_markdown IS '프로젝트 소개';
COMMENT ON COLUMN project.responsibilities_markdown IS '주요 개발 업무';
COMMENT ON COLUMN project.troubleshooting_markdown IS '기술적 의사결정 및 트러블슈팅';

CREATE UNIQUE INDEX idx_project_slug ON project(slug);
CREATE INDEX idx_project_published ON project(is_published, updated_at DESC);
CREATE INDEX idx_project_featured ON project(is_featured, featured_order);

-- 프로젝트-태그 매핑
CREATE TABLE project_tag (
    project_id bigint NOT NULL,
    tag_id bigint NOT NULL,
    PRIMARY KEY (project_id, tag_id),
    CONSTRAINT fk_project_tag_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_tag_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);
COMMENT ON TABLE project_tag IS '프로젝트-태그 매핑';
CREATE INDEX idx_project_tag_tag ON project_tag(tag_id);

-- 프로젝트-미디어 매핑
CREATE TABLE project_media (
    project_id bigint NOT NULL,
    media_id bigint NOT NULL,
    sort_order int NOT NULL DEFAULT 0,
    PRIMARY KEY (project_id, media_id),
    CONSTRAINT fk_project_media_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_media_media FOREIGN KEY (media_id) REFERENCES media_file(id) ON DELETE CASCADE
);
COMMENT ON TABLE project_media IS '프로젝트 이미지 갤러리';
CREATE INDEX idx_project_media_sort ON project_media(project_id, sort_order);

-- 블로그 포스트
CREATE TABLE blog_post (
    id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    title varchar(200) NOT NULL,
    summary varchar(300),
    content_markdown text NOT NULL,
    status varchar(20) NOT NULL CHECK (status IN ('DRAFT', 'PUBLISHED')),
    published_at timestamptz,
    thumbnail_media_id bigint,
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    view_count bigint NOT NULL DEFAULT 0,
    like_count bigint NOT NULL DEFAULT 0,
    CONSTRAINT fk_blog_post_thumbnail FOREIGN KEY (thumbnail_media_id) REFERENCES media_file(id) ON DELETE SET NULL
);
COMMENT ON TABLE blog_post IS '기술 블로그 글';
COMMENT ON COLUMN blog_post.view_count IS '조회수';
COMMENT ON COLUMN blog_post.like_count IS '좋아요 수';

CREATE INDEX idx_blog_post_status ON blog_post(status, published_at DESC);
CREATE INDEX idx_blog_post_view_count ON blog_post(view_count DESC);
CREATE INDEX idx_blog_post_like_count ON blog_post(like_count DESC);

-- 블로그-태그 매핑
CREATE TABLE blog_post_tag (
    blog_post_id uuid NOT NULL,
    tag_id bigint NOT NULL,
    PRIMARY KEY (blog_post_id, tag_id),
    CONSTRAINT fk_blog_post_tag_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_tag_tag FOREIGN KEY (tag_id) REFERENCES tag(id) ON DELETE CASCADE
);
COMMENT ON TABLE blog_post_tag IS '글-태그 매핑';
CREATE INDEX idx_blog_post_tag_tag ON blog_post_tag(tag_id);

-- 블로그-미디어 매핑
CREATE TABLE blog_post_media (
    blog_post_id uuid NOT NULL,
    media_id bigint NOT NULL,
    sort_order int NOT NULL DEFAULT 0,
    PRIMARY KEY (blog_post_id, media_id),
    CONSTRAINT fk_blog_post_media_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_media_media FOREIGN KEY (media_id) REFERENCES media_file(id) ON DELETE CASCADE
);
COMMENT ON TABLE blog_post_media IS '글-미디어 매핑';
CREATE INDEX idx_blog_post_media_post ON blog_post_media(blog_post_id, sort_order);

-- 블로그 좋아요
CREATE TABLE blog_post_like (
    user_id bigint NOT NULL,
    blog_post_id uuid NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, blog_post_id),
    CONSTRAINT fk_blog_post_like_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_like_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE
);
COMMENT ON TABLE blog_post_like IS '블로그 게시글 좋아요';
CREATE INDEX idx_blog_post_like_post ON blog_post_like(blog_post_id);
CREATE INDEX idx_blog_post_like_created_at ON blog_post_like(created_at DESC);

-- 블로그 북마크
CREATE TABLE blog_post_bookmark (
    user_id bigint NOT NULL,
    blog_post_id uuid NOT NULL,
    created_at timestamptz NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, blog_post_id),
    CONSTRAINT fk_blog_post_bookmark_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_bookmark_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE
);
COMMENT ON TABLE blog_post_bookmark IS '블로그 게시글 북마크';
CREATE INDEX idx_blog_post_bookmark_post ON blog_post_bookmark(blog_post_id);
CREATE INDEX idx_blog_post_bookmark_user_created ON blog_post_bookmark(user_id, created_at DESC);


-- ==================================================
-- SAMPLE DATA
-- ==================================================

