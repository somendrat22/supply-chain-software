package com.supply_chain_easy.supply_chain_base_operations.schedulers;

import com.supply_chain_easy.supply_chain_base_operations.models.Operation;
import com.supply_chain_easy.supply_chain_base_operations.repositories.OperationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * It will have some auto triggers functions -> The functions which gets auto invoked
 * These kind of functions which gets auto invoked are called as ScheduleJobs or Jobs
 */
@Slf4j
@Component
public class SystemJobs {

    private OperationRepository operationRepository;

    @Autowired
    public SystemJobs(OperationRepository operationRepository){
        this.operationRepository = operationRepository;
    }

    /**
     * This function will get auto trigger in every 1 hour
     * Work of this function is to load operations in the operation table.
     */
    @Scheduled(initialDelay = 0, fixedRate = 60000)
    public void loadAllSystemOperations(){
        log.info("Load operations scheduled job got triggered.");
        List<Operation> operations = List.of(
                // PROCUREMENT
                // =======================
                Operation.builder().operationId("OP0001").operationName("CREATE_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0002").operationName("UPDATE_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0003").operationName("DELETE_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0004").operationName("VIEW_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0005").operationName("VIEW_ALL_PURCHASE_REQUISITIONS").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0006").operationName("SUBMIT_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0007").operationName("APPROVE_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0008").operationName("REJECT_PURCHASE_REQUISITION").operationCategory("PROCUREMENT").build(),

                Operation.builder().operationId("OP0009").operationName("CREATE_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0010").operationName("UPDATE_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0011").operationName("DELETE_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0012").operationName("VIEW_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0013").operationName("VIEW_ALL_PURCHASE_ORDERS").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0014").operationName("APPROVE_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0015").operationName("REJECT_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0016").operationName("CANCEL_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0017").operationName("CLOSE_PURCHASE_ORDER").operationCategory("PROCUREMENT").build(),

                Operation.builder().operationId("OP0018").operationName("CREATE_RFQ").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0019").operationName("UPDATE_RFQ").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0020").operationName("DELETE_RFQ").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0021").operationName("VIEW_RFQ").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0022").operationName("VIEW_ALL_RFQS").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0023").operationName("PUBLISH_RFQ").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0024").operationName("CLOSE_RFQ").operationCategory("PROCUREMENT").build(),

                Operation.builder().operationId("OP0025").operationName("CREATE_CONTRACT").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0026").operationName("UPDATE_CONTRACT").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0027").operationName("DELETE_CONTRACT").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0028").operationName("VIEW_CONTRACT").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0029").operationName("VIEW_ALL_CONTRACTS").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0030").operationName("APPROVE_CONTRACT").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0031").operationName("RENEW_CONTRACT").operationCategory("PROCUREMENT").build(),
                Operation.builder().operationId("OP0032").operationName("TERMINATE_CONTRACT").operationCategory("PROCUREMENT").build(),

                // =======================
                // SUPPLIER
                // =======================
                Operation.builder().operationId("OP0033").operationName("CREATE_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0034").operationName("UPDATE_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0035").operationName("DELETE_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0036").operationName("VIEW_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0037").operationName("VIEW_ALL_SUPPLIERS").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0038").operationName("APPROVE_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0039").operationName("REJECT_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0040").operationName("BLOCK_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0041").operationName("UNBLOCK_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0042").operationName("INVITE_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0043").operationName("EVALUATE_SUPPLIER").operationCategory("SUPPLIER").build(),
                Operation.builder().operationId("OP0044").operationName("AUDIT_SUPPLIER").operationCategory("SUPPLIER").build(),

                // =======================
                // INVENTORY
                // =======================
                Operation.builder().operationId("OP0045").operationName("CREATE_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0046").operationName("UPDATE_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0047").operationName("DELETE_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0048").operationName("VIEW_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0049").operationName("VIEW_ALL_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0050").operationName("TRANSFER_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0051").operationName("ADJUST_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0052").operationName("RESERVE_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0053").operationName("RELEASE_INVENTORY").operationCategory("INVENTORY").build(),
                Operation.builder().operationId("OP0054").operationName("COUNT_INVENTORY").operationCategory("INVENTORY").build(),

                // =======================
                // WAREHOUSE
                // =======================
                Operation.builder().operationId("OP0055").operationName("CREATE_GOODS_RECEIPT").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0056").operationName("UPDATE_GOODS_RECEIPT").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0057").operationName("VIEW_GOODS_RECEIPT").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0058").operationName("VIEW_ALL_GOODS_RECEIPTS").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0059").operationName("CREATE_GOODS_ISSUE").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0060").operationName("UPDATE_GOODS_ISSUE").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0061").operationName("VIEW_GOODS_ISSUE").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0062").operationName("VIEW_ALL_GOODS_ISSUES").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0063").operationName("CREATE_WAREHOUSE_TRANSFER").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0064").operationName("VIEW_WAREHOUSE_TRANSFER").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0065").operationName("VIEW_ALL_WAREHOUSE_TRANSFERS").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0066").operationName("PICK_ITEMS").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0067").operationName("PACK_ITEMS").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0068").operationName("SHIP_ITEMS").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0069").operationName("RECEIVE_ITEMS").operationCategory("WAREHOUSE").build(),
                Operation.builder().operationId("OP0070").operationName("CREATE_ROLE").operationCategory("SYSTEM").build(),
                Operation.builder().operationId("OP0070").operationName("VIEW_ALL_OPERATIONS").operationCategory("SYSTEM").build()
        );

        log.info("Saving operations in DB");
        for(Operation operation : operations){
            if(operationRepository.findByOperationName(operation.getOperationName()) == null){
                operationRepository.save(operation);
            }
        }
        log.info("Operations saved successfully");
    }

}
