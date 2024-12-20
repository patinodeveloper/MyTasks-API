package my_tasks.service;


import my_tasks.model.Task;

import java.util.List;

public interface ITaskService {
    List<Task> getAllTasks();
    Task getTaskById(Long idTask);
    Task saveTask(Task task);
    void deleteTask(Task task);
}
