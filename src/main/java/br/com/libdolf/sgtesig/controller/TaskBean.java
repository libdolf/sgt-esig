package br.com.libdolf.sgtesig.controller;

import br.com.libdolf.sgtesig.model.Priority;
import br.com.libdolf.sgtesig.model.Status;
import br.com.libdolf.sgtesig.model.Task;
import br.com.libdolf.sgtesig.service.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@ViewScoped
@Named
@Data
public class TaskBean implements Serializable {

    @Inject
    private TaskService taskService;

    private Long searchId;
    private String searchText;
    private String selectedAssignee;
    private Status selectedStatus;
    private Date selectedDeadline;

    private List<Task> taskList;
    private List<String> assigneeList;
    private List<Status> statusList;
    private List<Priority> priorityList;

    private Task newTask;
    private Task editedTask;
    private Task selectedTask;

    @PostConstruct
    public void init() {
        refreshTaskList();
        this.assigneeList = List.of("João", "Antônio");
        this.priorityList = Arrays.asList(Priority.values());
        this.statusList = Arrays.asList(Status.values());
        this.newTask = new Task();
        this.editedTask = new Task();
    }

    public void searchTasks() {
        taskList = taskService.searchTasks(searchId, searchText, selectedAssignee, selectedDeadline, selectedStatus);
    }

    public void saveNewTask() {
        taskService.saveNewTask(newTask);
        newTask = new Task();
        refreshTaskList();
    }

    public void prepareEdit(Task task) {
        editedTask = task;
    }

    public void update() {
        taskService.updateTask(editedTask);
        refreshTaskList();
    }

    public void delete(Task task) {
        taskService.deleteTask(task);
        refreshTaskList();
    }

    public void completeTask(Task task) {
        taskService.completeTask(task);
        refreshTaskList();
    }

    public void prepareView(Task task) {
        selectedTask = task;
    }

    public void refreshTaskList() {
        taskList = taskService.findAllTasks();
    }
}
