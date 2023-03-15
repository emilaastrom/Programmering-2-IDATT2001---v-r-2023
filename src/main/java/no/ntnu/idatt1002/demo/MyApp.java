package no.ntnu.idatt1002.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * Use this class to start the application
 * @author nilstes
 */
public class MyApp extends Application {

    /**
     * Main method for my application
     */
    public static void main(String[] args) throws Exception {
        launch(args);
   }

    @Override
    public void start(Stage stage) throws Exception {
        //Disabled FXML SceneBuilder - Loading the FXML file
        //FXMLLoader fxmlLoader = new FXMLLoader(MyApp.class.getResource("main.fxml"));
        //Setting and displaying the scene (FXML SceneBuilder)
        //Scene scene = new Scene(fxmlLoader.load(), 800, 600);



        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: rgba(100,148,76,0.38)");

        //HBox for current page title
        HBox titleBox = new HBox();
        Text titleText = new Text();
        titleText.setText("Oversikt");
        titleText.setStyle("-fx-font-size: 24;" +
                "-fx-text-fill: #116c75;" +
                "-fx-alignment: center;" +
                "-fx-font-weight: bold;");

        titleBox.setAlignment(javafx.geometry.Pos.CENTER);
        titleBox.setStyle("-fx-background-color: #e5e5e5;" + "-fx-padding: 10;");
        titleBox.getChildren().add(titleText);
        root.setTop(titleBox);

        //VBox for menus
        VBox navigationMenu = new VBox();
        navigationMenu.setStyle("-fx-background-color: #ffffff;" + "-fx-padding: 10;");
        String buttonStyle = "-fx-font-size: 16;" +
                "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #116c75;" +
                "-fx-pref-width: 150;" +
                "-fx-pref-height: 50;" +
                "-fx-highlight-fill: #116c75;" +
                "-fx-highlight-text-fill: #ffffff;";
        Button overviewButton = new Button("Oversikt");
        overviewButton.setStyle(buttonStyle);
        Button accountButton = new Button("Konto");
        accountButton.setStyle(buttonStyle);
        Button incomeButton = new Button("Inntekter");
        incomeButton.setStyle(buttonStyle);
        Button expensesButton = new Button("Utgifter");
        expensesButton.setStyle(buttonStyle);
        Button savingsButton = new Button("Sparemål");
        savingsButton.setStyle(buttonStyle);
        Button settingsButton = new Button("Innstillinger");
        settingsButton.setStyle(buttonStyle);
        Button helpButton = new Button("Hjelp");
        helpButton.setStyle(buttonStyle);
        Button loggUtButton = new Button("Logg ut");
        loggUtButton.setStyle(buttonStyle);
        navigationMenu.isFillWidth();
        navigationMenu.getChildren().addAll(overviewButton, accountButton, incomeButton
        , expensesButton, savingsButton, settingsButton, helpButton, loggUtButton);
        root.setLeft(navigationMenu);

        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Budsjettverktoy");
        stage.setScene(scene);
        stage.show();
    }
}
