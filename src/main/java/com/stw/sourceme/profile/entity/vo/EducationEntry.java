package com.stw.sourceme.profile.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationEntry {
    private String institution;
    private String period;
    private String major;
    private String minor;
    private String gpa;
    private String majorGpa;
    private List<String> activities;
}
