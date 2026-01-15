-- Remove project view/like counts and interactions
DROP INDEX IF EXISTS idx_project_view_count;
DROP INDEX IF EXISTS idx_project_like_count;

ALTER TABLE project
    DROP COLUMN IF EXISTS view_count,
    DROP COLUMN IF EXISTS like_count;

DROP TABLE IF EXISTS project_like;
DROP TABLE IF EXISTS project_bookmark;
