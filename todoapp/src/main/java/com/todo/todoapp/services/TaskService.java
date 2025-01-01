package com.todo.todoapp.services;

import com.todo.todoapp.models.Task;
import com.todo.todoapp.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
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

    @Scheduled(fixedRate = 60000) // Runs every 60 seconds
    public void deleteExpiredTasks() {
        LocalDateTime now = LocalDateTime.now();
        List<Task> expiredTasks = taskRepository.findAllByReminderDateTimeBeforeAndCompleted(now, true);

        if (!expiredTasks.isEmpty()) {
            taskRepository.deleteAll(expiredTasks);
            System.out.println("Deleted expired tasks: " + expiredTasks.size());
        }
    }
}
