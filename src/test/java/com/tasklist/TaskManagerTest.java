package com.tasklist;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @Test
    void addAndFindTask() {
        TaskManager manager = new TaskManager();
        Task task = new Task(
                1,
                "Test task",
                "Test description",
                "9/20/2026",
                "High"
        );

        assertTrue(manager.addTask(task));
        assertEquals(task, manager.findTaskById(1));
    }

    @Test
    void rejectDuplicateId() {
        TaskManager manager = new TaskManager();

        assertTrue(manager.addTask(new Task(
                1, "First", "Description",
                "9/20/2026", "High"
        )));

        assertFalse(manager.addTask(new Task(
                1, "Duplicate", "Description",
                "9/21/2026", "Low"
        )));
    }

    @Test
    void updateTask() {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(
                1, "Old title", "Old description",
                "9/20/2026", "Low"
        ));

        assertTrue(manager.updateTask(
                1,
                "New title",
                "New description",
                "9/22/2026",
                "High"
        ));

        Task task = manager.findTaskById(1);

        assertEquals("New title", task.getTitle());
        assertEquals("High", task.getPriority());
    }

    @Test
    void removeTask() {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(
                1, "Task", "Description",
                "9/20/2026", "Medium"
        ));

        assertTrue(manager.removeTaskById(1));
        assertNull(manager.findTaskById(1));
    }

    @Test
    void toggleCompletion() {
        TaskManager manager = new TaskManager();

        manager.addTask(new Task(
                1, "Task", "Description",
                "9/20/2026", "Medium"
        ));

        assertTrue(manager.setTaskCompleted(1, true));
        assertTrue(manager.findTaskById(1).isCompleted());
    }
}