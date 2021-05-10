package sample;

import javafx.application.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.print.PrinterJob;

public class Main extends Application {

    private final TableView<Task> tableChron = new TableView<Task>();
    private final ObservableList<Task> data =
            FXCollections.observableArrayList(
                    new Task("Friday", "12:00pm", "Tomorrow", "Mom", "Do ASAP"),
                    new Task("Monday", "9:38am", "May 10", "Dr Ruslan", "Finish on time"));

    final HBox hb = new HBox();
    Label response;

    private Desktop desktop = Desktop.getDesktop();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  throws Exception{

        VBox vBox1 = new VBox();
        vBox1.setStyle("-fx-padding: 16;");
        Scene scene1 = new Scene(vBox1);
        scene1.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        response = new Label("Menu");

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("_File");
        MenuItem open = new MenuItem("Open");
        MenuItem save = new MenuItem("Save As");
        MenuItem print = new MenuItem("Print");
        MenuItem exit = new MenuItem("Exit");
        SeparatorMenuItem separator = new SeparatorMenuItem();

        fileMenu.getItems().add(open);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(save);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(print);
        fileMenu.getItems().add(new SeparatorMenuItem());
        fileMenu.getItems().add(exit);

        menuBar.getMenus().add(fileMenu);

        EventHandler<ActionEvent> MEHandler =
                new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent ae) {
                        String name = ((MenuItem)ae.getTarget()).getText();

                        if(name.equals("Exit")) Platform.exit();

                        response.setText( name + " selected");
                    }
                };

        open.setOnAction(MEHandler);
        save.setOnAction(MEHandler);
        print.setOnAction(MEHandler);
        exit.setOnAction(MEHandler);

        final FileChooser fileChooser = new FileChooser();

        open.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(final ActionEvent e) {
                        File opensFile = fileChooser.showOpenDialog(primaryStage);
                        if (opensFile != null) {
                            openFile(opensFile);
                        }
                    }

                });

        save.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e) {
                        File savesFile = fileChooser.showSaveDialog(primaryStage);
                        if (savesFile != null) {
                            saveFile(savesFile);
                        }
                    }

                });

        print.setOnAction(
                new EventHandler<ActionEvent>(){
                    @Override
                    public void handle(ActionEvent e) {
                        PrinterJob job = PrinterJob.createPrinterJob();
                        if (job != null) {
                            boolean showPrintDialog = job.showPrintDialog(primaryStage.getOwner());
                            if(showPrintDialog){
                                job.endJob();
                            }
                        }
                    }
                });

        final Label label = new Label("To Do List");

        tableChron.setEditable(true);

        TableColumn dayCol = new TableColumn("Date");
        TableColumn timeCol = new TableColumn("Time");
        TableColumn deadlineCol = new TableColumn("Deadline");
        TableColumn mentorCol = new TableColumn("Boss");
        TableColumn descriptionCol = new TableColumn("Task Description");

        dayCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Day"));
        dayCol.setCellFactory(TextFieldTableCell.forTableColumn());
        dayCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDay(t.getNewValue());
                    }
                }
        );

        timeCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Time"));
        timeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        timeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTime(t.getNewValue());
                    }
                }
        );

        deadlineCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Deadline"));
        deadlineCol.setCellFactory(TextFieldTableCell.forTableColumn());
        deadlineCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDeadline(t.getNewValue());
                    }
                }
        );

        mentorCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Mentor"));
        mentorCol.setCellFactory(TextFieldTableCell.forTableColumn());
        mentorCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setMentor(t.getNewValue());
                    }
                }
        );

        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Task, String>("Description"));
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Task, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Task, String> t) {
                        ((Task) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }
        );

        tableChron.setItems(data);
        tableChron.getColumns().addAll( dayCol, timeCol, deadlineCol, mentorCol, descriptionCol);

        tableChron.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        HBox hbox1 = new HBox();
        hbox1.setStyle("-fx-padding: 30 0 0 0;");

        TextField addDay = new TextField();
        TextField addTime = new TextField();
        TextField addDeadline = new TextField();
        TextField addMentor = new TextField();
        TextField addDescription = new TextField();

        Button btnAdd = new Button("Add To List");
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                data.add(new Task(
                        addDay.getText(),
                        addTime.getText(),
                        addDeadline.getText(),
                        addMentor.getText(),
                        addDescription.getText()));
                addDay.clear();
                addTime.clear();
                addDeadline.clear();
                addMentor.clear();
                addDescription.clear();
            }
        });

        hbox1.getChildren().addAll(addDay, addTime, addDeadline, addMentor, addDescription, btnAdd);

        vBox1.getChildren().addAll(menuBar, label, tableChron, hbox1);

        primaryStage.setTitle("To Do List (Final)");
        primaryStage.setScene (scene1);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static class Task {

        private final SimpleStringProperty day;
        private final SimpleStringProperty time;
        private final SimpleStringProperty deadline;
        private final SimpleStringProperty mentor;
        private final SimpleStringProperty description;

        private Task(String day1, String time1, String deadline1, String mentor1, String description1){

            this.day = new SimpleStringProperty (day1);
            this.time = new SimpleStringProperty(time1);
            this.deadline = new SimpleStringProperty(deadline1);
            this.mentor = new SimpleStringProperty(mentor1);
            this.description = new SimpleStringProperty(description1);
        }

        public String getDay() {
            return day.get();
        }

        public void setDay(String day1) {
            day.set(day1);
        }

        public String getTime() {
            return time.get();
        }

        public void setTime(String time1) {
            time.set(time1);
        }

        public String getDeadline() {
            return deadline.get();
        }

        public void setDeadline(String deadline1) {
            deadline.set(deadline1);
        }

        public String getMentor() {
            return mentor.get();
        }

        public void setMentor(String mentor1) {
            mentor.set(mentor1);
        }

        public String getDescription() {
            return description.get();
        }

        public void setDescription(String deadline1) {
            description.set(deadline1);
        }

    }

    private void openFile(File opensFile) {
        try{
            desktop.open(opensFile);
        } catch (IOException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

    private void saveFile(File savesFile) {
        try{
            desktop.open(savesFile);
        } catch (IOException ex) {
            Logger.getLogger(
                    Main.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }
}