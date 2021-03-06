package com.julesn.uabrewbar.dtos;

import com.julesn.uabrewbar.domain.Position;
import com.julesn.uabrewbar.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    private Long number;
    private Status status;
    private String place;
    private String bar;
    @Nullable
    private String client;
    private Map<Position, Integer> positions;
    private float toPay;
}
