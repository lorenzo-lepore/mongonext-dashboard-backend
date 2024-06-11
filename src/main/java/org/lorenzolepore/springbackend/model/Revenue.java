package org.lorenzolepore.springbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "revenues")
public class Revenue {
    /*@Id
    private String id;*/
    private String month;
    private int amount;
}
