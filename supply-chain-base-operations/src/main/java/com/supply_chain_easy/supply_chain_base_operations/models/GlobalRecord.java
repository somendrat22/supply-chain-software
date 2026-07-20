package com.supply_chain_easy.supply_chain_base_operations.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "global_records")
public class GlobalRecord {
    @Id
    private UUID sysId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @OneToMany
    private List<Attachment> attachments;
    @OneToMany
    private List<Activity> activities;
    private String createdBy;
    private String updatedBy;
}
