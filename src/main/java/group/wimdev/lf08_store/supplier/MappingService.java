package group.wimdev.lf08_store.supplier;

import group.wimdev.lf08_store.contact.ContactEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public SupplierEntity mapAddSupplierDtoToSupplier(AddSupplierDTO dto) {
        SupplierEntity newSupplier = new SupplierEntity();
        newSupplier.setName(dto.getName());
        ContactEntity newContact = new ContactEntity();
        newContact.setStreet(dto.getStreet());
        newContact.setPostcode(dto.getPostcode());
        newContact.setCity(dto.getCity());
        newContact.setPhone(dto.getPhone());
        newContact.setSupplier(newSupplier);
        return newSupplier;
    }
}
