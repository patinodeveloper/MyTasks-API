package my_tasks.controller;

import my_tasks.model.Project;
import my_tasks.service.IProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        if (projects.isEmpty()) {
            logger.warn("No se encontraron proyectos en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        projects.forEach(project -> logger.info(project.toString()));
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            logger.warn("Proyecto con ID {} no encontrado.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Proyecto encontrado: {}", project);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<Project> postProject(@RequestBody Project project) {
        logger.info("Proyecto a agregar: {}", project);
        Project savedProject = projectService.saveProject(project);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project receivedProject) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            logger.warn("No se encontraron Proyectos con el ID: {} en el sistema.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        project.setName(receivedProject.getName());
        project.setDescription(receivedProject.getDescription());
        project.setStartTime(receivedProject.getStartTime());
        project.setEndTime(receivedProject.getEndTime());
        project.setStatus(receivedProject.getStatus());

        projectService.saveProject(project);
        return ResponseEntity.ok(project);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            logger.warn("Proyecto con ID {} no encontrado para eliminación.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        projectService.deleteProject(project);
        logger.info("Proyecto con el ID {} eliminado exitosamente.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Proyecto eliminado exitosamente.");
    }
}
