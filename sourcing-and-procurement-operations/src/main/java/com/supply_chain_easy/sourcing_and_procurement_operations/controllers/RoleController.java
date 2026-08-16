package com.supply_chain_easy.sourcing_and_procurement_operations.controllers;

import com.supply_chain_easy.supply_chain_base_operations.dtos.CreateRoleRequestDto;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.models.Role;
import com.supply_chain_easy.supply_chain_base_operations.services.AuthService;
import com.supply_chain_easy.supply_chain_base_operations.services.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/spo/api/v1/role")
public class RoleController {

    private AuthService authService;
    private RoleService roleService;

    @Autowired
    public RoleController(AuthService authService,
                          RoleService roleService){
        this.authService = authService;
        this.roleService = roleService;
    }

    /**
     * In method params we want to read 2 things
     * 1. RequestBody - roleName and List<String> operationNames
     * 2. RequestHeader - token
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity createRole(
            @RequestBody CreateRoleRequestDto createRoleRequestDto,
            @RequestHeader String token

    ){
        log.info("Inside RoleController#createRole");
        Employee roleCreatorUser = authService.isUserAuthorizedToPerformOperation(token, "CREATE_ROLE");
        // RoleService to create role
        Role role = roleService.createRole(createRoleRequestDto,
                roleCreatorUser);
        return new ResponseEntity(role, HttpStatus.CREATED);
    }


}
