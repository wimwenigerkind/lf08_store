package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.supplier.SupplierEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "article")
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Designation is mandatory")
    private String designation;

    @NotNull
    private Double price;

    @NotNull
    private String currency;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate = LocalDateTime.now();

    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    private SupplierEntity supplier;
}
