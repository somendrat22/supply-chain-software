package com.supply_chain_easy.sourcing_and_procurement_operations.controllers;

import com.supply_chain_easy.sourcing_and_procurement_operations.services.EmailService;
import com.supply_chain_easy.supply_chain_base_operations.dtos.LoginRequestDto;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.services.AuthService;
import com.supply_chain_easy.supply_chain_base_operations.services.EmployeeService;
import com.supply_chain_easy.supply_chain_base_operations.utilities.JwtUtility;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/spo/api/v1/emp")
public class EmployeeController {

    private AuthService authService;
    private JwtUtility jwtUtility;
    private EmployeeService employeeService;

    @Autowired
    public EmployeeController(AuthService authService,
                              JwtUtility jwtUtility,
                              EmployeeService employeeService){
        this.authService = authService;
        this.jwtUtility = jwtUtility;
        this.employeeService = employeeService;
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

    // http://localhost:8080/spo/api/v1/emp/view-profile ? Header -> Token
    @GetMapping("/view-profile")
    public ResponseEntity fetchUserDetails(
            @RequestHeader String token
    ){
        Claims claims = jwtUtility.extractAllClaims(token);
        String email = claims.get("email", String.class);
        Employee employee = employeeService.fetchEmployeeByWorkEmail(email);
        return new ResponseEntity(employee, HttpStatus.OK);
    }

}
