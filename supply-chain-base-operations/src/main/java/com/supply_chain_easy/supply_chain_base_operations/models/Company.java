package com.supply_chain_easy.supply_chain_base_operations.models;

import com.supply_chain_easy.supply_chain_base_operations.enums.CompanyStatus;
import com.supply_chain_easy.supply_chain_base_operations.enums.CompanyType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "companies")
public class Company extends GlobalRecord {

    private String companyId;

    private String legalName;

    private String displayName;

    private String companyCode;

    private String registrationNumber;

    private CompanyType companyType;

    // =========================
    // Business Information
    // =========================

    private String industry;

    private String businessDescription;

    private String website;

    private CompanyStatus status;

    // =========================
    // Contact Information
    // =========================

    private String email;

    private String phoneNumber;

    private String registeredAddress;

    // =========================
    // Tax & Legal
    // =========================

    private String taxIdentificationNumber;

    private String vatNumber;

    private String gstNumber;

    private Boolean active;
}
