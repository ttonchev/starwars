package com.spring;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.controller.StarshipController.StarshipsCounterRequest;
import com.spring.controller.StarshipController.StarshipsCounterRequest.Action;
import com.spring.controller.VehicleController.VehiclesCounterRequest;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StarwarsApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = StarwarsApplicationTests.MongoDbInitializer.class)
class StarwarsApplicationTests {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper objectMapper;
  private static MongoDbContainer mongoDbContainer;
  private HttpMessageConverter mappingJackson2HttpMessageConverter;

  @BeforeAll
  public static void startContainerAndPublicPortIsAvailable() {
    mongoDbContainer = new MongoDbContainer();
    mongoDbContainer.start();
  }

  @Autowired
  void setConverters(HttpMessageConverter<?>[] converters) {
    mappingJackson2HttpMessageConverter =
        Arrays.stream(converters).filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny()
            .orElse(null);

    assertNotNull("the JSON message converter must not be null", mappingJackson2HttpMessageConverter);
  }

  @Test
  public void testStarshipsOperations() throws Exception {
    mvc.perform(get("/starships/")).andExpect(status().isOk()).andExpect(content().json(json(List.of())));

    mvc.perform(get("/starships/search?name=Death Star")).andExpect(status().isOk())
        .andExpect(content().json("{\"id\":null,\"name\":\"Death Star\",\"count\":1}"));
    mvc.perform(get("/starships/")).andExpect(status().isOk())
        .andExpect(content().json("[{\"id\":1,\"name\":\"Death Star\",\"count\":1}]"));

    mvc.perform(patch("/starships/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new StarshipsCounterRequest(Action.INCREMENT, 10))))
        .andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":\"Death Star\",\"count\":11}"));

    mvc.perform(patch("/starships/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new StarshipsCounterRequest(Action.DECREMENT, 10))))
        .andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":\"Death Star\",\"count\":1}"));

    mvc.perform(patch("/starships/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new StarshipsCounterRequest(Action.SET, 10))))
        .andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":\"Death Star\",\"count\":10}"));
  }

  @Test
  public void testVehiclesOperations() throws Exception {
    mvc.perform(get("/vehicles/")).andExpect(status().isOk()).andExpect(content().json(json(List.of())));

    mvc.perform(get("/vehicles/search?name=Snowspeeder")).andExpect(status().isOk())
        .andExpect(content().json("{\"id\":null,\"name\":\"Snowspeeder\",\"count\":1}"));
    mvc.perform(get("/vehicles/")).andExpect(status().isOk())
        .andExpect(content().json("[{\"id\":1,\"name\":\"Snowspeeder\",\"count\":1}]"));

    mvc.perform(patch("/vehicles/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new VehiclesCounterRequest(VehiclesCounterRequest.Action.INCREMENT, 10))))
        .andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":\"Snowspeeder\",\"count\":11}"));

    mvc.perform(patch("/vehicles/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new VehiclesCounterRequest(VehiclesCounterRequest.Action.DECREMENT, 10))))
        .andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":\"Snowspeeder\",\"count\":1}"));

    mvc.perform(patch("/vehicles/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new VehiclesCounterRequest(VehiclesCounterRequest.Action.SET,
                10))))
        .andExpect(status().isOk()).andExpect(content().json("{\"id\":1,\"name\":\"Snowspeeder\",\"count\":10}"));
  }

  private String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
    return mockHttpOutputMessage.getBodyAsString();
  }

  public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      TestPropertyValues values =
          TestPropertyValues.of("spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
              "spring.data.mongodb.port=" + mongoDbContainer.getPort());
      values.applyTo(configurableApplicationContext);
    }
  }
}
