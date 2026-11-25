package loja.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

/** Same rule as PricingPolicyTest, now through HTTP, with the clock frozen on Black Friday. */
@SpringBootTest
@AutoConfigureMockMvc
class PriceControllerTest {

    @TestConfiguration
    static class FridayAfternoon {

        @Bean
        @Primary
        Clock fixedClock() {
            return Clock.fixed(Instant.parse("2026-11-27T16:00:00Z"), ZoneOffset.UTC);
        }
    }

    @Autowired
    private MockMvc mvc;

    @Test
    void couponAndBlackFridayAreCappedAtThirtyPercent() throws Exception {
        String order = """
                {
                  "id": "BF-1001",
                  "customerEmail": "ana@exemplo.pt",
                  "couponCode": "BEMVINDO10",
                  "lines": [ { "sku": "AURICULARES", "unitPrice": 49.99, "quantity": 1 } ]
                }
                """;

        mvc.perform(post("/encomendas/preco").contentType(MediaType.APPLICATION_JSON).content(order))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total.amount").value(34.99));
    }
}
