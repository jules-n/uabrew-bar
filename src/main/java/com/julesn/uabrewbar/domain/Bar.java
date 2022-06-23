package com.julesn.uabrewbar.domain;
  
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Bar.collection)
public class Bar {
    public static final String collection = "bars";

    @Indexed(unique = true)
    private String name;
    private String country;
    private Set<String> workers;
    private Set<String> places;
}
