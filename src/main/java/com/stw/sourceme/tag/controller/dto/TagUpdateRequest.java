package com.stw.sourceme.tag.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TagUpdateRequest {

    @NotBlank(message = "태그명은 필수입니다.")
    @Size(max = 50, message = "태그명은 50자 이하여야 합니다.")
    private String name;
}
