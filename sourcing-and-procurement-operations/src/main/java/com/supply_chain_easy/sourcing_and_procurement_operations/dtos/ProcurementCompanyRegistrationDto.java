package com.supply_chain_easy.sourcing_and_procurement_operations.dtos;

import com.supply_chain_easy.supply_chain_base_operations.enums.CompanyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcurementCompanyRegistrationDto {

    // =========================
    // Procurement Information
    // =========================
    private String procurementCompanyCode;

    private String procurementModel;

    private String procurementStrategy;

    private BigDecimal annualProcurementBudget;

    private BigDecimal annualProcurementSpend;

    private String defaultCurrency;

    private Boolean budgetControlEnabled;

    private Boolean purchaseRequisitionRequired;

    private Boolean purchaseOrderRequired;

    private Boolean sourcingRequired;

    private Boolean contractRequired;

    private Boolean supplierApprovalRequired;

    private Boolean multiLevelApprovalEnabled;

    private Boolean threeWayMatchingEnabled;

    // =========================
    // Company Information
    // =========================
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

    // =========================
    // Contact Information
    // =========================
    private String email;

    private String phoneNumber;

    private String registeredAddress;

    // =========================
    // Tax & Legal Information
    // =========================
    private String taxIdentificationNumber;

    private String vatNumber;

    private String gstNumber;
}
