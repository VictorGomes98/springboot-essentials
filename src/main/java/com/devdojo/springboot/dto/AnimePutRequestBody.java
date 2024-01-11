package com.devdojo.springboot.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private long id;
    private String name;
}
