package hexlet.code;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.repository.LabelRepository;
import org.instancio.Instancio;
import org.instancio.Select;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.util.ModelGenerator;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class TasksControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private  LabelRepository labelRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    private Task testTask;

    @BeforeEach
    public void beforeEach() {
        var taskStatus = taskStatusRepository.findBySlug("draft").orElseThrow();
        testTask = Instancio.of(modelGenerator.getTaskModel())
                .set(Select.field(Task::getAssignee), null)
                .create();
        testTask.setTaskStatus(taskStatus);
        testTask.setLabels(Set.of());
        taskRepository.save(testTask);
    }

    @Test
    public void testIndex() throws Exception {
        mockMvc.perform(get("/api/tasks").with(jwt()))
                .andExpect(status().isOk());
    }

    @Test
    public void testShow() throws Exception {
        var request = get("/api/tasks/" + testTask.getId()).with(jwt());
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testCreate() throws Exception {
        var taskStatus = taskStatusRepository.findBySlug("draft").get();
        var label = labelRepository.findByName("feature").get();
        var data = new TaskCreateDTO();
        data.setName("New Task Name");
        data.setTaskStatusSlug(taskStatus.getSlug());
        data.setTaskLabelIds(Set.of(label.getId()));

        var json = om.writeValueAsString(data);
        var request = post("/api/tasks").with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        mockMvc.perform(request)
                .andExpect(status().isCreated());

        var task = taskRepository.findByName(data.getName());
        assertNotNull(task.get());
    }

    @Test
    public void testUpdate() throws Exception {
        var data = Instancio.of(modelGenerator.getTaskModel())
                .create();

        var request = put("/api/tasks/" + testTask.getId()).with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(data));
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete("/api/tasks/" + testTask.getId()).with(jwt()))
                .andExpect(status().isNoContent());
    }
}

