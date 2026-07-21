package com.supply_chain_easy.sourcing_and_procurement_operations;

import com.supply_chain_easy.supply_chain_base_operations.models.GlobalRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class SourcingAndProcurementOperationsApplication {

	public static void main(String[] args) {
        GlobalRecord globalRecord = new GlobalRecord();
		SpringApplication.run(SourcingAndProcurementOperationsApplication.class, args);
	}

}
