package group.wimdev.lf08_store.article;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddArticleDto {
    @NotBlank(message = "Designation is mandatory")
    @Size(max = 50, message = "Designation must not exceed 50 characters")
    private String designation;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private Double price;
}