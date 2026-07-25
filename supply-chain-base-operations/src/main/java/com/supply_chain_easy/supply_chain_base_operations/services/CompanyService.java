package com.supply_chain_easy.supply_chain_base_operations.services;

import com.supply_chain_easy.supply_chain_base_operations.models.Company;
import com.supply_chain_easy.supply_chain_base_operations.models.Employee;
import com.supply_chain_easy.supply_chain_base_operations.models.Role;
import com.supply_chain_easy.supply_chain_base_operations.models.User;
import com.supply_chain_easy.supply_chain_base_operations.transformers.SystemTransformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CompanyService {

    public RoleService roleService;
    public EmployeeService employeeService;

    public CompanyService(RoleService roleService,
                          EmployeeService employeeService){
        this.roleService = roleService;
        this.employeeService = employeeService;
    }

    /**
     * Work of this function is to create admin user for the company.
     * @param company
     * @return
     */
    public Employee createAdminUserForCompany(Company company){
        // Before creating admin user we should create admin for the role for the company
        // Creation of admin role -> Common for all our project lets keep it in Common pojetc
        Role adminRole = roleService.createAdminRole(company.getLegalName());
        // Admin Employee -> Admin user
        // Admin Employee -> First Employee of our application.
        Employee adminEmployee = SystemTransformer.mapCompanyToAdminEmployee(company, adminRole);
        // We need to call EmployeeService -> To save employee in database.
        return employeeService.save(adminEmployee);
    }

}
