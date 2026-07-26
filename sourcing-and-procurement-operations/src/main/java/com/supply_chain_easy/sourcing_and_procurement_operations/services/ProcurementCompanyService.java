package com.supply_chain_easy.sourcing_and_procurement_operations.services;


import com.supply_chain_easy.sourcing_and_procurement_operations.dtos.ProcurementCompanyRegistrationDto;
import com.supply_chain_easy.sourcing_and_procurement_operations.transformers.CompanyTransformer;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.models.ProcurementCompany;
import com.supply_chain_easy.supply_chain_base_operations.repositories.ProcurementCompanyRepository;
import com.supply_chain_easy.supply_chain_base_operations.services.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class ProcurementCompanyService {

    private CompanyTransformer companyTransformer;
    private ProcurementCompanyRepository procurementCompanyRepository;
    private CompanyService companyService;
    private EmailService emailService;
    private ExecutorService executor;

    @Autowired
    public ProcurementCompanyService(CompanyTransformer companyTransformer,
                                     ProcurementCompanyRepository procurementCompanyRepository,
                                     CompanyService companyService,
                                     EmailService emailService,
                                     ExecutorService executorService){
        this.companyTransformer = companyTransformer;
        this.companyService = companyService;
        this.emailService = emailService;
        this.procurementCompanyRepository = procurementCompanyRepository;
        this.executor = executorService;
    }

    public ProcurementCompany onBoardProcurementCompany(ProcurementCompanyRegistrationDto procurementCompanyRegistrationDto){
        // 1. Map all the details to the ProcurementCompanyModel
        // Mapping logic at some different place ?
        ProcurementCompany procurementCompany = companyTransformer.transformProcurementCompanyDtoToModel(procurementCompanyRegistrationDto);
        // Save this object in the table
        procurementCompany = procurementCompanyRepository.save(procurementCompany);
        Employee adminEmployee = companyService.createAdminUserForCompany(procurementCompany);
        // Create Admin Role for this company and after creating admin role we need to create admin user for the company
        // After creation of the employee we should send the email
        // EmailService -> EmailService
        ProcurementCompany finalProcurementCompany = procurementCompany;
        executor.execute( () -> {
            emailService.sendRegistrationEmailToProcurementCompany(
                    finalProcurementCompany,
                    adminEmployee
            );
        });
        return procurementCompany;
    }

}
