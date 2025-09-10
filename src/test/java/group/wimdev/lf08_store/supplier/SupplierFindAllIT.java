package group.wimdev.lf08_store.supplier;

import group.wimdev.lf08_store.contact.ContactEntity;
import group.wimdev.lf08_store.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SupplierFindAllIT extends AbstractIntegrationTest {
    @Test
    void findAll() throws Exception {
        var supplier1 = new SupplierEntity();
        supplier1.setName("Meier");
        var contact1 = new ContactEntity();
        contact1.setStreet("Hauptstraße");
        contact1.setPostcode("12345");
        contact1.setCity("Bremen");
        contact1.setPhone("+4912345");
        supplier1.setContact(contact1);

        this.supplierRepository.save(supplier1);

        this.mockMvc.perform(get("/store/supplier"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Meier")))
                .andExpect(jsonPath("$[0].street", is("Hauptstraße")))
                .andExpect(jsonPath("$[0].postcode", is("12345")))
                .andExpect(jsonPath("$[0].city", is("Bremen")))
                .andExpect(jsonPath("$[0].phone", is("+4912345")));
    }
}
