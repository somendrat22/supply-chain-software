package com.supply_chain_easy.supply_chain_base_operations.services;

import com.supply_chain_easy.supply_chain_base_operations.exceptions.InvalidCredentialsException;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.utilities.JwtUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
