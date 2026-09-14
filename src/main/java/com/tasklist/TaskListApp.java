// Developer: Joshua Allgood
// Date: September 14, 2026
// Purpose: Displays and manages tasks in a JavaFX interface.

package com.tasklist;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class TaskListApp extends Application {
    // Manage tasks and connect them to the table.
    private final TaskManager manager = new TaskManager();
    private final ObservableList<Task> taskItems =
            FXCollections.observableArrayList();

    // Build the JavaFX window.
    @Override
    public void start(Stage stage) {
        loadSampleTasks();

        TableView<Task> taskTable = createTaskTable();
        taskTable.setItems(taskItems);

        Button completeButton = new Button("Mark Complete");
        Button removeButton = new Button("Remove");
        Button refreshButton = new Button("Refresh");
        Button addButton = new Button("Add");
        addButton.setOnAction(event -> addTask());
        Button updateButton = new Button("Update");
        updateButton.setOnAction(event -> updateTask(taskTable));

        completeButton.setOnAction(event -> {
            Task selectedTask =
                    taskTable.getSelectionModel().getSelectedItem();

            if (selectedTask == null) {
                showAlert("Select a task first.");
                return;
            }

            boolean newStatus = !selectedTask.isCompleted();

            manager.setTaskCompleted(
                    selectedTask.getId(),
                    newStatus
            );

            taskTable.refresh();

            showInfo(
                    newStatus
                            ? "Task marked complete."
                            : "Task marked pending."
            );
        });

        removeButton.setOnAction(event -> {
            Task selectedTask =
                    taskTable.getSelectionModel().getSelectedItem();

            if (selectedTask == null) {
                showAlert("Select a task to remove.");
                return;
            }

            Alert confirmation = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "Remove task \"" + selectedTask.getTitle() + "\"?",
                    ButtonType.OK,
                    ButtonType.CANCEL
            );

            confirmation.setTitle("Confirm Removal");
            confirmation.setHeaderText("Remove Task");

            ButtonType result = confirmation.showAndWait()
                    .orElse(ButtonType.CANCEL);

            if (result == ButtonType.OK) {
                boolean removed =
                        manager.removeTaskById(selectedTask.getId());

                if (removed) {
                    refreshTasks();
                    showInfo("Task removed successfully.");
                }
            }
        });

        refreshButton.setOnAction(event -> refreshTasks());

        HBox buttonBar = new HBox(
                10,
                addButton,
                updateButton,
                completeButton,
                removeButton,
                refreshButton
        );

        Label title = new Label("Task List");

        BorderPane layout = new BorderPane();
        layout.setTop(title);
        layout.setCenter(taskTable);
        layout.setBottom(buttonBar);

        Scene scene = new Scene(layout, 850, 500);

        stage.setTitle("Task List");
        stage.setScene(scene);
        stage.show();
    }

    // Create the columns shown in the task table.
    private TableView<Task> createTaskTable() {
        TableView<Task> table = new TableView<>();

        TableColumn<Task, Number> idColumn =
                new TableColumn<>("ID");
        idColumn.setCellValueFactory(cell ->
                new SimpleIntegerProperty(
                        cell.getValue().getId()
                )
        );

        TableColumn<Task, String> titleColumn =
                new TableColumn<>("Title");
        titleColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getTitle()
                )
        );

        TableColumn<Task, String> dueDateColumn =
                new TableColumn<>("Due Date");
        dueDateColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getDueDate()
                )
        );

        TableColumn<Task, String> priorityColumn =
                new TableColumn<>("Priority");
        priorityColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getPriority()
                )
        );

        TableColumn<Task, String> statusColumn =
                new TableColumn<>("Status");
        statusColumn.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().isCompleted()
                                ? "Complete"
                                : "Pending"
                )
        );

        table.getColumns().addAll(
                idColumn,
                titleColumn,
                dueDateColumn,
                priorityColumn,
                statusColumn
        );

        table.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        return table;
    }

    // Add sample tasks for the first interface test.
    private void loadSampleTasks() {
        if (manager.getTasks().isEmpty()) {
            manager.addTask(new Task(
                    1,
                    "Finish project outline",
                    "Write the first draft",
                    "2026-09-20",
                    "High"
            ));

            manager.addTask(new Task(
                    2,
                    "Review JavaFX basics",
                    "Practice TableView controls",
                    "2026-09-22",
                    "Medium"
            ));

            refreshTasks();
        }
    }

    // Refresh the observable table data.
    private void refreshTasks() {
        taskItems.setAll(manager.getTasks());
    }

    // Display a structured form for adding a task.
    private void addTask() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Task");

        ButtonType addButtonType = new ButtonType(
                "Add",
                ButtonBar.ButtonData.OK_DONE
        );

        dialog.getDialogPane().getButtonTypes().addAll(
                addButtonType,
                ButtonType.CANCEL
        );

        TextField idField = new TextField();
        TextField titleField = new TextField();
        TextArea descriptionField = new TextArea();
        TextField dueDateField = new TextField();

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High");
        priorityBox.setValue("Medium");

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));

        form.add(new Label("ID:"), 0, 0);
        form.add(idField, 1, 0);

        form.add(new Label("Title:"), 0, 1);
        form.add(titleField, 1, 1);

        form.add(new Label("Description:"), 0, 2);
        form.add(descriptionField, 1, 2);

        form.add(new Label("Due Date:"), 0, 3);
        form.add(dueDateField, 1, 3);

        form.add(new Label("Priority:"), 0, 4);
        form.add(priorityBox, 1, 4);

        dialog.getDialogPane().setContent(form);

        Button addButton = (Button) dialog.getDialogPane()
                .lookupButton(addButtonType);

        addButton.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    int id;

                    try {
                        id = Integer.parseInt(idField.getText().trim());
                    } catch (NumberFormatException e) {
                        showAlert("Task ID must be a number.");
                        idField.requestFocus();
                        event.consume();
                        return;
                    }

                    String title = titleField.getText().trim();
                    String description = descriptionField.getText().trim();
                    String dueDate = dueDateField.getText().trim();
                    String priority = priorityBox.getValue();

                    if (title.isEmpty()) {
                        showAlert("Title is required.");
                        titleField.requestFocus();
                        event.consume();
                        return;
                    }

                    if (description.isEmpty()) {
                        showAlert("Description is required.");
                        descriptionField.requestFocus();
                        event.consume();
                        return;
                    }

                    if (dueDate.isEmpty()) {
                        showAlert("Due date is required.");
                        dueDateField.requestFocus();
                        event.consume();
                        return;
                    }

                    if (manager.findTaskById(id) != null) {
                        showAlert("A task with that ID already exists.");
                        idField.requestFocus();
                        event.consume();
                        return;
                    }

                    manager.addTask(new Task(
                            id,
                            title,
                            description,
                            dueDate,
                            priority
                    ));

                    refreshTasks();
                }
        );

        dialog.showAndWait();
    }

    // Display an error message.
    private void showAlert(String message) {
        Alert alert = new Alert(
                Alert.AlertType.ERROR,
                message,
                ButtonType.OK
        );
        alert.setTitle("Invalid Task");
        alert.showAndWait();
    }

    // Display a successful operation message.
    private void showInfo(String message) {
        Alert alert = new Alert(
                Alert.AlertType.INFORMATION,
                message,
                ButtonType.OK
        );

        alert.setTitle("Task List");
        alert.showAndWait();
    }

    // Display a form for updating the selected task.
    private void updateTask(TableView<Task> taskTable) {
        Task selectedTask =
                taskTable.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            showAlert("Select a task to update.");
            return;
        }

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Update Task");

        ButtonType updateButtonType = new ButtonType(
                "Update",
                ButtonBar.ButtonData.OK_DONE
        );

        dialog.getDialogPane().getButtonTypes().addAll(
                updateButtonType,
                ButtonType.CANCEL
        );

        TextField titleField =
                new TextField(selectedTask.getTitle());

        TextArea descriptionField =
                new TextArea(selectedTask.getDescription());

        TextField dueDateField =
                new TextField(selectedTask.getDueDate());

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("Low", "Medium", "High");
        priorityBox.setValue(selectedTask.getPriority());

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.setPadding(new Insets(15));

        form.add(new Label("Title:"), 0, 0);
        form.add(titleField, 1, 0);

        form.add(new Label("Description:"), 0, 1);
        form.add(descriptionField, 1, 1);

        form.add(new Label("Due Date:"), 0, 2);
        form.add(dueDateField, 1, 2);

        form.add(new Label("Priority:"), 0, 3);
        form.add(priorityBox, 1, 3);

        dialog.getDialogPane().setContent(form);

        Button updateButton = (Button) dialog.getDialogPane()
                .lookupButton(updateButtonType);

        updateButton.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    String title = titleField.getText().trim();
                    String description =
                            descriptionField.getText().trim();
                    String dueDate = dueDateField.getText().trim();
                    String priority = priorityBox.getValue();

                    if (title.isEmpty()) {
                        showAlert("Title is required.");
                        titleField.requestFocus();
                        event.consume();
                        return;
                    }

                    if (description.isEmpty()) {
                        showAlert("Description is required.");
                        descriptionField.requestFocus();
                        event.consume();
                        return;
                    }

                    if (dueDate.isEmpty()) {
                        showAlert("Due date is required.");
                        dueDateField.requestFocus();
                        event.consume();
                        return;
                    }

                    boolean updated = manager.updateTask(
                            selectedTask.getId(),
                            title,
                            description,
                            dueDate,
                            priority
                    );

                    if (updated) {
                        refreshTasks();
                        showInfo("Task updated successfully.");
                    }
                }
        );

        dialog.showAndWait();
    }

    // Launch the JavaFX application.
    public static void main(String[] args) {
        launch();
    }
}