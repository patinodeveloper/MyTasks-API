package my_tasks.mappers.impl;

import my_tasks.dto.tasks.TaskDTO;
import my_tasks.dto.tasks.TaskRequestDTO;
import my_tasks.mappers.TaskMapper;
import my_tasks.model.Task;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapperImpl implements TaskMapper {
    @Override
    public TaskDTO toDTO(Task task) {
        if (task == null) return null;
        return new TaskDTO(task);
    }

    @Override
    public Task toEntity(TaskDTO dto) {
        if (dto == null) return null;

        Task task = new Task();
        task.setId(dto.getId());
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setStartTime(dto.getStartTime());
        task.setEndTime(dto.getEndTime());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());

        return task;
    }

    @Override
    public List<TaskDTO> toDTOList(List<Task> tasks) {
        List<TaskDTO> dtoList = new ArrayList<>();

        if (tasks == null) return dtoList;

        for (Task task : tasks) {
            TaskDTO taskDTO = toDTO(task);
            dtoList.add(taskDTO);
        }

        return dtoList;
    }

    @Override
    public Task toEntity(TaskRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        Task task = new Task();
        task.setName(requestDTO.getName());
        task.setDescription(requestDTO.getDescription());
        task.setStartTime(requestDTO.getStartTime());
        task.setEndTime(requestDTO.getEndTime());
        task.setStatus(requestDTO.getStatus());
        task.setPriority(requestDTO.getPriority());
        task.setProject(requestDTO.getProject());

        return task;
    }
}
