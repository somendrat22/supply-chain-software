package com.supply_chain_easy.sourcing_and_procurement_operations.controllers;

import com.supply_chain_easy.sourcing_and_procurement_operations.dtos.ProcurementCompanyRegistrationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("/api/v1/procurement-company")
public class ProcurementCompanyController {

    @PostMapping("/on-board")
    public ResponseEntity onBoardProcurementCompany(
            @RequestBody ProcurementCompanyRegistrationDto procurementCompanyRegistrationDto
            ){
        // Service layer
    }

}
