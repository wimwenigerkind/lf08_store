package group.wimdev.lf08_store.supplier;

import group.wimdev.lf08_store.article.ArticleEntity;
import group.wimdev.lf08_store.article.GetArticleDto;
import group.wimdev.lf08_store.contact.ContactEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    public List<GetArticleDto> mapArticlesToGetArticleDtos(Set<ArticleEntity> articles) {
        return articles.stream()
                .map(this::mapArticleToGetArticleDto)
                .collect(Collectors.toList());
    }

    private GetArticleDto mapArticleToGetArticleDto(ArticleEntity article) {
        GetArticleDto dto = new GetArticleDto();
        dto.setId(article.getId());
        dto.setDesignation(article.getDesignation());
        dto.setPrice(article.getPrice());
        return dto;
    }
}
