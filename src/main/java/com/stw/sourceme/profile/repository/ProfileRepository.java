package com.stw.sourceme.profile.repository;

import com.stw.sourceme.profile.entity.SiteProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<SiteProfile, Long> {
}
