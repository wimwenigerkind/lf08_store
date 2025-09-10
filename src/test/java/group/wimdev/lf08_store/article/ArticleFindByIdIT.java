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

public class ArticleFindByIdIT extends AbstractIntegrationTest {
    @Test
    @Transactional
    void findById() throws Exception {
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
        article.setDesignation("Test Article by ID");
        article.setPrice(45.75);
        article.setSupplier(savedSupplier);
        article.setCreateDate(LocalDateTime.now());
        article.setLastUpdateDate(LocalDateTime.now());

        var savedArticle = this.articleRepository.save(article);

        this.mockMvc.perform(get("/store/article/" + savedArticle.getId()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("id", is(savedArticle.getId().intValue())))
                .andExpect(jsonPath("designation", is("Test Article by ID")))
                .andExpect(jsonPath("price", is(45.75)));
    }
    
    @Test
    void findByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/store/article/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", is("Article with id 999 not found")));
    }
}