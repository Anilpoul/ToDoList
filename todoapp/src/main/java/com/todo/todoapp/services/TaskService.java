package com.todo.todoapp.services;

import com.todo.todoapp.models.Task;
import com.todo.todoapp.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAlltasks() {
        return taskRepository.findAll();
    }

    public void createTask(String title, String reminderDateTime) {
        Task task = new Task();
        task.setTitle(title);
        task.setCompleted(false);
        if(reminderDateTime != null && !reminderDateTime.isEmpty()){
            task.setReminderDateTime(LocalDateTime.parse(reminderDateTime));
        }
        taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public void toggleTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Invalid Task Id"));
        task.setCompleted(!task.isCompleted());
        taskRepository.save(task);
    }

    public void editTask(Long id, String title) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task ID:" + id));
        task.setTitle(title);
        taskRepository.save(task);
    }
}
