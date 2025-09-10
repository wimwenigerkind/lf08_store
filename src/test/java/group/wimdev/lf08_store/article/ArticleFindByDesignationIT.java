package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.contact.ContactEntity;
import group.wimdev.lf08_store.supplier.SupplierEntity;
import group.wimdev.lf08_store.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArticleFindByDesignationIT extends AbstractIntegrationTest {
    @Test
    @Transactional
    void findByDesignation() throws Exception {
        var supplier = new SupplierEntity();
        supplier.setName("Test Supplier");
        var contact = new ContactEntity();
        contact.setStreet("Teststraße 1");
        contact.setPostcode("12345");
        contact.setCity("Test City");
        contact.setPhone("0123456789");
        supplier.setContact(contact);
        
        var savedSupplier = this.supplierRepository.save(supplier);

        // Create article
        var article = new ArticleEntity();
        article.setDesignation("UniqueDesignation123");
        article.setPrice(99.99);
        article.setSupplier(savedSupplier);
        article.setCreateDate(LocalDateTime.now());
        article.setLastUpdateDate(LocalDateTime.now());

        var savedArticle = this.articleRepository.save(article);

        this.mockMvc.perform(get("/store/article/designation/UniqueDesignation123"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id", is(savedArticle.getId().intValue())))
                .andExpect(jsonPath("designation", is("UniqueDesignation123")))
                .andExpect(jsonPath("price", is(99.99)));
    }
    
    @Test
    void findByDesignationNotFound() throws Exception {
        this.mockMvc.perform(get("/store/article/designation/NonExistentDesignation"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", is("Article with designation NonExistentDesignation not found")));
    }
}