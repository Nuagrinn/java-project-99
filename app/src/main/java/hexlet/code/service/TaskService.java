package hexlet.code.service;

import hexlet.code.dto.TaskCreateDTO;
import hexlet.code.dto.TaskUpdateDTO;
import hexlet.code.mapper.TaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;

    @Autowired
    private final TaskMapper taskMapperImpl;

    public Task create(TaskCreateDTO data) {
        var task = taskMapperImpl.map(data);
        return taskRepository.save(task);
    }

    public Task update(long id, TaskUpdateDTO data) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task has not found"));
        taskMapperImpl.update(data, task);
        return taskRepository.save(task);
    }
}

