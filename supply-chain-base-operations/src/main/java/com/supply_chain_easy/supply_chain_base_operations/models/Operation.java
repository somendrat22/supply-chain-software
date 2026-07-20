package com.supply_chain_easy.supply_chain_base_operations.models;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operations")
public class Operation extends GlobalRecord{
    private String operationId;
    private String operationName;
    private String operationCategory;
}
