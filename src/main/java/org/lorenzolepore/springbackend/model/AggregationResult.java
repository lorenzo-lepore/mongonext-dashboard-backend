package org.lorenzolepore.springbackend.model;

import java.time.*;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
