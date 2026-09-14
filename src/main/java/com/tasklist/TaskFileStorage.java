// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Saves and loads tasks from a local file.

package com.tasklist;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TaskFileStorage {

    private static final Path FILE_PATH =
            Paths.get("tasks.csv");

    // Save all tasks to the local file.
    public void saveTasks(List<Task> tasks) {
        List<String> lines = new ArrayList<>();

        for (Task task : tasks) {
            lines.add(
                    task.getId() + "|" +
                            task.getTitle() + "|" +
                            task.getDescription() + "|" +
                            task.getDueDate() + "|" +
                            task.getPriority() + "|" +
                            task.isCompleted()
            );
        }

        try {
            Files.write(FILE_PATH, lines);
        } catch (IOException e) {
            System.out.println(
                    "Could not save tasks: " + e.getMessage()
            );
        }
    }

    // Load tasks from the local file.
    public List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();

        if (!Files.exists(FILE_PATH)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(FILE_PATH);

            for (String line : lines) {
                String[] values = line.split("\\|", -1);

                if (values.length != 6) {
                    continue;
                }

                Task task = new Task(
                        Integer.parseInt(values[0]),
                        values[1],
                        values[2],
                        values[3],
                        values[4]
                );

                task.setCompleted(
                        Boolean.parseBoolean(values[5])
                );

                tasks.add(task);
            }
        } catch (IOException | NumberFormatException e) {
            System.out.println(
                    "Could not load tasks: " + e.getMessage()
            );
        }

        return tasks;
    }
}