package com.todo.todoapp.repository;

import com.todo.todoapp.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByReminderDateTimeBeforeAndCompleted(LocalDateTime dateTime, boolean completed);
}
