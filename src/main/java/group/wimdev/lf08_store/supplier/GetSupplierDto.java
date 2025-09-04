package group.wimdev.lf08_store.supplier;

import lombok.Data;

@Data
public class GetSupplierDto {
    private Long id;
    private String name;
    private String street;
    private String postcode;
    private String city;
    private String phone;
}
