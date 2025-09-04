package group.wimdev.lf08_store.supplier;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/store/supplier")
public class SupplierController {

    private final SupplierService supplierService;
    private final MappingService mappingService;

    public SupplierController(SupplierService supplierService, MappingService mappingService) {
        this.supplierService = supplierService;
        this.mappingService = mappingService;
    }

    @PostMapping
    public ResponseEntity<GetSupplierDto> createSupplier(@Valid @RequestBody final AddSupplierDto dto) {
        SupplierEntity newSupplier = this.mappingService.mapAddSupplierDtoToSupplier(dto);
        newSupplier = this.supplierService.create(newSupplier);
        final GetSupplierDto request = this.mappingService.mapSupplierToGetSupplierDto(newSupplier);
        return new ResponseEntity<>(request, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<GetSupplierDto>> findAllSuppliers() {
        List<SupplierEntity> all = this.supplierService.readAll();
        List<GetSupplierDto> dtoList = new LinkedList<>();
        for (SupplierEntity supplier : all) {
            dtoList.add(this.mappingService.mapSupplierToGetSupplierDto(supplier));
        }
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSupplierDto> getSupplierById(@PathVariable final Long id) {
        final var entity = this.supplierService.readById(id);
        final GetSupplierDto dto = this.mappingService.mapSupplierToGetSupplierDto(entity);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetSupplierDto> updateSupplier(@PathVariable final Long id, @Valid @RequestBody final AddSupplierDto dto) {
        final var entity = this.supplierService.readById(id);
        if (entity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.mappingService.updateSupplierFromDto(entity, dto);
        final var updatedEntity = this.supplierService.update(entity);
        final var dtoUpdated = this.mappingService.mapSupplierToGetSupplierDto(updatedEntity);
        return new ResponseEntity<>(dtoUpdated, HttpStatus.OK);
    }
}
