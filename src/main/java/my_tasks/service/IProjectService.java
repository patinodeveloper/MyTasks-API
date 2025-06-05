package my_tasks.service;

import my_tasks.dto.projects.ProjectDTO;
import my_tasks.dto.projects.ProjectRequestDTO;
import my_tasks.model.User;

import java.util.List;

public interface IProjectService {
    List<ProjectDTO> getAllProjects();

    ProjectDTO getProjectById(Long idProject, User user);

    ProjectDTO saveProject(ProjectRequestDTO requestDTO, User user);

    ProjectDTO updateProject(Long id, ProjectRequestDTO requestDTO, User user);

    void deleteProject(Long id, User user);

    List<ProjectDTO> findByUserId(Long id);
}
