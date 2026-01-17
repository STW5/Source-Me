package com.stw.sourceme.profile.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationPatentEntry {
    private String type; // "PUBLICATION" or "PATENT"
    private String title;
    private String details;
    private String date;
    private String description;
}
