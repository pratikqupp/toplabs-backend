package com.TopLabBazaar2909.TLBnew2909.model;

import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

public class BaseAuditableDocument {
    @Version
    private Long version;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModified;

    @CreatedBy
    private String createdBy;

    @LastModifiedBy
    private String lastModifiedBy;
}
