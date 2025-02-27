package com.explore.backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedDeviceResponse {
    private List<DeviceResponseDTO> devices;
    private int currentPage;
    private int totalPages;
    private long totalItems;

    public PaginatedDeviceResponse(List<DeviceResponseDTO> devices, int currentPage, int totalPages, long totalItems) {
        this.devices = devices;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.totalItems = totalItems;
    }
}