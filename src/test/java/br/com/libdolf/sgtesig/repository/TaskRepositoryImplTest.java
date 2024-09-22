package br.com.libdolf.sgtesig.repository;

import br.com.libdolf.sgtesig.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryImplTest {

    @InjectMocks
    private TaskRepositoryImpl taskRepository;

    @Mock
    private EntityManager em;

    @Mock
    private Logger logger;

    @Mock
    private TypedQuery<Task> mockedQuery;

    private Task task;

    @BeforeEach
    public void setUp() {
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description");
    }

    @Test
    public void testSave_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskRepository.save(null);
        });
        assertEquals("Task cannot be null", exception.getMessage());
        verify(logger).error("Attempted to save a null task");
    }

    @Test
    public void testSave_ValidTask() {
        taskRepository.save(task);
        verify(logger).info("Saving task: {}", task);
        verify(em).persist(task);
    }

    @Test
    public void testUpdate_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskRepository.update(null);
        });
        assertEquals("Task or its ID cannot be null", exception.getMessage());
        verify(logger).error("Attempted to update a null task or task without ID");
    }

    @Test
    public void testUpdate_ValidTask() {
        taskRepository.update(task);
        verify(logger).info("Updating task: {}", task);
        verify(em).merge(task);
    }

    @Test
    public void testDelete_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskRepository.delete(null);
        });
        assertEquals("ID cannot be null", exception.getMessage());
        verify(logger).error("Attempted to delete a task with null ID");
    }

    @Test
    public void testDelete_TaskFound() {
        when(em.find(Task.class, task.getId())).thenReturn(task);
        taskRepository.delete(task.getId());
        verify(logger).info("Deleting task with ID: {}", task.getId());
        verify(em).remove(task);
    }

    @Test
    public void testDelete_TaskNotFound() {
        when(em.find(Task.class, task.getId())).thenReturn(null);
        taskRepository.delete(task.getId());
        verify(logger).warn("Task not found with ID: {}", task.getId());
    }

    @Test
    public void testFindById_TaskFound() {
        when(em.find(Task.class, task.getId())).thenReturn(task);
        Optional<Task> foundTask = taskRepository.findById(task.getId());

        assertTrue(foundTask.isPresent());
        assertEquals(task, foundTask.get());
        verify(logger).info("Found task: {}", task);
    }

    @Test
    public void testFindById_TaskDoesNotExist() {
        when(em.find(Task.class, 1L)).thenReturn(null);
        Optional<Task> foundTask = taskRepository.findById(1L);

        assertFalse(foundTask.isPresent());
        verify(logger).info("No task found for ID: {}", 1L);
    }

    @Test
    public void testFindById_NullId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskRepository.findById(null);
        });

        assertEquals("ID cannot be null", exception.getMessage());
        verify(logger).error("Attempted to find a task with null ID");
    }

    @Test
    public void testFindAll_TasksFound() {
        List<Task> tasks = Arrays.asList(new Task(), new Task()); // Cria algumas instâncias de Task
        when(em.createQuery("SELECT t FROM Task t", Task.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(tasks);

        List<Task> foundTasks = taskRepository.findAll();

        assertEquals(2, foundTasks.size());
        assertEquals(tasks, foundTasks);
        verify(logger).info("Fetching all tasks");
    }

    @Test
    public void testFindAll_NoTasksFound() {
        List<Task> tasks = Collections.emptyList();
        when(em.createQuery("SELECT t FROM Task t", Task.class)).thenReturn(mockedQuery);
        when(mockedQuery.getResultList()).thenReturn(tasks);

        List<Task> foundTasks = taskRepository.findAll();

        assertTrue(foundTasks.isEmpty());
        verify(logger).info("Fetching all tasks");
    }
}
