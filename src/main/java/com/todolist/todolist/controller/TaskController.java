package com.todolist.todolist.controller;

import com.google.gson.JsonObject;
import com.todolist.todolist.models.Task;
import com.todolist.todolist.services.TaskService;
import jakarta.validation.Valid;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/")
    public ResponseEntity getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @PostMapping("/")
    public ResponseEntity addTask(HttpEntity<String> httpEntity) {
        JSONObject newTaskInfo = new JSONObject(httpEntity.getBody());
        Task newTask = new Task(
                newTaskInfo.getString("title"),
                newTaskInfo.getString("description"),
                newTaskInfo.getBoolean("done")
        );
        return ResponseEntity.ok(taskService.addTask(newTask));
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTask(@PathVariable Long id, HttpEntity<String> httpEntity) {
        JSONObject newTaskInfo = new JSONObject(httpEntity.getBody());
        Task task = taskService.getTask(id);

        task.setTitle(newTaskInfo.getString("title"));
        task.setDescription(newTaskInfo.getString("description"));
        task.setDone(newTaskInfo.getBoolean("done"));

        return ResponseEntity.ok(taskService.updateTask(task));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task deleted");
    }

}
