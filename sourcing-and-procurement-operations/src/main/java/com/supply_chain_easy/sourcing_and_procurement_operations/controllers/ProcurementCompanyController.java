package com.supply_chain_easy.sourcing_and_procurement_operations.controllers;

import com.supply_chain_easy.sourcing_and_procurement_operations.dtos.ProcurementCompanyRegistrationDto;
import com.supply_chain_easy.sourcing_and_procurement_operations.services.ProcurementCompanyService;
import com.supply_chain_easy.supply_chain_base_operations.models.ProcurementCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/spo/api/v1/procurement-company")
public class ProcurementCompanyController {

    private ProcurementCompanyService procurementCompanyService;

    @Autowired
    public ProcurementCompanyController(ProcurementCompanyService procurementCompanyService){
        this.procurementCompanyService = procurementCompanyService;
    }

    @PostMapping("/on-board")
    public ResponseEntity onBoardProcurementCompany(
            @RequestBody ProcurementCompanyRegistrationDto procurementCompanyRegistrationDto
            ){
        // Service layer

        ProcurementCompany procurementCompany = procurementCompanyService.onBoardProcurementCompany(procurementCompanyRegistrationDto);
        return new ResponseEntity(procurementCompany, HttpStatus.CREATED);
    }

}
