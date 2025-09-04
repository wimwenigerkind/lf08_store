package group.wimdev.lf08_store.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public SupplierEntity create(SupplierEntity supplier) {
        return supplierRepository.save(supplier);
    }

    public List<SupplierEntity> readAll() {
        return supplierRepository.findAll();
    }

    public SupplierEntity readById(Long id) {
        return supplierRepository.findById(id).orElse(null);
    }

    public SupplierEntity update(SupplierEntity supplier) {
        SupplierEntity updatedSupplier = readById(supplier.getId());
        updatedSupplier.setName(supplier.getName());
        updatedSupplier.getContact().setStreet(supplier.getContact().getStreet());
        updatedSupplier.getContact().setPostcode(supplier.getContact().getPostcode());
        updatedSupplier.getContact().setCity(supplier.getContact().getCity());
        updatedSupplier.getContact().setPhone(supplier.getContact().getPhone());
        return supplierRepository.save(updatedSupplier);
    }

    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }
}
