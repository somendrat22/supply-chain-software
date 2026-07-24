package com.supply_chain_easy.sourcing_and_procurement_operations;

import com.supply_chain_easy.supply_chain_base_operations.models.GlobalRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ComponentScan({
        "com.supply_chain_easy.sourcing_and_procurement_operations",
        "com.supply_chain_easy.supply_chain_base_operations"
})
@EnableScheduling
@SpringBootApplication
public class SourcingAndProcurementOperationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SourcingAndProcurementOperationsApplication.class, args);
	}

}
