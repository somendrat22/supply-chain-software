package com.supply_chain_easy.supply_chain_base_operations.repositories;

import com.supply_chain_easy.supply_chain_base_operations.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OperationRepository extends JpaRepository<Operation, UUID> {

    public Operation findByOperationName(String name);

}
