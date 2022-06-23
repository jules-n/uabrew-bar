package com.julesn.uabrewbar.dtos;

import com.julesn.uabrewbar.domain.Position;
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
public class CreateOrderDTO {
    private String place;
    @Nullable
    private String client;
    private Map<Position, Integer> positions;
}
