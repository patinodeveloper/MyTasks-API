package my_tasks.service.impl;

import my_tasks.dto.tasks.TaskDTO;
import my_tasks.dto.tasks.TaskRequestDTO;
import my_tasks.helpers.UserHelper;
import my_tasks.mappers.TaskMapper;
import my_tasks.model.Project;
import my_tasks.model.Task;
import my_tasks.model.User;
import my_tasks.repository.ProjectRepository;
import my_tasks.repository.TaskRepository;
import my_tasks.service.ITaskService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements ITaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final TaskMapper taskMapper;
    private final UserHelper userHelper;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository,
                           TaskMapper taskMapper, UserHelper userHelper) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.taskMapper = taskMapper;
        this.userHelper = userHelper;
    }

    @Override
    public List<TaskDTO> getAllTasks() {
        List<Task> tasks = taskRepository.findAll();
        return taskMapper.toDTOList(tasks);
    }

    @Override
    public TaskDTO getTaskById(Long idTask, User user) {
        Task task = taskRepository.findById(idTask).orElse(null);
        if (task == null) return null;

        if (userHelper.isAdmin(user) || task.getProject().getUser().getId().equals(user.getId())) {
            return taskMapper.toDTO(task);
        }

        return null;
    }

    @Override
    public TaskDTO saveTask(TaskRequestDTO requestDTO, User user) {
        // Busca el proyecto por ID
        Project project = projectRepository.findById(requestDTO.getProject().getId())
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

        // Verifica que el proyecto le pertenece al usuario autenticado
        if (!project.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("No tienes permiso para agregar tareas a este proyecto");
        }

        Task task = taskMapper.toEntity(requestDTO);
        Task saved = taskRepository.save(task);
        return taskMapper.toDTO(saved);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskRequestDTO requestDTO, User user) {
        Task task = taskRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Tarea no encontrada"));

        if (!userHelper.isAdmin(user) && !task.getProject().getUser().getId().equals(user.getId())) {
            return null;
        }

        task.setName(requestDTO.getName());
        task.setDescription(requestDTO.getDescription());
        task.setStartTime(requestDTO.getStartTime());
        task.setEndTime(requestDTO.getEndTime());
        task.setStatus(requestDTO.getStatus());
        task.setPriority(requestDTO.getPriority());
        task.setProject(requestDTO.getProject());

        Task updated = taskRepository.save(task);
        return taskMapper.toDTO(updated);
    }

    @Override
    public void deleteTask(Long id, User user) {
        Task task = taskRepository.findById(id).orElse(null);
        if (task == null) return;

        if (!userHelper.isAdmin(user) || !task.getProject().getUser().getId().equals(user.getId())) {
            return;
        }

        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskDTO> findByProjectUserId(Long userId) {
        List<Task> tasks = taskRepository.findByProjectUserId(userId);
        return taskMapper.toDTOList(tasks);
    }

}
