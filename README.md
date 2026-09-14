# TaskList

This is a JavaFX application I built to practice managing tasks with a graphical user interface.

The project uses Maven to manage JavaFX and run the application.

## Features

- View tasks in a table
- Add a new task
- Update task information
- Choose due dates with a calendar picker
- Set task priority
- Mark tasks complete or pending
- Remove tasks with confirmation
- Prevent duplicate task IDs
- Validate required fields
- Save task data between program runs

## Technologies

- Java 23
- JavaFX
- Maven
- IntelliJ IDEA
- Git and GitHub
- CSV file storage

## Project Files

- `Task.java` represents a task and stores its details.
- `TaskManager.java` manages task operations.
- `TaskFileStorage.java` loads and saves task data.
- `TaskListApp.java` provides the JavaFX graphical interface.

## Running the Application

Open the project in IntelliJ IDEA and allow Maven to load the project.

Run the application from the Maven panel:

```text
Plugins → javafx → javafx:run
```

You can also run it from a terminal:

```text
mvn javafx:run
```

## Testing the Program

Try each button in the graphical interface:

1. Add a task with all required fields.
2. Try adding a duplicate task ID.
3. Select a task and update its information.
4. Leave a required field blank.
5. Mark a task complete.
6. Mark the task pending again.
7. Remove a task and test both confirmation options.
8. Close and reopen the application to confirm saved tasks remain.

## Automated Tests

The project uses JUnit 5 tests for task management and file storage.

Run the tests with Maven:

```text
mvn test
```

The tests verify adding tasks, finding tasks, updating tasks, removing tasks, completion status, duplicate-ID protection, and saving/loading task records.

## Data Storage

Task records are saved locally in `tasks.csv`.

This file is ignored by Git because it contains local task data.

## Developer
Joshua Allgood
