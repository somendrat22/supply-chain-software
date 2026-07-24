package com.supply_chain_easy.sourcing_and_procurement_operations.services;


import com.supply_chain_easy.sourcing_and_procurement_operations.dtos.ProcurementCompanyRegistrationDto;
import com.supply_chain_easy.sourcing_and_procurement_operations.transformers.CompanyTransformer;
import com.supply_chain_easy.supply_chain_base_operations.models.ProcurementCompany;
import com.supply_chain_easy.supply_chain_base_operations.repositories.ProcurementCompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcurementCompanyService {

    private CompanyTransformer companyTransformer;
    private ProcurementCompanyRepository procurementCompanyRepository;

    @Autowired
    public ProcurementCompanyService(CompanyTransformer companyTransformer,
                                     ProcurementCompanyRepository procurementCompanyRepository){
        this.companyTransformer = companyTransformer;
        this.procurementCompanyRepository = procurementCompanyRepository;
    }

    public ProcurementCompany onBoardProcurementCompany(ProcurementCompanyRegistrationDto procurementCompanyRegistrationDto){
        // 1. Map all the details to the ProcurementCompanyModel
        // Mapping logic at some different place ?
        ProcurementCompany procurementCompany = companyTransformer.transformProcurementCompanyDtoToModel(procurementCompanyRegistrationDto);
        // Save this object in the table
        procurementCompany = procurementCompanyRepository.save(procurementCompany);

        // Create Admin Role for this company and after creating admin role we need to create admin user for the company
        return procurementCompany;
    }

}
