package group.wimdev.lf08_store.supplier;

import group.wimdev.lf08_store.article.ArticleEntity;
import group.wimdev.lf08_store.article.ArticleService;
import group.wimdev.lf08_store.contact.ContactEntity;
import group.wimdev.lf08_store.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SupplierFindArticlesIT extends AbstractIntegrationTest {
    
    @Autowired
    private ArticleService articleService;

    @Test
    void findArticlesBySupplierId() throws Exception {
        var supplier = new SupplierEntity();
        supplier.setName("Test Supplier");
        var contact = new ContactEntity();
        contact.setStreet("Teststraße 1");
        contact.setPostcode("12345");
        contact.setCity("Test City");
        contact.setPhone("0123456789");
        supplier.setContact(contact);

        var savedSupplier = this.supplierRepository.save(supplier);

        var article1 = new ArticleEntity();
        article1.setDesignation("Test Article 1");
        article1.setPrice(25.50);
        article1.setCurrency("USD");
        this.articleService.createForSupplier(article1, savedSupplier.getId());

        var article2 = new ArticleEntity();
        article2.setDesignation("Test Article 2");
        article2.setPrice(15.99);
        article2.setCurrency("USD");
        this.articleService.createForSupplier(article2, savedSupplier.getId());

        this.mockMvc.perform(get("/store/supplier/" + savedSupplier.getId() + "/articles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").isNumber())
                .andExpect(jsonPath("$[0].designation").isString())
                .andExpect(jsonPath("$[0].price").isNumber())
                .andExpect(jsonPath("$[1].id").isNumber())
                .andExpect(jsonPath("$[1].designation").isString())
                .andExpect(jsonPath("$[1].price").isNumber());
    }
    
    @Test
    void findArticlesBySupplierIdNotFound() throws Exception {
        this.mockMvc.perform(get("/store/supplier/999/articles"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", is("Supplier with id 999 not found")));
    }
    
    @Test
    void findArticlesBySupplierIdWithNoArticles() throws Exception {
        var supplier = new SupplierEntity();
        supplier.setName("Empty Supplier");
        var contact = new ContactEntity();
        contact.setStreet("Leerstraße 1");
        contact.setPostcode("00000");
        contact.setCity("Empty City");
        contact.setPhone("0000000000");
        supplier.setContact(contact);
        
        var savedSupplier = this.supplierRepository.save(supplier);

        this.mockMvc.perform(get("/store/supplier/" + savedSupplier.getId() + "/articles"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}