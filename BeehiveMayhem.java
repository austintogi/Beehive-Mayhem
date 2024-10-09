import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.geometry.Insets;

public class BeehiveMayhem extends Application {

    private Button hiddenButton;
    Label roundLabel;
    private int lives = 2; // Number of lives
    private Label livesLabel; // Lives label
    private int round = 1; // To keep track of the current round
    private List<Button> unselectedButtons = new ArrayList<>(); // List to store unselected buttons
    private int selectionCount = 0; // Counter to keep track of the number of selections
    private Stage primaryStage;
    Image fClick = new Image("file:beeMain.png");
    BackgroundImage fBackgroundimage = new BackgroundImage(fClick, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    Background fBackground = new Background(fBackgroundimage);
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        // Create the title label
        Label titleLabel = new Label("Beehive Mayhem");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;-fx-text-fill: red;");

        Image backgroundImage = new Image("file:main.png");
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        // Create the round label
        roundLabel = new Label("Round 1");
        roundLabel.setStyle("-fx-font-size: 18px;-fx-font-weight: bold;-fx-text-fill: red;");

        // Create the lives label
        livesLabel = new Label("Lives : " + lives);
        livesLabel.setStyle("-fx-font-size: 18px;-fx-font-weight: bold;-fx-text-fill: red;");

        // Create a 3x3 grid of buttons
        GridPane gridPane = new GridPane();
        gridPane.setHgap(14); // Horizontal gap between buttons
        gridPane.setVgap(14); // Vertical gap between buttons

        for (int i = 0; i < 3; i++) 
        {
            for (int j = 0; j < 3; j++) 
            {
                Button button = new Button("X"); // Button label initially set to "X"
                button.setMinSize(65, 65); // Button size

                button.setStyle("-fx-background-color: transparent; -fx-border-color: black; -fx-text-fill: transparent;");
        
                
                // Add button to unselectedButtons list
                unselectedButtons.add(button);

                // Add event handler to handle button click
                button.setOnAction(e -> handleButtonClick(button));

                gridPane.add(button, j, i); // Add button to grid pane
            }
        }

        // Randomly select one button to be the hidden button for round 1
        int hiddenRow = new Random().nextInt(3);
        int hiddenCol = new Random().nextInt(3);
        hiddenButton = (Button) gridPane.getChildren().get(hiddenRow * 3 + hiddenCol);
        hiddenButton.setText("x");

        // Remove the selected button from unselectedButtons list
        unselectedButtons.remove(hiddenButton);

        // Create the "Stop" button
        //Button stopButton = new Button("Stop");
        //stopButton.setMinWidth(100); // Set minimum width for the button
        //stopButton.setOnAction(e -> stopGame());

        // Create a VBox to hold the title, round label, lives label, grid of buttons, and "Stop" button
        GridPane pane = new GridPane(); // 20 pixels spacing between nodes
        pane.setPadding(new Insets(10));
        pane.setHgap(5);
        pane.setVgap(18);
        pane.add(titleLabel,25,0);
        pane.add(roundLabel,25,1);
        pane.add(livesLabel,25,2);
        pane.add(gridPane,30,10);
        //pane.getChildren().addAll(titleLabel, roundLabel, livesLabel, gridPane);
        //pane.setStyle("-fx-padding: 50px; -fx-alignment: center;");
        pane.setBackground(new Background(background));

        // Create the scene and set it on the stage
        Scene scene = new Scene(pane, 707, 709); // Increased height to accommodate the "Stop" button
        primaryStage.setTitle("Beehive Mayhem");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    private void handleButtonClick(Button button) {
        button.setDisable(true); // Disable the selected button
        selectionCount++; // Increment the selection counter

        if (lives > 0) 
        {
            if ("x".equals(button.getText())) 
            {
                button.setBackground((fBackground));
                lives--;

                // Update lives label
                livesLabel.setText("Lives : " + lives);

                if (lives == 0) 
                {
                    // Disable all buttons when lives become 0
                    disableAllButtons();
                }
            } 
            else 
            {
                if (button == hiddenButton) 
                {
                    button.setText("N");
                    button.setBackground((fBackground));
                    lives--;

                    // Update lives label
                    livesLabel.setText("Lives : " + lives);

                    if (lives == 0) 
                    {
                        // Disable all buttons when lives become 0
                        disableAllButtons();
                    }
                } 
                else 
                {
                    button.setText("Y");
                    unselectedButtons.remove(button); // Remove the selected button from the unselectedButtons list
                }
            }
        }

        // Check the current round and update the round label accordingly
        if (round == 1) {
            roundLabel.setText("Round 2");
            round++;
        } else if (round == 2) {
            roundLabel.setText("Round 3");
            round++;
        } else if (round == 3) {
            roundLabel.setText("Round 4");
            round++;
        } else if (round == 4) {
            roundLabel.setText("Round 5");
            round++;
        } else if (round == 5 && lives > 0) {
            // Disable all buttons after round 5
            //disableAllButtons();
            round++;
        }

        // Set one more box to "N" among the remaining unselected boxes in round 2
        if (round == 2 && lives > 0) {
            setBoxToN();
        }

        // Set one more box to "N" among the remaining unselected boxes in round 3
        if (round == 3 && lives > 0) {
            setBoxToN();
        }

        // Set one more box to "N" among the remaining unselected boxes in round 4
        if (round == 4 && lives > 0) {
            setBoxToN();
        }
        
        if (round == 5 && lives > 0) {
            setBoxToN();
        }
        
        // Check if the user finishes 2 rounds and has a lives count of 0
        if (round == 3 && lives == 0) {
            displayWinPopup("You have won 250 vBucks");
        }

        // Check if the user finishes 3 rounds and has a lives count of 0
        if (round == 4 && lives == 0) {
            displayWinPopup("You have won 500 vBucks");
        }

        if (round > 5 && lives == 1) {
            displayWinPopup("You have won 1000 vBucks");
        }
        
        if (round > 5 && lives == 2) {
            displayWinPopup("You have won 2000 vBucks");
        }
        // Check if the user finishes 4 rounds and has a lives count of 0
        if (round >= 5 && lives == 0) {
            displayWinPopup("You have won 750 vBucks");
        }
        
        
    }

    private void disableAllButtons() {
        for (Button btn : unselectedButtons) {
            btn.setDisable(true);
        }
    }

    private void setBoxToN() {
        int randomIndex = new Random().nextInt(unselectedButtons.size());
        Button randomButton = unselectedButtons.get(randomIndex);
        randomButton.setText("x");
        unselectedButtons.remove(randomButton);
    }
    
    private void stopGame() {
        disableAllButtons(); // Disable all buttons
        displayPopup("You stopped here"); // Display popup with the message "You stopped here"
        primaryStage.close(); // Close the primary stage
    }

    private void displayPopup(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void displayWinPopup(String message) 
    {
        primaryStage.close();
        displayPopup(message);
    }
}
   
