-- Add detailed fields to project table
ALTER TABLE project
    ADD COLUMN team_size VARCHAR(100),
    ADD COLUMN project_role VARCHAR(100),
    ADD COLUMN owned_services VARCHAR(200),
    ADD COLUMN introduction_markdown TEXT,
    ADD COLUMN responsibilities_markdown TEXT,
    ADD COLUMN troubleshooting_markdown TEXT;

COMMENT ON COLUMN project.team_size IS '팀 규모';
COMMENT ON COLUMN project.project_role IS '역할';
COMMENT ON COLUMN project.owned_services IS '담당 서비스';
COMMENT ON COLUMN project.introduction_markdown IS '프로젝트 소개';
COMMENT ON COLUMN project.responsibilities_markdown IS '주요 개발 업무';
COMMENT ON COLUMN project.troubleshooting_markdown IS '기술적 의사결정 및 트러블슈팅';
