package my_tasks.dto.projects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import my_tasks.model.ProjectStatus;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRequestDTO {
    @NotBlank(message = "El nombre del proyecto es requerido")
    @Size(min = 3, max = 50, message = "El nombre del proyecto debe tener entre 3 y 100 caracteres")
    private String name;

    private String description;

    @NotNull(message = "La fecha de inicio es requerida")
    private LocalDate startTime;

    @NotNull(message = "La fecha de finalización es requerida")
    private LocalDate endTime;

    @NotNull(message = "El status es requerido")
    private ProjectStatus status;
}
