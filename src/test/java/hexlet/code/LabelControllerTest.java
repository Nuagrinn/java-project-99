package hexlet.code;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class LabelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private LabelRepository labelRepository;

    private Label testLabel;

    @BeforeEach
    public void beforeEach() {
        testLabel = Instancio.of(Label.class)
                .ignore(field(Label.class, "id"))
                .create();
        labelRepository.save(testLabel);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/labels").with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        var request = get("/api/labels/" + testLabel.getId()).with(jwt());
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var data = Instancio.of(Label.class)
                .ignore(field(Label.class, "id"))
                .create();

        var request = post("/api/labels").with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var label = labelRepository.findByName(data.getName());
        assertNotNull(label.get());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = Instancio.of(Label.class)
                .ignore(field(Label.class, "id"))
                .create();

        var request = put("/api/labels/" + testLabel.getId()).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/labels/" + testLabel.getId()).with(jwt()))
                .andExpect(status().isNoContent());
    }
}
