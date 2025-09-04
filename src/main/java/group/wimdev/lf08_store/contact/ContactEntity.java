package group.wimdev.lf08_store.contact;

import group.wimdev.lf08_store.supplier.SupplierEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "supplier_contact")
public class ContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    @Column(name = "zip")
    private String postcode;

    private String city;

    private String phone;

    @OneToOne(mappedBy = "contact", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private SupplierEntity supplier;
}
