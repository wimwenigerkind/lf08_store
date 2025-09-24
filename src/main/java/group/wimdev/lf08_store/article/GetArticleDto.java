package group.wimdev.lf08_store.article;

import lombok.Data;

@Data
public class GetArticleDto {
    private Long id;
    private String designation;
    private Double price;
    private String currency;
}