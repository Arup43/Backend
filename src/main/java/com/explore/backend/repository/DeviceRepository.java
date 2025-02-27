package com.explore.backend.repository;

import com.explore.backend.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String> {
    long countByStatus(String status);
    long countByHasLikeTrue();
    long countByHasCommentTrue();
    long countByHasShareTrue();
    long countByHasStreamTrue();
}