package com.explore.backend.service;

import com.explore.backend.dto.DeviceRequestDTO;
import com.explore.backend.dto.DeviceResponseDTO;
import com.explore.backend.dto.DeviceStatsDTO;
import com.explore.backend.dto.PaginatedDeviceResponse;
import com.explore.backend.mapper.DeviceMapper;
import com.explore.backend.model.Device;
import com.explore.backend.repository.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeviceService {

    private static final int PAGE_SIZE = 10;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceMapper deviceMapper;

    public PaginatedDeviceResponse getAllDevices(int page) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.ASC, "id"));
        Page<Device> devicePage = deviceRepository.findAll(pageable);
        
        List<DeviceResponseDTO> devices = devicePage.getContent()
            .stream()
            .map(deviceMapper::toDTO)
            .collect(Collectors.toList());
            
        return new PaginatedDeviceResponse(
            devices,
            devicePage.getNumber(),
            devicePage.getTotalPages(),
            devicePage.getTotalElements()
        );
    }

    public Optional<DeviceResponseDTO> getDeviceById(String id) {
        return deviceRepository.findById(id)
                .map(deviceMapper::toDTO);
    }

    public DeviceResponseDTO createDevice(DeviceRequestDTO deviceDTO) {
        Device device = deviceMapper.toEntity(deviceDTO);
        Device savedDevice = deviceRepository.save(device);
        return deviceMapper.toDTO(savedDevice);
    }

    public Optional<DeviceResponseDTO> updateDevice(String id, DeviceRequestDTO deviceDTO) {
        return deviceRepository.findById(id)
                .map(existingDevice -> {
                    deviceMapper.updateEntityFromDTO(deviceDTO, existingDevice);
                    Device updatedDevice = deviceRepository.save(existingDevice);
                    return deviceMapper.toDTO(updatedDevice);
                });
    }

    public void deleteDevice(String id) {
        deviceRepository.deleteById(id);
    }

    public Optional<DeviceResponseDTO> updateDeviceStatus(String id, String status) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setStatus(status);
                    return deviceMapper.toDTO(deviceRepository.save(device));
                });
    }

    public Optional<DeviceResponseDTO> updateDeviceActive(String id, Boolean isActive) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setIsActive(isActive);
                    return deviceMapper.toDTO(deviceRepository.save(device));
                });
    }

    public Optional<DeviceResponseDTO> updateDeviceLike(String id, Boolean hasLike) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setHasLike(hasLike);
                    return deviceMapper.toDTO(deviceRepository.save(device));
                });
    }

    public Optional<DeviceResponseDTO> updateDeviceComment(String id, Boolean hasComment) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setHasComment(hasComment);
                    return deviceMapper.toDTO(deviceRepository.save(device));
                });
    }

    public Optional<DeviceResponseDTO> updateDeviceShare(String id, Boolean hasShare) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setHasShare(hasShare);
                    return deviceMapper.toDTO(deviceRepository.save(device));
                });
    }

    public Optional<DeviceResponseDTO> updateDeviceStream(String id, Boolean hasStream) {
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setHasStream(hasStream);
                    return deviceMapper.toDTO(deviceRepository.save(device));
                });
    }

    public DeviceStatsDTO getDeviceStats() {
        DeviceStatsDTO stats = new DeviceStatsDTO();
        stats.setTotalActiveDevices(deviceRepository.count());
        stats.setExecutionOngoing(deviceRepository.countByStatus("active"));
        stats.setExecutionCompleted(deviceRepository.countByStatus("completed"));
        stats.setTotalLikes(deviceRepository.countByHasLikeTrue());
        stats.setTotalComments(deviceRepository.countByHasCommentTrue());
        stats.setTotalShares(deviceRepository.countByHasShareTrue());
        stats.setTotalStream(deviceRepository.countByHasStreamTrue());
        return stats;
    }
}