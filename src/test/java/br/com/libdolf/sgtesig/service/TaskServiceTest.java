package br.com.libdolf.sgtesig.service;

import br.com.libdolf.sgtesig.model.Status;
import br.com.libdolf.sgtesig.model.Task;
import br.com.libdolf.sgtesig.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        task = new Task();
        task.setId(1L);
        task.setTitle("Test Task");
        task.setDescription("Description");
        task.setAssignee("User");
        task.setStatus(Status.IN_PROGRESS);
        task.setDeadline(new Date());
    }

    @Test
    public void testSaveNewTask_ValidTask() {
        taskService.saveNewTask(task);
        verify(taskRepository).save(task);
        assertEquals(Status.IN_PROGRESS, task.getStatus());
    }

    @Test
    public void testSaveNewTask_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.saveNewTask(null);
        });
        assertEquals("Task cannot be null", exception.getMessage());
    }

    @Test
    public void testUpdateTask_ValidTask() {
        taskService.updateTask(task);
        verify(taskRepository).update(task);
    }

    @Test
    public void testUpdateTask_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.updateTask(null);
        });
        assertEquals("Task or Task ID cannot be null", exception.getMessage());
    }

    @Test
    public void testDeleteTask_ValidTask() {
        taskService.deleteTask(task);
        verify(taskRepository).delete(task.getId());
    }

    @Test
    public void testDeleteTask_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.deleteTask(null);
        });
        assertEquals("Task or Task ID cannot be null", exception.getMessage());
    }

    @Test
    public void testFindAllTasks() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.findAllTasks();
        assertEquals(1, result.size());
        assertEquals(task, result.getFirst());
    }

    @Test
    public void testCompleteTask() {
        taskService.completeTask(task);
        verify(taskRepository).update(task);
        assertEquals(Status.COMPLETED, task.getStatus());
    }

    @Test
    public void testCompleteTask_NullTask() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            taskService.completeTask(null);
        });
        assertEquals("Task cannot be null", exception.getMessage());
    }

    @Test
    public void testSearchTasks_NoFilters() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.searchTasks(null, null, null, null, null);
        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
    }

    @Test
    public void testSearchTasks_WithFilters() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        when(taskRepository.findAll()).thenReturn(taskList);

        List<Task> result = taskService.searchTasks(1L, "Test", "User", null, Status.IN_PROGRESS);
        assertEquals(1, result.size());
    }

}