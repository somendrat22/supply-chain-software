package com.supply_chain_easy.supply_chain_base_operations.services;

import com.supply_chain_easy.supply_chain_base_operations.models.Operation;
import com.supply_chain_easy.supply_chain_base_operations.repositories.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OperationService {

    private OperationRepository operationRepository;

    @Autowired
    public OperationService(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    public List<Operation> fetchAllOperations(){
        return operationRepository.findAll();
    }

    public List<Operation> fetchAllOperationsByName(List<String> operationNames){
        List<Operation> operations = new ArrayList<>();
        for(String oprName : operationNames){
            Operation operation = operationRepository.findByOperationName(oprName);
            if(operation == null){
                log.warn("Invalid operation name passed : {}", oprName);
                continue;
            }
            operations.add(operation);
        }
        return operations;
    }
}
