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

### deleteSupplier
```shell
xh delete http://localhost:8080/store/supplier/1
```

### getArticlesBySupplierId
```shell
xh http://localhost:8080/store/supplier/1/articles
```

## Article

### findAllArticles
```shell
xh http://localhost:8080/store/article
```

### getArticleById
```shell
xh http://localhost:8080/store/article/1
```

### getArticleByDesignation
```shell
xh http://localhost:8080/store/article/designation/ArticleDesignation01
```

### createArticleForSupplier
```shell
xh post http://localhost:8080/store/article/1 designation=ArticleDesignation01 price:=29.99
```