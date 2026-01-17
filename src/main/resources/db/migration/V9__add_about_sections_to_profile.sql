-- Add Internship, Education, Work History, Publications & Patents, Certificate columns to profile table
-- Using JSONB for flexible data structure

ALTER TABLE profile ADD COLUMN internships JSONB;
ALTER TABLE profile ADD COLUMN education JSONB;
ALTER TABLE profile ADD COLUMN work_history JSONB;
ALTER TABLE profile ADD COLUMN publications_patents JSONB;
ALTER TABLE profile ADD COLUMN certificates JSONB;

COMMENT ON COLUMN profile.internships IS '인턴십 경험 (JSON 배열)';
COMMENT ON COLUMN profile.education IS '학력 정보 (JSON 배열)';
COMMENT ON COLUMN profile.work_history IS '경력 이력 (JSON 배열)';
COMMENT ON COLUMN profile.publications_patents IS '논문 및 특허 (JSON 배열)';
COMMENT ON COLUMN profile.certificates IS '자격증 (JSON 배열)';
