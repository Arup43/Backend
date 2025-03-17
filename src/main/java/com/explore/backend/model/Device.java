package com.explore.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "devices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String status;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "has_like")
    private Boolean hasLike;

    @Column(name = "has_comment")
    private Boolean hasComment;

    @Column(name = "has_subscribe")
    private Boolean hasSubscribe;

    @Column(name = "has_stream")
    private Boolean hasStream;
}