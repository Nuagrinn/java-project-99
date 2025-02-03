package hexlet.code.component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import hexlet.code.dto.UserCreateDTO;
import org.apache.commons.text.CaseUtils;
import org.instancio.Instancio;
import org.instancio.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import hexlet.code.mapper.UserMapper;
import hexlet.code.model.Label;
import hexlet.code.model.Task;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskRepository;
import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.util.ModelGenerator;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Initializer implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private TaskStatusRepository taskStatusRepository;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        addModels();
    }

    private void addModels() {
        var userData = new UserCreateDTO();
        userData.setEmail("hexlet@example.com");
        userData.setPassword("qwerty");
        var user = userMapper.map(userData);
        userRepository.save(user);

        var labelNames = Arrays.asList("bug", "feature");
        var labels = labelNames.stream()
                .map(name -> {
                    var label = new Label();
                    label.setName(name);
                    labelRepository.save(label);
                    return label;
                }).toList();

        var statusNames = Arrays.asList("draft", "to_review", "to_be_fixed", "to_publish", "published");
        var taskStatuses = statusNames.stream()
                .map(name -> {
                    var taskStatus = new TaskStatus();
                    taskStatus.setSlug(name);
                    taskStatus.setName(CaseUtils.toCamelCase(name, true, new char[] {'_'}));
                    taskStatusRepository.save(taskStatus);
                    return taskStatus;
                }).collect(Collectors.toSet());

        var tasks = Instancio.ofList(modelGenerator.getTaskModel())
                .size(30)
                .generate(Select.field(Task::getTaskStatus), gen -> gen.oneOf(taskStatuses))
                .supply(Select.field(Task::getLabels), () -> Set.of())
                .supply(Select.field(Task::getAssignee), () -> user)
                .create();
        taskRepository.saveAll(tasks);

    }
}
