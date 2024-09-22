package br.com.libdolf.sgtesig.repository;

import br.com.libdolf.sgtesig.model.Task;

import br.com.libdolf.sgtesig.util.Transacional;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;

@RequestScoped
public class TaskRepositoryImpl implements TaskRepository {

    @Inject
    private Logger logger;

    @Inject
    private EntityManager em;

    @Override
    @Transacional
    public void save(Task task) {
        if (task == null) {
            logger.error("Attempted to save a null task");
            throw new IllegalArgumentException("Task cannot be null");
        }
        logger.info("Saving task: {}", task);
        em.persist(task);
    }

    @Override
    @Transacional
    public void update(Task task) {
        if (task == null || task.getId() == null) {
            logger.error("Attempted to update a null task or task without ID");
            throw new IllegalArgumentException("Task or its ID cannot be null");
        }
        logger.info("Updating task: {}", task);
        em.merge(task);
    }

    @Override
    @Transacional
    public void delete(Long id) {
        if (id == null) {
            logger.error("Attempted to delete a task with null ID");
            throw new IllegalArgumentException("ID cannot be null");
        }
        Task task = em.find(Task.class, id);
        if (task != null) {
            logger.info("Deleting task with ID: {}", id);
            em.remove(task);
        } else {
            logger.warn("Task not found with ID: {}", id);
        }
    }

    @Override
    public Optional<Task> findById(Long id) {
        if (id == null) {
            logger.error("Attempted to find a task with null ID");
            throw new IllegalArgumentException("ID cannot be null");
        }
        Task task = em.find(Task.class, id);
        if (task == null) {
            logger.info("No task found for ID: {}", id);
        } else {
            logger.info("Found task: {}", task);
        }
        return Optional.ofNullable(task);
    }

    @Override
    public List<Task> findAll() {
        logger.info("Fetching all tasks");
        return em.createQuery("SELECT t FROM Task t", Task.class).getResultList();
    }
}