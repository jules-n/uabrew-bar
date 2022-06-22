package com.julesn.uabrewbar.domain;
  
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.Nullable;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = Bar.collection)
public class Bar {
    public static final String collection = "bars";
    private static final String REGEX = "[A-Za-z]+";
  
    @Indexed(unique = true)
    private String name;
    private String country;
    private Set<String> workers;
    private Set<String> places;
  
    @SneakyThrows
    public void setCountry(String country) {
      boolean isValid = country.matches(REGEX);
      if (!isValid) {
        throw new Exception("Only latin");
      }
      if (country.toLowerCase(Locale.ROOT).equals("russia") 
          || country.toLowerCase(Locale.ROOT).equals("ruzzia") 
          || country.toLowerCase(Locale.ROOT).equals("kazaptstan")) {
        throw new Exception("Go fuck yourself, pidori");
      }
    }
  this.country = country;
  log.info("Welcom, friens from "+country);
}
