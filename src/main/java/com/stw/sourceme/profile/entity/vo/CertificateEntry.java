package com.stw.sourceme.profile.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificateEntry {
    private String name;
    private String issuer;
    private String date;
    private String score;
}
