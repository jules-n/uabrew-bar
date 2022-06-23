package com.julesn.uabrewbar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

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
    private Long number;
    private Status status;
    private String place;
    private String bar;
    @Nullable
    private String client;
    private Map<Position, Integer> positions;
    float toPay;
}
