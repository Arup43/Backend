package com.explore.backend.dto;

import lombok.Data;

@Data
public class CommandResponseDTO {
    private String react;
    private String comment;
    private Integer streamSec;
    private Integer reactAt;
    private Integer commentAt;
    private Integer shareAt;
}