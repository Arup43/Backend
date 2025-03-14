package com.explore.backend.dto;

import lombok.Data;

@Data
public class CommandYTDto {
    private String link;
    private Integer len;
    private Integer numOfDevices;
    private Boolean subscribe;
    private String description;
}
