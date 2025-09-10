package group.wimdev.lf08_store.supplier;

import group.wimdev.lf08_store.contact.ContactEntity;
import group.wimdev.lf08_store.testcontainers.AbstractIntegrationTest;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostArticleForSupplierByIdIT extends AbstractIntegrationTest {

    @Test
    void postArticleForSupplierById() throws Exception {
        var supplier1 = new SupplierEntity();
        supplier1.setName("Meier");
        var contact1 = new ContactEntity();
        contact1.setStreet("Hauptstraße");
        contact1.setPostcode("12345");
        contact1.setCity("Bremen");
        contact1.setPhone("+4912345");
        supplier1.setContact(contact1);

        var savedSupplier1 = this.supplierRepository.save(supplier1);

        String content = """
                {
                         "designation": "ArticleDesignation01",
                         "price": 29.99
                }
                """;

        final var contentAsString = this.mockMvc.perform(
                        post("/store/article/{id}", savedSupplier1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("designation", is("ArticleDesignation01")))
                .andExpect(jsonPath("price", is(29.99)))
                .andReturn()
                .getResponse()
                .getContentAsString();

        final var id = Long.parseLong(new JSONObject(contentAsString).get("id").toString());

        final var loadedEntity = supplierRepository.findById(id);
        loadedEntity.ifPresent(supplier -> {
            assert supplier.getArticles() != null;
            assert supplier.getArticles().stream().anyMatch(article -> article.getDesignation().equals("ArticleDesignation01") && article.getPrice() == 29.99);
        });
    }

    @Test
    void postArticleForNonExistentSupplierId() throws Exception {
        String content = """
            {
                "designation": "InvalidArticle",
                "price": 10.0
            }
            """;

        this.mockMvc.perform(
                        post("/store/article/{id}", 9999L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("message", is("Supplier with id 9999 not found")));
    }
}