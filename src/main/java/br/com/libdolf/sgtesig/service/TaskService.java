package br.com.libdolf.sgtesig.service;

import br.com.libdolf.sgtesig.model.Status;
import br.com.libdolf.sgtesig.model.Task;
import br.com.libdolf.sgtesig.repository.TaskRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class TaskService {

    @Inject
    private TaskRepository taskRepository;

    public void saveNewTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        task.setStatus(Status.IN_PROGRESS);
        taskRepository.save(task);
    }

    public void updateTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new IllegalArgumentException("Task or Task ID cannot be null");
        }
        taskRepository.update(task);
    }

    public void deleteTask(Task task) {
        if (task == null || task.getId() == null) {
            throw new IllegalArgumentException("Task or Task ID cannot be null");
        }
        taskRepository.delete(task.getId());
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }

    public void completeTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        task.setStatus(Status.COMPLETED);
        taskRepository.update(task);
    }

    public List<Task> searchTasks(Long searchId, String searchTerm, String assignee, Date selectedDeadline, Status status) {
        List<Task> tasks = taskRepository.findAll();

        return tasks.stream()
                .filter(task -> (searchId == null || task.getId().equals(searchId)) &&
                        (searchTerm == null || searchTerm.isEmpty() ||
                                task.getTitle().contains(searchTerm) ||
                                task.getDescription().contains(searchTerm)) &&
                        (assignee == null || assignee.isEmpty() || task.getAssignee().equals(assignee)) &&
                        (status == null || task.getStatus().equals(status)) &&
                        (selectedDeadline == null || task.getDeadline().compareTo(selectedDeadline) == 0)
                )
                .sorted(Comparator.comparing(Task::getId))
                .collect(Collectors.toList());
    }
}
