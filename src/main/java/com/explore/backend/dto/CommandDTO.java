package com.explore.backend.dto;

import lombok.Data;

@Data
public class CommandDTO {
    private String link;
    private Integer length;
    private Integer numOfDevices;
}