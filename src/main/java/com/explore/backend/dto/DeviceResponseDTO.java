package com.explore.backend.dto;

import lombok.Data;

@Data
public class DeviceResponseDTO {
    private String id;
    private String status;
    private Boolean isActive;
    private Boolean hasLike;
    private Boolean hasComment;
    private Boolean hasShare;
    private Boolean hasStream;
}