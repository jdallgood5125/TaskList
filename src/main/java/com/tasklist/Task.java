// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Represents a task and stores its details.

package com.tasklist;

public class Task {
    // Store the task information.
    private int id;
    private String title;
    private String description;
    private String dueDate;
    private String priority;
    private boolean completed;

    // Create a new task.
    public Task(
            int id,
            String title,
            String description,
            String dueDate,
            String priority
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completed = false;
    }

    // Return the task ID.
    public int getId() {
        return id;
    }

    // Return and update the task title.
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Return and update the task description.
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Return and update the due date.
    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    // Return and update the priority.
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    // Return and update completion status.
    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    // Return a readable task summary.
    @Override
    public String toString() {
        return id + " | "
                + title + " | "
                + priority + " | "
                + (completed ? "Complete" : "Pending");
    }
}