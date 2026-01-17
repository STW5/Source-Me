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
public class WorkHistoryEntry {
    private String organization;
    private String period;
    private String role;
    private List<String> projects;
    private List<String> activities;
}
