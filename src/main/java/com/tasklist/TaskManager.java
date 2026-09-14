// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Manages tasks and provides task operations.

package com.tasklist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TaskManager {
    // Store tasks in memory.
    private final List<Task> tasks = new ArrayList<>();

    // Add a task when its ID is unique.
    public boolean addTask(Task task) {
        if (findTaskById(task.getId()) != null) {
            return false;
        }

        tasks.add(task);
        return true;
    }

    // Return tasks sorted by numerical ID.
    public List<Task> getTasks() {
        tasks.sort(
                Comparator.comparingInt(Task::getId)
        );

        return tasks;
    }

    // Find a task by ID.
    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }

        return null;
    }

    // Update an existing task.
    public boolean updateTask(
            int id,
            String title,
            String description,
            String dueDate,
            String priority
    ) {
        Task task = findTaskById(id);

        if (task == null) {
            return false;
        }

        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);

        return true;
    }

    // Remove a task by ID.
    public boolean removeTaskById(int id) {
        Task task = findTaskById(id);

        if (task == null) {
            return false;
        }

        tasks.remove(task);
        return true;
    }

    // Mark a task complete or pending.
    public boolean setTaskCompleted(int id, boolean completed) {
        Task task = findTaskById(id);

        if (task == null) {
            return false;
        }

        task.setCompleted(completed);
        return true;
    }
}