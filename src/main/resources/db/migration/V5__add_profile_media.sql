-- Add profile media support
ALTER TABLE profile ADD COLUMN profile_media_id BIGINT;
ALTER TABLE profile ADD CONSTRAINT fk_profile_media FOREIGN KEY (profile_media_id) REFERENCES media_file(id) ON DELETE SET NULL;

COMMENT ON COLUMN profile.profile_media_id IS '프로필 사진 미디어 파일 ID';