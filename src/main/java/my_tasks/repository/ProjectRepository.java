package my_tasks.repository;

import my_tasks.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    /* Consultas personalizadas */
    List<Project> findByUserId(Long id);
}
