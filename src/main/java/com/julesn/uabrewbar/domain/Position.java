package com.julesn.uabrewbar.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@EqualsAndHashCode
public class Position {
    String name;
    int price;
}
