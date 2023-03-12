import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.yuri.productservice.dto.ProductDTO;
import org.yuri.productservice.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Container
	final static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;

	@DynamicPropertySource
	static void setProperties(final DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldBeAbleToCreateProduct() throws Exception {
		final ProductDTO productDTO = getProductDTO();

		final String productJson = objectMapper.writeValueAsString(productDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productJson))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		Assertions.assertEquals(1, this.productRepository.findAll().size());
	}

	@Test
	void shouldBeAbleToListAllProducts() throws Exception {
		final ProductDTO productDTO = getProductDTO();

		final String productJson = objectMapper.writeValueAsString(productDTO);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productJson));

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productJson));

		final MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();

		final List<ProductDTO> productDTOS = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<ProductDTO>>() {});

		Assertions.assertEquals(2, productDTOS.size());
	}

	private ProductDTO getProductDTO() {
		final ProductDTO product = ProductDTO.builder()
				.name("Product 1")
				.price(BigDecimal.valueOf(10))
				.description("Description of Product 1")
				.build();

		return product;
	}

}
