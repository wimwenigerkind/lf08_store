package group.wimdev.lf08_store.article;

import group.wimdev.lf08_store.contact.ContactEntity;
import group.wimdev.lf08_store.supplier.SupplierEntity;
import group.wimdev.lf08_store.testcontainers.AbstractIntegrationTest;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostArticleIT extends AbstractIntegrationTest {
    @Test
    @Transactional
    void postArticle() throws Exception {
        var supplier = new SupplierEntity();
        supplier.setName("Test Supplier");
        var contact = new ContactEntity();
        contact.setStreet("Teststraße 1");
        contact.setPostcode("12345");
        contact.setCity("Test City");
        contact.setPhone("0123456789");
        supplier.setContact(contact);
        
        var savedSupplier = this.supplierRepository.save(supplier);
        
        String content = """
                {
                    "designation": "Test Article",
                    "price": 29.99,
                    "currency": "USD"
                }
                """;

        final var contentAsString = this.mockMvc.perform(post("/store/article/" + savedSupplier.getId())
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("designation", is("Test Article")))
                .andExpect(jsonPath("price", is(29.99)))
                .andExpect(jsonPath("currency", is("USD")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        final var loadedEntity = articleRepository.findById(id);
        assertThat(loadedEntity).isPresent();
        assertThat(loadedEntity.get().getDesignation()).isEqualTo("Test Article");
        assertThat(loadedEntity.get().getPrice()).isEqualTo(29.99);
        assertThat(loadedEntity.get().getSupplier().getId()).isEqualTo(savedSupplier.getId());
        assertThat(loadedEntity.get().getCreateDate()).isNotNull();
        assertThat(loadedEntity.get().getLastUpdateDate()).isNotNull();
    }
}