package com.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.util.Duration;

public class App extends Application {

    private static Scene scene; // Class-level scene variable
    private TableView<EVAUnit> evaTable = new TableView<>();
    private TableView<Angel> angelTable = new TableView<>();
    private ObservableList<EVAUnit> evaUnits = FXCollections.observableArrayList();
    private ObservableList<Angel> angels = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) throws IOException {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label title = new Label("EVA Command Center");
        title.setStyle("-fx-font-size: 24px;");
        grid.add(title, 0, 0, 2, 1);

        Label warningLabel = new Label("Approaching Limits");
        warningLabel.getStyleClass().addAll("label", "danger", "blink"); // Add CSS classes
        grid.add(warningLabel, 1, 0);

        // Create the flashing effect
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> warningLabel.setOpacity(1)),
                new KeyFrame(Duration.seconds(0.5), e -> warningLabel.setOpacity(0)),
                new KeyFrame(Duration.seconds(1), e -> warningLabel.setOpacity(1)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Create Table for EVA Units
        TableColumn<EVAUnit, Integer> unitIDColumn = new TableColumn<>("Unit ID");
        unitIDColumn.setCellValueFactory(new PropertyValueFactory<>("unitID"));

        TableColumn<EVAUnit, String> pilotNameColumn = new TableColumn<>("Pilot Name");
        pilotNameColumn.setCellValueFactory(new PropertyValueFactory<>("pilotName"));

        TableColumn<EVAUnit, Integer> syncRateColumn = new TableColumn<>("Sync Rate");
        syncRateColumn.setCellValueFactory(new PropertyValueFactory<>("syncRate"));

        evaTable.getColumns().addAll(unitIDColumn, pilotNameColumn, syncRateColumn);

        // Create Table for Angels
        TableColumn<Angel, String> angelNameColumn = new TableColumn<>("Angel Name");
        angelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Angel, Double> angelPowerColumn = new TableColumn<>("Power Level");
        angelPowerColumn.setCellValueFactory(new PropertyValueFactory<>("powerLevel"));

        angelTable.getColumns().addAll(angelNameColumn, angelPowerColumn);

        grid.add(evaTable, 0, 2); // Add the EVA table to the grid
        grid.add(angelTable, 1, 2); // Add the Angel table to the grid

        Button deployButton = new Button("Deploy EVA Unit");
        grid.add(deployButton, 0, 1);

        Button battleButton = new Button("Start Battle");
        grid.add(battleButton, 1, 1);

        scene = new Scene(grid, 800, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setTitle("Evangelion Command System");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Test Data
        evaUnits.add(new EVAUnit(1, "Shinji Ikari", 95, "Progressive Knife", "Ready"));
        evaUnits.add(new EVAUnit(2, "Asuka Langley", 92, "Spear of Longinus", "Ready"));

        angels.add(new Angel("Sachiel", 50.5));
        angels.add(new Angel("Shamshel", 60.2));
        angels.add(new Angel("Ramiel", 80.0));
        angels.add(new Angel("Gaghiel", 70.0));
        angels.add(new Angel("Israfel", 65.0));
        angels.add(new Angel("Sandalphon", 55.0));
        angels.add(new Angel("Matarael", 45.0));
        angels.add(new Angel("Sahaquiel", 85.0));
        angels.add(new Angel("Ireul", 40.0));
        angels.add(new Angel("Leliel", 90.0));
        angels.add(new Angel("Bardiel", 75.0));
        angels.add(new Angel("Zeruel", 100.0));
        angels.add(new Angel("Arael", 80.0));
        angels.add(new Angel("Armisael", 85.0));
        angels.add(new Angel("Tabris", 95.0));

        evaTable.setItems(evaUnits);
        angelTable.setItems(angels);

        // Button actions
        deployButton.setOnAction(e -> {
            // Create the new EVA units
            EVAUnit newEVA = new EVAUnit(3, "Rei Ayanami", 99, "Sonic Glaive", "Ready");
            EVAUnit kaworuEVA = new EVAUnit(4, "Kaworu Nagisa", 99, "Spear of Cassius", "Ready");

            // Check if the new EVA units are already in the list
            boolean isReiAdded = evaUnits.stream().anyMatch(eva -> eva.getUnitID() == newEVA.getUnitID());
            boolean isKaworuAdded = evaUnits.stream().anyMatch(eva -> eva.getUnitID() == kaworuEVA.getUnitID());

            // Add them only if they are not already present
            if (!isReiAdded) {
                evaUnits.add(newEVA);
            }
            if (!isKaworuAdded) {
                evaUnits.add(kaworuEVA);
            }
        });

        battleButton.setOnAction(e -> {
            EVAUnit selectedEVA = evaTable.getSelectionModel().getSelectedItem();
            Angel selectedAngel = angelTable.getSelectionModel().getSelectedItem();

            if (selectedEVA != null && selectedAngel != null) {
                String result = startBattle(selectedEVA, selectedAngel);

                // Create an Alert to display the result
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Battle Result");
                alert.setHeaderText(null); // You can set a header text or leave it null
                alert.setContentText(result); // Set the battle result as the content

                alert.showAndWait(); // Show the alert and wait for the user to close it

                // Check if Shinji wins and play the video
                if (result.contains("EVA Unit 1")) {
                    playExternalVideo();
                }
            } else {
                // Alert if no EVA Unit or Angel is selected
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Selection Warning");
                alert.setHeaderText(null);
                alert.setContentText("Select an EVA Unit and an Angel to start a battle.");
                alert.showAndWait();
            }
        });

    }

    private String startBattle(EVAUnit eva, Angel angel) {
        if (eva.getSyncRate() > angel.getPowerLevel()) {
            return "EVA Unit " + eva.getUnitID() + " wins against " + angel.getName();
        } else {
            return "Angel " + angel.getName() + " wins!";
        }
    }

    public void playExternalVideo() {
        try {
            // Path to your AppleScript
            String scriptPath = " "; // Adjust
                                     // the
                                     // path
                                     // as
                                     // needed
            String videoPath = " ";

            ProcessBuilder processBuilder = new ProcessBuilder("osascript", scriptPath, videoPath);
            processBuilder.redirectErrorStream(true); // Merge error stream
            Process process = processBuilder.start();

            // Print output for debugging
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line); // Print any output for debugging
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
