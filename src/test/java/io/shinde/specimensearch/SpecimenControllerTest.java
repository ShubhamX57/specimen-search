package io.shinde.specimensearch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SpecimenControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void healthReportsIndexedCount() throws Exception {
        mvc.perform(get("/api/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"))
                .andExpect(jsonPath("$.indexed").value(5));
    }

    @Test
    void listsAllSpecimens() throws Exception {
        mvc.perform(get("/api/specimens"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].id").value("SPC-001"));
    }

    @Test
    void retrievesByIdAndReturns404WhenMissing() throws Exception {
        mvc.perform(get("/api/specimens/SPC-002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Zebrafish embryo"));

        mvc.perform(get("/api/specimens/NOPE"))
                .andExpect(status().isNotFound());
    }

    @Test
    void searchMatchesFreeTextAcrossFields() throws Exception {
        mvc.perform(get("/api/specimens/search").param("q", "imaging"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); // zebrafish + drosophila
    }

    @Test
    void searchFiltersByTaxon() throws Exception {
        mvc.perform(get("/api/specimens/search").param("taxon", "Mammalia"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); // mouse + human
    }
}
