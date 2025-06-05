package my_tasks.mappers.impl;

import my_tasks.dto.projects.ProjectDTO;
import my_tasks.dto.projects.ProjectRequestDTO;
import my_tasks.mappers.ProjectMapper;
import my_tasks.model.Project;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProjectMapperImpl implements ProjectMapper {
    @Override
    public ProjectDTO toDTO(Project project) {
        if (project == null) return null;
        return new ProjectDTO(project);
    }

    @Override
    public Project toEntity(ProjectDTO dto) {
        if (dto == null) return null;

        Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setStartTime(dto.getEndTime());
        project.setEndTime(dto.getEndTime());
        project.setStatus(dto.getStatus());

        return project;
    }

    @Override
    public List<ProjectDTO> toDTOList(List<Project> projects) {
        List<ProjectDTO> dtoList = new ArrayList<>();

        if (projects == null) {
            return dtoList;
        }

        for (Project project : projects) {
            ProjectDTO dto = toDTO(project);
            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public Project toEntity(ProjectRequestDTO requestDTO) {
        if (requestDTO == null) return null;

        Project project = new Project();
        project.setName(requestDTO.getName());
        project.setDescription(requestDTO.getDescription());
        project.setStartTime(requestDTO.getEndTime());
        project.setEndTime(requestDTO.getEndTime());
        project.setStatus(requestDTO.getStatus());

        return project;
    }
}
