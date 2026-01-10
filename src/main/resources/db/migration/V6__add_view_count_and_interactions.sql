-- Add view count and like count to blog_post and project

-- Add view_count and like_count to blog_post
ALTER TABLE blog_post
    ADD COLUMN view_count BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN like_count BIGINT NOT NULL DEFAULT 0;

CREATE INDEX idx_blog_post_view_count ON blog_post(view_count DESC);
CREATE INDEX idx_blog_post_like_count ON blog_post(like_count DESC);

COMMENT ON COLUMN blog_post.view_count IS '조회수';
COMMENT ON COLUMN blog_post.like_count IS '좋아요 수';

-- Add view_count and like_count to project
ALTER TABLE project
    ADD COLUMN view_count BIGINT NOT NULL DEFAULT 0,
    ADD COLUMN like_count BIGINT NOT NULL DEFAULT 0;

CREATE INDEX idx_project_view_count ON project(view_count DESC);
CREATE INDEX idx_project_like_count ON project(like_count DESC);

COMMENT ON COLUMN project.view_count IS '조회수';
COMMENT ON COLUMN project.like_count IS '좋아요 수';

-- Create blog_post_like table
CREATE TABLE blog_post_like (
    user_id BIGINT NOT NULL,
    blog_post_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, blog_post_id),
    CONSTRAINT fk_blog_post_like_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_like_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE
);

CREATE INDEX idx_blog_post_like_post ON blog_post_like(blog_post_id);
CREATE INDEX idx_blog_post_like_created_at ON blog_post_like(created_at DESC);

COMMENT ON TABLE blog_post_like IS '블로그 게시글 좋아요';

-- Create project_like table
CREATE TABLE project_like (
    user_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, project_id),
    CONSTRAINT fk_project_like_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_like_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE INDEX idx_project_like_project ON project_like(project_id);
CREATE INDEX idx_project_like_created_at ON project_like(created_at DESC);

COMMENT ON TABLE project_like IS '프로젝트 좋아요';

-- Create blog_post_bookmark table
CREATE TABLE blog_post_bookmark (
    user_id BIGINT NOT NULL,
    blog_post_id UUID NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, blog_post_id),
    CONSTRAINT fk_blog_post_bookmark_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_blog_post_bookmark_post FOREIGN KEY (blog_post_id) REFERENCES blog_post(id) ON DELETE CASCADE
);

CREATE INDEX idx_blog_post_bookmark_post ON blog_post_bookmark(blog_post_id);
CREATE INDEX idx_blog_post_bookmark_user_created ON blog_post_bookmark(user_id, created_at DESC);

COMMENT ON TABLE blog_post_bookmark IS '블로그 게시글 북마크';

-- Create project_bookmark table
CREATE TABLE project_bookmark (
    user_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, project_id),
    CONSTRAINT fk_project_bookmark_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_project_bookmark_project FOREIGN KEY (project_id) REFERENCES project(id) ON DELETE CASCADE
);

CREATE INDEX idx_project_bookmark_project ON project_bookmark(project_id);
CREATE INDEX idx_project_bookmark_user_created ON project_bookmark(user_id, created_at DESC);

COMMENT ON TABLE project_bookmark IS '프로젝트 북마크';
