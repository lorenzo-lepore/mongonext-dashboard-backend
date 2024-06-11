package org.lorenzolepore.springbackend.model;

import java.time.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AggregationResult {
    private String id;
    @Field("customer_id")
    private String customerId;
    private int amount;
    private LocalDateTime date;
    private String status;
    private Customer[] customer;

    public AggregationResult(String customerId, int amount, String status, Customer[] customer) {
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.date = LocalDateTime.now().plusHours(2);
        this.customer = customer;
    }
}
