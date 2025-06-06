package my_tasks.controller;

import my_tasks.dto.tasks.TaskDTO;
import my_tasks.dto.tasks.TaskRequestDTO;
import my_tasks.model.User;
import my_tasks.responses.ApiResponse;
import my_tasks.service.ITaskService;
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
@RequestMapping("/mytasks/api/v1/tasks")
@CrossOrigin("http://localhost:5173")
public class TaskController {

    private final Logger logger =
            LoggerFactory.getLogger(TaskController.class);

    private final ITaskService taskService;

    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getAllTasks() {
        List<TaskDTO> tasks = taskService.getAllTasks();

        if (tasks.isEmpty()) {
            logger.warn("No se encontraron tareas en el sistema");
            return ResponseEntity.ok(ApiResponse.successMessage("No se encontraron tareas en el sistema"));
        }

        tasks.forEach(task -> logger.info(task.toString()));
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> getTaskById(@PathVariable Long id, @AuthenticationPrincipal User user) {
        TaskDTO task = taskService.getTaskById(id, user);
        if (task == null) {
            logger.warn("No se encontró ninguna Tarea con el ID: {} en el sistema", id);
            return ResponseEntity.ok(ApiResponse.successMessage("La tarea no existe"));
        }
        logger.info("Tarea encontrada: {}", task);
        return ResponseEntity.ok(ApiResponse.success(task));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDTO>> postTask(@RequestBody TaskRequestDTO task,
                                                         @AuthenticationPrincipal User user) {
        logger.info("Tarea a agregar: {}", task);
        TaskDTO savedTask = taskService.saveTask(task, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(savedTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDTO>> updateTask(@PathVariable Long id, @RequestBody TaskRequestDTO receivedTask,
                                                           @AuthenticationPrincipal User user) {
        TaskDTO updated = taskService.updateTask(id, receivedTask, user);

        if (updated == null) {
            logger.warn("Error al intentar actualizar la tarea con ID: {}", id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(ApiResponse.success(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long id, @AuthenticationPrincipal User user) {
        taskService.deleteTask(id, user);
        logger.info("Cita con ID: {} eliminada exitosamente.", id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ApiResponse.successMessage("Tarea eliminada exitosamente"));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<List<TaskDTO>>> getTasksByUser(@AuthenticationPrincipal User user) {
        List<TaskDTO> tasks = taskService.findByProjectUserId(user.getId());
        if (tasks.isEmpty()) {
            logger.warn("No se encontraron tareas para el usuario en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        tasks.forEach(task -> logger.info(task.toString()));
        return ResponseEntity.ok(ApiResponse.success(tasks));
    }
}
