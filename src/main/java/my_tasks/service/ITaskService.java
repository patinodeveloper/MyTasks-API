package my_tasks.service;


import my_tasks.dto.tasks.TaskDTO;
import my_tasks.dto.tasks.TaskRequestDTO;
import my_tasks.model.Task;
import my_tasks.model.User;

import java.util.List;

public interface ITaskService {
    List<TaskDTO> getAllTasks();

    TaskDTO getTaskById(Long idTask, User user);

    TaskDTO saveTask(TaskRequestDTO requestDTO, User user);

    TaskDTO updateTask(Long id, TaskRequestDTO requestDTO, User user);

    void deleteTask(Long id, User user);

    List<TaskDTO> findByProjectUserId(Long id);
}
