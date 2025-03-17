package com.explore.backend.dto;

import lombok.Data;

@Data
public class DeviceRequestDTO {
    private String status;
    private Boolean isActive;
    private Boolean hasLike;
    private Boolean hasComment;
    private Boolean hasSubscribe;
    private Boolean hasStream;
}