package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.contact.ContactEntity;
import group.wimdev.lf08_store.supplier.SupplierEntity;
import group.wimdev.lf08_store.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ArticleFindAllIT extends AbstractIntegrationTest {
    @Test
    @Transactional
    void findAll() throws Exception {
        var supplier = new SupplierEntity();
        supplier.setName("Test Supplier");
        var contact = new ContactEntity();
        contact.setStreet("Teststraße 1");
        contact.setPostcode("12345");
        contact.setCity("Test City");
        contact.setPhone("0123456789");
        supplier.setContact(contact);
        
        var savedSupplier = this.supplierRepository.save(supplier);

        var article = new ArticleEntity();
        article.setDesignation("Test Article 1");
        article.setPrice(25.50);
        article.setSupplier(savedSupplier);
        article.setCreateDate(LocalDateTime.now());
        article.setLastUpdateDate(LocalDateTime.now());

        this.articleRepository.save(article);

        this.mockMvc.perform(get("/store/article"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].designation", is("Test Article 1")))
                .andExpect(jsonPath("$[0].price", is(25.5)));
    }
}