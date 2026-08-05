package com.supply_chain_easy.supply_chain_base_operations.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleRequestDto {
    private String roleName;
    private List<String> operationNames;
}
