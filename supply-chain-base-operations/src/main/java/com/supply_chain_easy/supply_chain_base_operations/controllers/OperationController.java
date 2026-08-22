package com.supply_chain_easy.supply_chain_base_operations.controllers;

import com.supply_chain_easy.supply_chain_base_operations.models.Operation;
import com.supply_chain_easy.supply_chain_base_operations.models.User;
import com.supply_chain_easy.supply_chain_base_operations.services.AuthService;
import com.supply_chain_easy.supply_chain_base_operations.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/base/api/v1/operation")
public class OperationController {

    private AuthService authService;
    private OperationService operationService;

    @Autowired
    public OperationController(AuthService authService,
                               OperationService operationService){
        this.authService = authService;
        this.operationService = operationService;
    }

    @GetMapping("/get/all")
    public ResponseEntity fetchAllOperations(
            @RequestHeader String token
    ){
        authService.isUserAuthorizedToPerformOperation(token, "VIEW_ALL_OPERATIONS");
        List<Operation> operations =  operationService.fetchAllOperations();
        return new ResponseEntity(operations, HttpStatus.OK);
    }
}
