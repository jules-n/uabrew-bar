package com.julesn.uabrewbar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = Order.COLLECTION)
public class Order {
    public static final String COLLECTION = "orders";
    private String _id;
    @Indexed(unique = true)
    private int number;
    private Status status;
    private String place;
    private String bar;
    private Set<String> rights;
    private Map<String, Integer> positions;
}
