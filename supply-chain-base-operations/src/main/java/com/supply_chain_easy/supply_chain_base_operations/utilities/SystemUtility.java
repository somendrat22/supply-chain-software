package com.supply_chain_easy.supply_chain_base_operations.utilities;

import java.util.concurrent.atomic.AtomicLong;

public class SystemUtility {

    private static final AtomicLong COUNTER = new AtomicLong(1);

    public static String generateId(String entityName) {

        return entityName.toUpperCase().replace(" ", "_")
                + "-"
                + String.format("%06d", COUNTER.getAndIncrement());
    }
}
