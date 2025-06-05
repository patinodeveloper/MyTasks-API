package my_tasks.dto.projects;

import lombok.Getter;
import my_tasks.model.Project;
import my_tasks.model.ProjectStatus;

import java.time.LocalDate;

@Getter
public class ProjectDTO {
    private final Long id;
    private final String name;
    private final String description;
    private final LocalDate startTime;
    private final LocalDate endTime;
    private final ProjectStatus status;

    public ProjectDTO(Project project) {
        this.id = project.getId();
        this.name = project.getName();
        this.description = project.getDescription();
        this.startTime = project.getStartTime();
        this.endTime = project.getEndTime();
        this.status = project.getStatus();
    }
}
