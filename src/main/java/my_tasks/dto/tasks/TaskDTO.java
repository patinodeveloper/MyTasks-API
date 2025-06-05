package my_tasks.dto.tasks;

import lombok.Getter;
import my_tasks.model.Task;
import my_tasks.model.TaskPriority;
import my_tasks.model.TaskStatus;

import java.time.LocalDate;

@Getter
public class TaskDTO {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final TaskStatus status;
    private final TaskPriority priority;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.name = task.getName();
        this.description = task.getDescription();
        this.startTime = task.getStartTime();
        this.endTime = task.getEndTime();
        this.status = task.getStatus();
        this.priority = task.getPriority();
    }
}
