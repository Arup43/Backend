package com.explore.backend.controller;

import com.explore.backend.dto.DeviceRequestDTO;
import com.explore.backend.dto.DeviceResponseDTO;
import com.explore.backend.dto.patch.DeviceStatusPatchDTO;
import com.explore.backend.dto.patch.DeviceActivePatchDTO;
import com.explore.backend.dto.patch.DeviceLikePatchDTO;
import com.explore.backend.dto.patch.DeviceCommentPatchDTO;
import com.explore.backend.dto.patch.DeviceSharePatchDTO;
import com.explore.backend.dto.patch.DeviceStreamPatchDTO;
import com.explore.backend.dto.PaginatedDeviceResponse;
import com.explore.backend.dto.DeviceStatsDTO;
import com.explore.backend.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Device", description = "Device management APIs")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping
    @Operation(summary = "Get all devices with pagination", description = "Retrieves a paginated list of devices in the system (10 devices per page)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved devices", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaginatedDeviceResponse.class)))
    public PaginatedDeviceResponse getAllDevices(
            @Parameter(description = "Page number (starts from 0)") @RequestParam(defaultValue = "0") int page) {
        return deviceService.getAllDevices(page);
    }

    @Operation(summary = "Get device by ID", description = "Retrieves a specific device by its ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved the device", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> getDeviceById(
            @Parameter(description = "ID of the device to retrieve") @PathVariable String id) {
        return deviceService.getDeviceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Create a new device", description = "Creates a new device with default values (status: active, isActive: true, all features disabled)")
    @ApiResponse(responseCode = "200", description = "Device created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @PostMapping
    public String createDevice() {
        // Create a default DeviceRequestDTO with specified values
        DeviceRequestDTO defaultDeviceDTO = new DeviceRequestDTO();
        defaultDeviceDTO.setStatus("active");
        defaultDeviceDTO.setIsActive(true);
        defaultDeviceDTO.setHasLike(false);
        defaultDeviceDTO.setHasComment(false);
        defaultDeviceDTO.setHasShare(false);
        defaultDeviceDTO.setHasStream(false);

        return deviceService.createDevice(defaultDeviceDTO).getId();
    }

    @Operation(summary = "Update a device", description = "Updates an existing device with the provided information")
    @ApiResponse(responseCode = "200", description = "Device updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponseDTO> updateDevice(
            @Parameter(description = "ID of the device to update") @PathVariable String id,
            @RequestBody DeviceRequestDTO deviceDTO) {
        return deviceService.updateDevice(id, deviceDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete a device", description = "Deletes a device by its ID")
    @ApiResponse(responseCode = "200", description = "Device deleted successfully")
    @ApiResponse(responseCode = "404", description = "Device not found")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDevice(
            @Parameter(description = "ID of the device to delete") @PathVariable String id) {
        return deviceService.getDeviceById(id)
                .map(device -> {
                    deviceService.deleteDevice(id);
                    return ResponseEntity.ok(id);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update device status", description = "Updates only the status of an existing device")
    @ApiResponse(responseCode = "200", description = "Device status updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PatchMapping("/{id}/status")
    public ResponseEntity<DeviceResponseDTO> updateDeviceStatus(
            @Parameter(description = "ID of the device") @PathVariable String id,
            @RequestBody DeviceStatusPatchDTO patchDTO) {
        return deviceService.updateDeviceStatus(id, patchDTO.getStatus())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update device active status", description = "Updates only the active status of an existing device")
    @ApiResponse(responseCode = "200", description = "Device active status updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PatchMapping("/{id}/active")
    public ResponseEntity<DeviceResponseDTO> updateDeviceActive(
            @Parameter(description = "ID of the device") @PathVariable String id,
            @RequestBody DeviceActivePatchDTO patchDTO) {
        return deviceService.updateDeviceActive(id, patchDTO.getIsActive())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update device like status", description = "Updates only the like status of an existing device")
    @ApiResponse(responseCode = "200", description = "Device like status updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PatchMapping("/{id}/like")
    public ResponseEntity<DeviceResponseDTO> updateDeviceLike(
            @Parameter(description = "ID of the device") @PathVariable String id,
            @RequestBody DeviceLikePatchDTO patchDTO) {
        return deviceService.updateDeviceLike(id, patchDTO.getHasLike())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update device comment status", description = "Updates only the comment status of an existing device")
    @ApiResponse(responseCode = "200", description = "Device comment status updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PatchMapping("/{id}/comment")
    public ResponseEntity<DeviceResponseDTO> updateDeviceComment(
            @Parameter(description = "ID of the device") @PathVariable String id,
            @RequestBody DeviceCommentPatchDTO patchDTO) {
        return deviceService.updateDeviceComment(id, patchDTO.getHasComment())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update device share status", description = "Updates only the share status of an existing device")
    @ApiResponse(responseCode = "200", description = "Device share status updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PatchMapping("/{id}/share")
    public ResponseEntity<DeviceResponseDTO> updateDeviceShare(
            @Parameter(description = "ID of the device") @PathVariable String id,
            @RequestBody DeviceSharePatchDTO patchDTO) {
        return deviceService.updateDeviceShare(id, patchDTO.getHasShare())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update device stream status", description = "Updates only the stream status of an existing device")
    @ApiResponse(responseCode = "200", description = "Device stream status updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceResponseDTO.class)))
    @ApiResponse(responseCode = "404", description = "Device not found")
    @PatchMapping("/{id}/stream")
    public ResponseEntity<DeviceResponseDTO> updateDeviceStream(
            @Parameter(description = "ID of the device") @PathVariable String id,
            @RequestBody DeviceStreamPatchDTO patchDTO) {
        return deviceService.updateDeviceStream(id, patchDTO.getHasStream())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stats")
    @Operation(summary = "Get device statistics", description = "Retrieves statistics about devices including counts of various states")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved device statistics", content = @Content(mediaType = "application/json", schema = @Schema(implementation = DeviceStatsDTO.class)))
    public DeviceStatsDTO getDeviceStats() {
        return deviceService.getDeviceStats();
    }

    @PostMapping("/reset")
    @Operation(summary = "Reset all devices", description = "Resets all devices to default values: status='idle', isActive=true, and all features disabled")
    @ApiResponse(responseCode = "200", description = "All devices reset successfully", content = @Content(mediaType = "application/json"))
    public ResponseEntity<String> resetAllDevices() {
        int count = deviceService.resetAllDevices();
        return ResponseEntity.ok("Successfully reset " + count + " devices");
    }
}