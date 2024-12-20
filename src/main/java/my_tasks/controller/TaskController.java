package my_tasks.controller;

import my_tasks.model.Task;
import my_tasks.service.ITaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mytasks/api/v1/tasks")
@CrossOrigin("http://localhost:5173")
public class TaskController {

    private final Logger logger =
            LoggerFactory.getLogger(TaskController.class);

    private final ITaskService taskService;

    @Autowired
    public TaskController(ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        if (tasks.isEmpty()) {
            logger.warn("No se encontraron tareas en el sistema");
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        tasks.forEach(task -> logger.info(task.toString()));
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            logger.warn("No se encontro ninguna Tarea con el ID: {} en el sistema", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        logger.info("Tarea encontrada: {}", task);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    public ResponseEntity<Task> postTask(@RequestBody Task task) {
        logger.info("Tarea a agregar: {}", task);
        Task savedTask = taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task receivedTask) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            logger.warn("No se encontraron Tareas con el ID: {} en el sistema", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        task.setName(receivedTask.getName());
        task.setDescription(receivedTask.getDescription());
        task.setStartTime(receivedTask.getStartTime());
        task.setEndTime(receivedTask.getEndTime());
        task.setStatus(receivedTask.getStatus());
        task.setPriority(receivedTask.getPriority());
        task.setProject(receivedTask.getProject());

        taskService.saveTask(task);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        Task task = taskService.getTaskById(id);
        if (task == null) {
            logger.warn("Tarea con ID: {} no encontrada para eliminación.", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        taskService.deleteTask(task);
        logger.info("Cita con ID: {} eliminada exitosamente.", id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
