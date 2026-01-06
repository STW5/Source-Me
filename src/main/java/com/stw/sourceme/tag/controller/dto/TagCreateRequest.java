package com.stw.sourceme.tag.controller.dto;

import com.stw.sourceme.tag.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagCreateRequest {

    @NotBlank(message = "태그명은 필수입니다.")
    @Size(max = 50, message = "태그명은 50자 이하여야 합니다.")
    private String name;

    public Tag toEntity() {
        return Tag.builder()
                .name(name)
                .build();
    }
}
