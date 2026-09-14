package com.tasklist;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskFileStorageTest {

    @Test
    void saveAndLoadTasks() throws Exception {
        TaskFileStorage storage = new TaskFileStorage();

        Task task = new Task(
                1,
                "Saved task",
                "Stored description",
                "9/25/2026",
                "High"
        );

        task.setCompleted(true);

        storage.saveTasks(List.of(task));

        List<Task> loadedTasks = storage.loadTasks();

        assertEquals(1, loadedTasks.size());
        assertEquals("Saved task", loadedTasks.get(0).getTitle());
        assertTrue(loadedTasks.get(0).isCompleted());

        Files.deleteIfExists(Path.of("tasks.csv"));
    }
}