package com.supply_chain_easy.sourcing_and_procurement_operations.controllers;

import com.supply_chain_easy.supply_chain_base_operations.dtos.LoginRequestDto;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/spo/api/v1/emp")
public class EmployeeController {

    private AuthService authService;

    @Autowired
    public EmployeeController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity login(
            @RequestBody LoginRequestDto loginRequestDto
            ){
        String token =  authService.authenticateUser(loginRequestDto.getEmail(), loginRequestDto.getPassword());
        HashMap<String, String> resp = new HashMap<>();
        resp.put("token", token);
        return new ResponseEntity(resp, HttpStatus.OK);
    }

}
