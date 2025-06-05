package my_tasks.controller;

import jakarta.validation.Valid;
import my_tasks.dto.projects.ProjectDTO;
import my_tasks.dto.projects.ProjectRequestDTO;
import my_tasks.model.Project;
import my_tasks.model.User;
import my_tasks.service.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mytasks/api/v1/projects")
@CrossOrigin("http://localhost:5173")
public class ProjectController {

    private final Logger logger =
            LoggerFactory.getLogger(ProjectController.class);

    private final IProjectService projectService;

    @Autowired
    public ProjectController(IProjectService projectService) {
        this.projectService = projectService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            logger.warn("No se encontraron proyectos en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        projects.forEach(project -> logger.info(project.toString()));
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        ProjectDTO project = projectService.getProjectById(id, user);
        if (project == null) {
            logger.warn("Proyecto con ID {} no encontrado.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        logger.info("Proyecto encontrado: {}", project);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<ProjectDTO> postProject(@RequestBody @Valid ProjectRequestDTO requestDTO,
                                                  @AuthenticationPrincipal User user) {
        logger.info("Proyecto a agregar: {}", requestDTO);
        ProjectDTO savedProject = projectService.saveProject(requestDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id,
                                                    @RequestBody @Valid ProjectRequestDTO requestDTO,
                                                    @AuthenticationPrincipal User user) {
        ProjectDTO updated = projectService.updateProject(id, requestDTO, user);
        if (updated == null) {
            logger.warn("Error al intentar actualizar el proyecto con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id, @AuthenticationPrincipal User user) {
        projectService.deleteProject(id, user);
        logger.info("Proyecto con el ID {} eliminado exitosamente.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Proyecto eliminado exitosamente.");
    }

    @GetMapping("/me")
    public ResponseEntity<List<ProjectDTO>> getProjectsByUser(@AuthenticationPrincipal User user) {
        List<ProjectDTO> projects = projectService.findByUserId(user.getId());
        if (projects.isEmpty()) {
            logger.warn("No se encontraron proyectos para el usuario en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        projects.forEach(project -> logger.info(project.toString()));
        return ResponseEntity.ok(projects);
    }

}
