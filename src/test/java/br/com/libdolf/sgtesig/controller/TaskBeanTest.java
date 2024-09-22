package br.com.libdolf.sgtesig.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import br.com.libdolf.sgtesig.model.Task;
import br.com.libdolf.sgtesig.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TaskBeanTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskBean taskBean;

    private Task newTask;
    private Task editedTask;

    @BeforeEach
    public void setUp() {
        newTask = new Task();
        newTask.setTitle("New Task");

        editedTask = new Task();
        editedTask.setId(1L);
        editedTask.setTitle("Edited Task");

        List<Task> taskList = Arrays.asList(newTask, editedTask);
    }

    @Test
    public void testSearchTasks_WithSearchText() {
        taskBean.setSearchText("Task");
        taskBean.searchTasks();
        verify(taskService).searchTasks(null, "Task", null, null, null);
    }

    @Test
    public void testSaveNewTask() {
        taskBean.setNewTask(newTask);
        taskBean.saveNewTask();
        verify(taskService).saveNewTask(newTask);
        verify(taskService).findAllTasks();
    }

    @Test
    public void testUpdateTask() {
        taskBean.prepareEdit(editedTask);
        taskBean.update();
        verify(taskService).updateTask(editedTask);
        verify(taskService).findAllTasks();
    }

    @Test
    public void testDeleteTask() {
        taskBean.delete(editedTask);
        verify(taskService).deleteTask(editedTask);
        verify(taskService).findAllTasks();
    }

    @Test
    public void testCompleteTask() {
        taskBean.completeTask(editedTask);
        verify(taskService).completeTask(editedTask);
        verify(taskService).findAllTasks();
    }

    @Test
    public void testPrepareView() {
        taskBean.prepareView(editedTask);
        assertEquals(editedTask, taskBean.getSelectedTask());
    }
}
