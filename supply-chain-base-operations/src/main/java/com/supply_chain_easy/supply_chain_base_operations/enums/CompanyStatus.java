package com.supply_chain_easy.supply_chain_base_operations.enums;

public enum CompanyStatus {

    // Company is being created but registration is incomplete
    DRAFT,

    // Company has submitted registration/onboarding details
    PENDING_VERIFICATION,

    // Company documents and information are being reviewed
    UNDER_REVIEW,

    // Company is approved and can use the platform
    ACTIVE,

    // Company is temporarily disabled
    SUSPENDED,

    // Company is no longer active on the platform
    INACTIVE,

    // Company registration was rejected
    REJECTED,

    // Company permanently closed or no longer exists
    CLOSED
}
