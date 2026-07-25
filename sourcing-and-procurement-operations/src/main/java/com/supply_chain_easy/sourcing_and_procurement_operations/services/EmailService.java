package com.supply_chain_easy.sourcing_and_procurement_operations.services;

import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.models.ProcurementCompany;
import com.supply_chain_easy.supply_chain_base_operations.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    private TemplateEngine templateEngine;
    private NotificationService notificationService;

    @Autowired
    public EmailService(TemplateEngine templateEngine,
                        NotificationService notificationService){
        this.templateEngine = templateEngine;
        this.notificationService = notificationService;
    }

    public void sendRegistrationEmailToProcurementCompany(ProcurementCompany procurementCompany, Employee admin){
        // Registration Details
        Context context = new Context();
        context.setVariable("registrationId", procurementCompany.getSysId());
        context.setVariable("registrationDate", procurementCompany.getCreatedAt());
        context.setVariable("reviewUrl", "http://www.google.com");

        // Company Information
        context.setVariable("legalName", procurementCompany.getLegalName());
        context.setVariable("displayName", procurementCompany.getDisplayName());
        context.setVariable("companyCode", procurementCompany.getCompanyCode());
        context.setVariable("registrationNumber", procurementCompany.getRegistrationNumber());
        context.setVariable("procurementCompanyCode", procurementCompany.getProcurementCompanyCode());
        context.setVariable("companyType", procurementCompany.getCompanyType());
        context.setVariable("industry", procurementCompany.getIndustry());
        context.setVariable("businessDescription", procurementCompany.getBusinessDescription());
        context.setVariable("website", procurementCompany.getWebsite());

        // Procurement Information
        context.setVariable("procurementModel", procurementCompany.getProcurementModel());
        context.setVariable("procurementStrategy", procurementCompany.getProcurementStrategy());
        context.setVariable("annualProcurementBudget", procurementCompany.getAnnualProcurementBudget());
        context.setVariable("annualProcurementSpend", procurementCompany.getAnnualProcurementSpend());
        context.setVariable("defaultCurrency", procurementCompany.getDefaultCurrency());

        // Contact Information
        context.setVariable("email", procurementCompany.getEmail());
        context.setVariable("phoneNumber", procurementCompany.getPhoneNumber());
        context.setVariable("registeredAddress", procurementCompany.getRegisteredAddress());

        // Tax Information
        context.setVariable("gstNumber", procurementCompany.getGstNumber());
        context.setVariable("taxIdentificationNumber", procurementCompany.getTaxIdentificationNumber());
        context.setVariable("vatNumber", procurementCompany.getVatNumber());

        // Configuration
        context.setVariable("budgetControlEnabled", procurementCompany.getBudgetControlEnabled());
        context.setVariable("purchaseRequisitionRequired", procurementCompany.getPurchaseRequisitionRequired());
        context.setVariable("purchaseOrderRequired", procurementCompany.getPurchaseOrderRequired());
        context.setVariable("sourcingRequired", procurementCompany.getSourcingRequired());
        context.setVariable("contractRequired", procurementCompany.getContractRequired());
        context.setVariable("supplierApprovalRequired", procurementCompany.getSupplierApprovalRequired());
        context.setVariable("multiLevelApprovalEnabled", procurementCompany.getMultiLevelApprovalEnabled());
        context.setVariable("threeWayMatchingEnabled", procurementCompany.getThreeWayMatchingEnabled());

        // I need to get html Content populated with the variable values
        String htmlContent = templateEngine.process("procurement-compnay-registration", context);
        notificationService.sendEmailNotification(htmlContent, admin.getWorkEmail(), "Welcome to Sourcing & Procurement Operations");
    }

}
