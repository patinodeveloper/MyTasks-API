package my_tasks.service;

import my_tasks.model.Project;

import java.util.List;

public interface IProjectService {
    List<Project> getAllProjects();
    Project getProjectById(Long idProject);
    Project saveProject(Project project);
    void deleteProject(Project project);
}
