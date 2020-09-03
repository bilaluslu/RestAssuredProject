package POJO;

// TODO Country  POJO class that have same encapsulated field as Countries table

import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class Countries {

    private String country_id;
    private String country_name;
    private int region_id;
}
