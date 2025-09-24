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

### findAllArticles with currency conversion
```shell
xh http://localhost:8080/store/article currency==EUR
```

### getArticleById
```shell
xh http://localhost:8080/store/article/1
```

### getArticleById with currency conversion
```shell
xh http://localhost:8080/store/article/1 currency==JPY
```

### getArticleByDesignation
```shell
xh http://localhost:8080/store/article/designation/ArticleDesignation01
```

### getArticleByDesignation with currency conversion
```shell
xh http://localhost:8080/store/article/designation/ArticleDesignation01 currency==CHF
```

### createArticleForSupplier
```shell
xh post http://localhost:8080/store/article/1 designation=ArticleDesignation01 price:=29.99 currency=USD
```

## Currency Conversion

The application supports real-time currency conversion for all article endpoints. Simply add the `currency` parameter to convert prices to your desired currency.

### Supported Currencies
- **USD** - US Dollar (base currency)
- **EUR** - Euro
- **GBP** - British Pound
- **JPY** - Japanese Yen
- **CHF** - Swiss Franc
- **CAD** - Canadian Dollar
- **AUD** - Australian Dollar
- And 150+ more currencies supported by the Exchange Rate API

### Examples
```shell
xh http://localhost:8080/store/article currency==GBP
```

```shell
xh http://localhost:8080/store/article/1 currency==JPY
```

```shell
xh http://localhost:8080/store/article/designation/iPhone currency==CHF
```

### Response Format
```json
{
  "id": 1,
  "designation": "iPhone 15",
  "price": 899.99,
  "currency": "USD"
}
```

When currency conversion is applied:
```json
{
  "id": 1,
  "designation": "iPhone 15",
  "price": 832.45,
  "currency": "EUR"
}
```