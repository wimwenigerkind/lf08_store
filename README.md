# LF08 Store

## Supplier

### findAllSuppliers
```shell
xh http://localhost:8080/store/supplier
```

### getSupplierById
```shell
xh http://localhost:8080/store/supplier/1
```

### createSupplier
```shell
xh post http://localhost:8080/store/supplier name=Supplier01 street=SupplierStreet01 postcode=01010 city=SupplierCityO1
```

### updateSupplier
```shell
xh put http://localhost:8080/store/supplier/1 name=Supplier01 street=SupplierStreet01 postcode=88888 city=SupplierCityO1
```