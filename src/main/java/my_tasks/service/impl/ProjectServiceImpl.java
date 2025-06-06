package my_tasks.service.impl;

import my_tasks.dto.projects.ProjectDTO;
import my_tasks.dto.projects.ProjectRequestDTO;
import my_tasks.exceptions.ForbbidenException;
import my_tasks.exceptions.NotFoundException;
import my_tasks.helpers.UserHelper;
import my_tasks.mappers.ProjectMapper;
import my_tasks.model.Project;
import my_tasks.model.User;
import my_tasks.repository.ProjectRepository;
import my_tasks.service.IProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements IProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final UserHelper userHelper;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, UserHelper userHelper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.userHelper = userHelper;
    }

    @Override
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAll();
        return projectMapper.toDTOList(projects);
    }

    @Override
    public ProjectDTO getProjectById(Long idProject, User user) {
        Project project = projectRepository.findById(idProject).orElse(null);
        if (project == null) return null;

        if (userHelper.isAdmin(user) || project.getUser().getId().equals(user.getId())) {
            return projectMapper.toDTO(project);
        }

        throw new ForbbidenException("No tienes permiso para acceder a este Proyecto");
    }

    @Override
    public ProjectDTO saveProject(ProjectRequestDTO projectRequestDTO, User user) {
        Project project = projectMapper.toEntity(projectRequestDTO);
        project.setUser(user);

        Project savedProject = projectRepository.save(project);
        return projectMapper.toDTO(savedProject);
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectRequestDTO requestDTO, User user) {
        Project project = projectRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Proyecto no encontrado"));

        if (!userHelper.isAdmin(user) && !project.getUser().getId().equals(user.getId())) {
            throw new ForbbidenException("No tienes permiso para actualizar este Proyecto");
        }

        project.setName(requestDTO.getName());
        project.setDescription(requestDTO.getDescription());
        project.setStartTime(requestDTO.getStartTime());
        project.setEndTime(requestDTO.getEndTime());
        project.setStatus(requestDTO.getStatus());

        Project updated = projectRepository.save(project);
        return projectMapper.toDTO(updated);
    }

    @Override
    public void deleteProject(Long id, User user) {
        Project project = projectRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Proyecto no encontrado con el ID: " + id));

        if (!userHelper.isAdmin(user) && !project.getUser().getId().equals(user.getId())) {
            throw new ForbbidenException("No tienes permiso para acceder a este Proyecto");
        }

        projectRepository.deleteById(id);
    }

    @Override
    public List<ProjectDTO> findByUserId(Long id) {
        List<Project> projects = projectRepository.findByUserId(id);
        return projectMapper.toDTOList(projects);
    }
}
