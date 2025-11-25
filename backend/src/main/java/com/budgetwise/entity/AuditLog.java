package com.budgetwise.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_user_id")
    private Long adminUserId;

    @Column(name = "action_type")
    private String actionType;

    @Column(name = "target_user_id")
    private Long targetUserId;

    @Column(name = "target_resource")
    private String targetResource;

    @Column(columnDefinition = "TEXT")
    private String details;

    @Column(name = "ip_address")
    private String ipAddress;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public AuditLog() {
    }

    public AuditLog(Long id, Long adminUserId, String actionType, Long targetUserId, String targetResource,
            String details, String ipAddress, LocalDateTime createdAt) {
        this.id = id;
        this.adminUserId = adminUserId;
        this.actionType = actionType;
        this.targetUserId = targetUserId;
        this.targetResource = targetResource;
        this.details = details;
        this.ipAddress = ipAddress;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Long adminUserId) {
        this.adminUserId = adminUserId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getTargetResource() {
        return targetResource;
    }

    public void setTargetResource(String targetResource) {
        this.targetResource = targetResource;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public static AuditLogBuilder builder() {
        return new AuditLogBuilder();
    }

    public static class AuditLogBuilder {
        private Long id;
        private Long adminUserId;
        private String actionType;
        private Long targetUserId;
        private String targetResource;
        private String details;
        private String ipAddress;
        private LocalDateTime createdAt;

        public AuditLogBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public AuditLogBuilder adminUserId(Long adminUserId) {
            this.adminUserId = adminUserId;
            return this;
        }

        public AuditLogBuilder actionType(String actionType) {
            this.actionType = actionType;
            return this;
        }

        public AuditLogBuilder targetUserId(Long targetUserId) {
            this.targetUserId = targetUserId;
            return this;
        }

        public AuditLogBuilder targetResource(String targetResource) {
            this.targetResource = targetResource;
            return this;
        }

        public AuditLogBuilder details(String details) {
            this.details = details;
            return this;
        }

        public AuditLogBuilder ipAddress(String ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public AuditLogBuilder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AuditLog build() {
            return new AuditLog(id, adminUserId, actionType, targetUserId, targetResource, details, ipAddress,
                    createdAt);
        }
    }
}
