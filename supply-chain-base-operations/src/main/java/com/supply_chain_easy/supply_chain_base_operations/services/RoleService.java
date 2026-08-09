package com.supply_chain_easy.supply_chain_base_operations.services;

import com.supply_chain_easy.supply_chain_base_operations.dtos.CreateRoleRequestDto;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.models.Operation;
import com.supply_chain_easy.supply_chain_base_operations.models.Role;
import com.supply_chain_easy.supply_chain_base_operations.repositories.RoleRepository;
import com.supply_chain_easy.supply_chain_base_operations.utilities.SystemUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RoleService {

    private OperationService operationService;
    private RoleRepository roleRepository;


    @Autowired
    public RoleService(OperationService operationService,
                       RoleRepository roleRepository){
        this.operationService = operationService;
        this.roleRepository = roleRepository;
    }

    public Role createAdminRole(String companyName){
        // TCS-MAINT
        // Admin can perform all the operations in our application.
        // So, The role object which we will create -> It will have access to all the operations
        // So, To create role object we want list of all operations
        // So, to get list of all operations we need to call operation service
        List<Operation> operations = operationService.fetchAllOperations();
        Role adminRole = Role.builder()
                .roleId(SystemUtility.generateId("ROLE"))
                .roleName(companyName + "-" + "MAINT")
                .createdAt(LocalDateTime.now())
                .operations(operations)
                .updatedAt(LocalDateTime.now())
                .createdBy("system")
                .updatedBy("system")
                .build();
        // Save this role to Database
        return roleRepository.save(adminRole);
    }

    public Role createRole(
            CreateRoleRequestDto createRoleRequestDto,
            Employee roleCreatorUser
    ){
        // We are getting operationNames so we need to convert operationNames to operation objects
        List<Operation> operations = operationService.fetchAllOperationsByName(createRoleRequestDto.getOperationNames());
        Role role = Role.builder()
                .roleId(SystemUtility.generateId("ROLE"))
                .roleName(roleCreatorUser.getCompany().getLegalName() + "-" + createRoleRequestDto.getRoleName())
                .createdAt(LocalDateTime.now())
                .operations(operations)
                .updatedAt(LocalDateTime.now())
                .createdBy(roleCreatorUser.getWorkEmail())
                .updatedBy(roleCreatorUser.getWorkEmail())
                .build();
        return role;
    }
}
