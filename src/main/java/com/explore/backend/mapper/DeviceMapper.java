package com.explore.backend.mapper;

import com.explore.backend.dto.DeviceRequestDTO;
import com.explore.backend.dto.DeviceResponseDTO;
import com.explore.backend.model.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    
    public Device toEntity(DeviceRequestDTO dto) {
        Device device = new Device();
        device.setStatus(dto.getStatus());
        device.setIsActive(dto.getIsActive());
        device.setHasLike(dto.getHasLike());
        device.setHasComment(dto.getHasComment());
        device.setHasShare(dto.getHasShare());
        device.setHasStream(dto.getHasStream());
        return device;
    }
    
    public DeviceResponseDTO toDTO(Device device) {
        DeviceResponseDTO dto = new DeviceResponseDTO();
        dto.setId(device.getId());
        dto.setStatus(device.getStatus());
        dto.setIsActive(device.getIsActive());
        dto.setHasLike(device.getHasLike());
        dto.setHasComment(device.getHasComment());
        dto.setHasShare(device.getHasShare());
        dto.setHasStream(device.getHasStream());
        return dto;
    }

    public void updateEntityFromDTO(DeviceRequestDTO dto, Device device) {
        device.setStatus(dto.getStatus());
        device.setIsActive(dto.getIsActive());
        device.setHasLike(dto.getHasLike());
        device.setHasComment(dto.getHasComment());
        device.setHasShare(dto.getHasShare());
        device.setHasStream(dto.getHasStream());
    }
}