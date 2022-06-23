package com.julesn.uabrewbar.dtos;

import com.julesn.uabrewbar.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Order {
    private Status status;
    private String place;
    private String bar;
    private Set<String> rights;
    private Map<String, Integer> positions;
}
