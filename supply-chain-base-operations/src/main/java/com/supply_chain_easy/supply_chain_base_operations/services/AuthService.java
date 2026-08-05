package com.supply_chain_easy.supply_chain_base_operations.services;

import com.supply_chain_easy.supply_chain_base_operations.exceptions.InvalidCredentialsException;
import com.supply_chain_easy.supply_chain_base_operations.exceptions.UnAuthorizedException;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.models.Operation;
import com.supply_chain_easy.supply_chain_base_operations.models.Role;
import com.supply_chain_easy.supply_chain_base_operations.models.User;
import com.supply_chain_easy.supply_chain_base_operations.utilities.JwtUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private EmployeeService employeeService;
    private JwtUtility jwtUtility;

    @Autowired
    public AuthService(EmployeeService employeeService,
                       JwtUtility jwtUtility){
        this.employeeService = employeeService;
        this.jwtUtility = jwtUtility;
    }

    public String authenticateUser(String email,
                                     String password){
        // I want to check email exist or not
        // Employee Service -> Find employee by WorkEmail
        Employee employee = employeeService.fetchEmployeeByWorkEmail(email);
        if(employee == null || !employee.getPassword().equals(password)){
            throw new InvalidCredentialsException("Wrong email or password entered");
        }
        return jwtUtility.generateJwtToken(employee);
    }

    public Employee isUserAuthorizedToPerformOperation(
            String userToken,
            String operationName
    ){
        // 1. Decrypt token of the user - Decrpt token and extract the claims
        Claims claims = jwtUtility.extractAllClaims(userToken);
        String email = claims.get("email", String.class);
        Employee employee = employeeService.fetchEmployeeByWorkEmail(email);
        List<Role> roles = employee.getRoles();
        // Is any role containing the operation which user is trying to perform
        for(Role role: roles){
            if(isOperationPresentInRole(role, operationName)){
                return employee;
            }
        }
        throw new UnAuthorizedException("User is not allowed to perform operation :" + operationName);
    }

    private boolean isOperationPresentInRole(Role role, String operationName){
        List<Operation> operations = role.getOperations();
        for(Operation operation : operations){
            if(operation.getOperationName().equals(operationName)){
                return true;
            }
        }
        return false;
    }


}
