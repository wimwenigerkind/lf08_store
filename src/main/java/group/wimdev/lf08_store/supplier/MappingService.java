package group.wimdev.lf08_store.supplier;

import group.wimdev.lf08_store.contact.ContactEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingService {
    public SupplierEntity mapAddSupplierDtoToSupplier(AddSupplierDto dto) {
        SupplierEntity newSupplier = new SupplierEntity();
        newSupplier.setName(dto.getName());
        ContactEntity newContact = new ContactEntity();
        newContact.setStreet(dto.getStreet());
        newContact.setPostcode(dto.getPostcode());
        newContact.setCity(dto.getCity());
        newContact.setPhone(dto.getPhone());
        newContact.setSupplier(newSupplier);
        newSupplier.setContact(newContact);
        return newSupplier;
    }

    public GetSupplierDto mapSupplierToGetSupplierDto(SupplierEntity supplier) {
        GetSupplierDto dto = new GetSupplierDto();
        dto.setId(supplier.getId());
        dto.setName(supplier.getName());
        if (supplier.getContact() != null) {
            dto.setStreet(supplier.getContact().getStreet());
            dto.setPostcode(supplier.getContact().getPostcode());
            dto.setCity(supplier.getContact().getCity());
            dto.setPhone(supplier.getContact().getPhone());
        }
        return dto;
    }

    public void updateSupplierFromDto(SupplierEntity supplier, AddSupplierDto dto) {
        supplier.setName(dto.getName());
        
        if (supplier.getContact() != null) {
            supplier.getContact().setStreet(dto.getStreet());
            supplier.getContact().setPostcode(dto.getPostcode());
            supplier.getContact().setCity(dto.getCity());
            supplier.getContact().setPhone(dto.getPhone());
        }
    }
}
