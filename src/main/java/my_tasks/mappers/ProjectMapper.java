package my_tasks.mappers;

import my_tasks.dto.projects.ProjectDTO;
import my_tasks.dto.projects.ProjectRequestDTO;
import my_tasks.model.Project;

import java.util.List;

public interface ProjectMapper {
    ProjectDTO toDTO(Project project);

    Project toEntity(ProjectDTO dto);

    List<ProjectDTO> toDTOList(List<Project> projects);

    Project toEntity(ProjectRequestDTO requestDTO);
}
