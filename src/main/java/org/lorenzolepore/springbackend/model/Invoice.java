package org.lorenzolepore.springbackend.model;

import java.time.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import org.bson.types.ObjectId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invoices")
public class Invoice {
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId id;
    @Field("customer_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private ObjectId customerId;
    private int amount;
    private LocalDateTime date;
    private String status;

    public Invoice(ObjectId customerId, int amount, String status) {
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.date = LocalDateTime.now().plusHours(2);
    }
}
