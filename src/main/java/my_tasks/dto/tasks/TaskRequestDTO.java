package my_tasks.dto.tasks;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_tasks.model.Project;
import my_tasks.model.TaskPriority;
import my_tasks.model.TaskStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {
    @NotBlank(message = "El nombre de la tarea es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de la tarea debe tener entre 3 y 100 caracteres")
    private String name;

    private String description;

    @NotNull
    private LocalDate startTime;

    @NotNull
    private LocalDate endTime;

    @NotNull(message = "El status no puede ser null")
    private TaskStatus status;

    @NotNull(message = "La prioridad no puede ser null")
    private TaskPriority priority;

    @NotNull(message = "El proyecto es requerido")
    private Project project;
}
