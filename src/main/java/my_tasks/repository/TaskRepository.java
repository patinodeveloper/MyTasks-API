package my_tasks.repository;

import my_tasks.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    /* Consultas personalizadas */
    List<Task> findByProjectUserId(Long userId);
}
