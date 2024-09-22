package br.com.libdolf.sgtesig.repository;

import br.com.libdolf.sgtesig.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    void save(Task task);
    void update(Task task);
    void delete(Long id);
    Optional<Task> findById(Long id);
    List<Task> findAll();
}
