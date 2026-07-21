package com.supply_chain_easy.sourcing_and_procurement_operations.transformers;

import com.supply_chain_easy.sourcing_and_procurement_operations.dtos.ProcurementCompanyRegistrationDto;
import com.supply_chain_easy.supply_chain_base_operations.enums.CompanyStatus;
import com.supply_chain_easy.supply_chain_base_operations.models.ProcurementCompany;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CompanyTransformer {

    public ProcurementCompany transformProcurementCompanyDtoToModel(ProcurementCompanyRegistrationDto dto){
        // ProcurementCompanyRegistrationDto to ProcurementCompany Model object
        return ProcurementCompany.builder()

                // =========================
                // Company Information
                // =========================
                .legalName(dto.getLegalName())
                .displayName(dto.getDisplayName())
                .companyCode(dto.getCompanyCode())
                .registrationNumber(dto.getRegistrationNumber())
                .companyType(dto.getCompanyType())

                // =========================
                // Business Information
                // =========================
                .industry(dto.getIndustry())
                .businessDescription(dto.getBusinessDescription())
                .website(dto.getWebsite())

                // =========================
                // Contact Information
                // =========================
                .email(dto.getEmail())
                .phoneNumber(dto.getPhoneNumber())
                .registeredAddress(dto.getRegisteredAddress())

                // =========================
                // Tax & Legal Information
                // =========================
                .taxIdentificationNumber(dto.getTaxIdentificationNumber())
                .vatNumber(dto.getVatNumber())
                .gstNumber(dto.getGstNumber())

                // =========================
                // Procurement Information
                // =========================
                .procurementCompanyCode(dto.getProcurementCompanyCode())
                .procurementModel(dto.getProcurementModel())
                .procurementStrategy(dto.getProcurementStrategy())
                .annualProcurementBudget(dto.getAnnualProcurementBudget())
                .annualProcurementSpend(dto.getAnnualProcurementSpend())
                .defaultCurrency(dto.getDefaultCurrency())
                .budgetControlEnabled(dto.getBudgetControlEnabled())
                .purchaseRequisitionRequired(dto.getPurchaseRequisitionRequired())
                .purchaseOrderRequired(dto.getPurchaseOrderRequired())
                .sourcingRequired(dto.getSourcingRequired())
                .contractRequired(dto.getContractRequired())
                .supplierApprovalRequired(dto.getSupplierApprovalRequired())
                .multiLevelApprovalEnabled(dto.getMultiLevelApprovalEnabled())
                .threeWayMatchingEnabled(dto.getThreeWayMatchingEnabled())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .updatedBy("system")
                .companyId("COMP-001")
                // Default values
                .status(CompanyStatus.UNDER_REVIEW)
                .active(false)
                .build();
    }

}
