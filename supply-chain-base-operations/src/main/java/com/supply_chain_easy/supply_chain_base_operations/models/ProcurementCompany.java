package com.supply_chain_easy.supply_chain_base_operations.models;

import java.math.BigDecimal;

public class ProcurementCompany extends Company {
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
}
