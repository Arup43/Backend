package com.explore.backend.dto;

import lombok.Data;

@Data
public class DeviceStatsDTO {
    private long totalActiveDevices;
    private long executionOngoing;
    private long executionCompleted;
    private long totalLikes;
    private long totalComments;
    private long totalSubscribes;
    private long totalStreams;
}