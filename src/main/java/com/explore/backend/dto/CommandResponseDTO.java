package com.explore.backend.dto;

import lombok.Data;

@Data
public class CommandResponseDTO {
    private String link;
    private String comment;
    private Integer len;
    private Boolean subscribe;
}