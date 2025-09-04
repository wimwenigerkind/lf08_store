package group.wimdev.lf08_store.supplier;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddSupplierDTO {
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @Size(max = 50, message = "Street must not exceed 50 characters")
    private String street;

    @NotBlank(message = "Postcode is mandatory")
    @Size(max = 5, message = "Postcode must have 5 characters")
    private String postcode;

    @NotBlank(message = "City is mandatory")
    @Size(max = 50, message = "City must not exceed 50 characters")
    private String city;

    private String phone;
}
