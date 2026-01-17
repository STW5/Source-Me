package com.stw.sourceme.profile.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InternshipEntry {
    private String title;
    private String period;
    private String description;
}
