package my_tasks.mappers;

import my_tasks.dto.tasks.TaskDTO;
import my_tasks.dto.tasks.TaskRequestDTO;
import my_tasks.model.Task;

import java.util.List;

public interface TaskMapper {
    TaskDTO toDTO(Task task);

    Task toEntity(TaskDTO dto);

    List<TaskDTO> toDTOList(List<Task> tasks);

    Task toEntity(TaskRequestDTO requestDTO);
}
