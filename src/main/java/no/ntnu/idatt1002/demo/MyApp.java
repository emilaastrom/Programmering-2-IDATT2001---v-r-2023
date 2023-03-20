package no.ntnu.idatt1002.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
                "-fx-text-fill: #ffffff;" +
                "-fx-alignment: center;" +
                "-fx-font-weight: bold;");

        titleBox.setAlignment(javafx.geometry.Pos.CENTER);
        titleBox.setStyle("-fx-background-color: rgba(32,44,23,0.38);" + "-fx-padding: 10;");
        titleBox.getChildren().add(titleText);
        root.setTop(titleBox);

        //StackPane for the different windows (overview, expenses, income, settings)
        StackPane windowPane = new StackPane();

        //TilePane for content of overview page
        BorderPane overviewWindow = new BorderPane();
        overviewWindow.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-color: #116c75;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");

        //HBox to contain different graphs
        HBox graphsBox = new HBox();
        graphsBox.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-color: #116c75;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 20px;" +
                "-fx-alignment: center;" +
                "-fx-max-height: 350px;");

        //Piechart overview of expenses
        ObservableList<PieChart.Data> pieChartExpenses =
                FXCollections.observableArrayList(
                        new PieChart.Data("Mat", 4000),
                        new PieChart.Data("Bolig", 5000),
                        new PieChart.Data("Transport", 900),
                        new PieChart.Data("Hobby", 400),
                        new PieChart.Data("Klær", 300));
        final PieChart chart = new PieChart(pieChartExpenses);
        chart.setTitle("Dine utgifter");

        //BarChart for income/expenses chart
        BarChart<?, ?> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        XYChart.Series series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data("Inntekter", 23000));
        series.getData().add(new XYChart.Data("Utgifter", 14000));
        barChart.getData().addAll(series);
        barChart.setTitle("Sum inntekt og utgifter");
        barChart.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 10px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");


        //TableView for viewing expenses/income
        TableView<Object> expensesIncomeTableView = new TableView<>();
        TableColumn<Object, Object> incomeColumn = new TableColumn<>("Inntekter");
        TableColumn<Object, Object> incomeValueColumn = new TableColumn<>("Sum");
        TableCell<Object, Object> incomeCellOne = new TableCell<>();
        incomeCellOne.setText("Jobb");
        expensesIncomeTableView.setMaxHeight(100);


        expensesIncomeTableView.getColumns().addAll(incomeColumn, incomeValueColumn);

        //Text for top of overview page
        Text overviewTitle = new Text("\nVelkommen til oversikten, her kan du få et kjapt overblikk over registrert informasjon!\n");
        overviewTitle.setStyle(
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;");

        //Text for bottom of overview page
        Text underOverviewText = new Text("\nDersom du ønsker å legge til en utgift eller inntekt, bruk navigasjonsmenyen til venstre.\n");
        underOverviewText.setStyle(
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;");

        //Pane for the bottom of the overview page
        VBox bottomOverviewPane = new VBox();
        bottomOverviewPane.getChildren().addAll(expensesIncomeTableView, underOverviewText);

        //Separator to differentiate between the different charts
        Separator chartSeparator = new Separator();
        chartSeparator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        graphsBox.getChildren().addAll(chart, chartSeparator, barChart);
        overviewWindow.setTop(overviewTitle);
        overviewWindow.setCenter(graphsBox);
        overviewWindow.setBottom(bottomOverviewPane);
        windowPane.getChildren().add(overviewWindow);

        //VBox for menu
        VBox navigationMenu = new VBox();
        navigationMenu.setStyle("-fx-background-color: rgba(100,148,76,0.38);" + "-fx-padding: 10;");
        navigationMenu.setSpacing(5);
        String buttonStyle = "-fx-font-size: 16;" +
                "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #116c75;" +
                "-fx-pref-width: 150;" +
                "-fx-pref-height: 50;" +
                "-fx-highlight-fill: #116c75;" +
                "-fx-opacity: 0.5;" +
                "-fx-highlight-text-fill: #ffffff;";

        String buttonHoverStyle = "-fx-font-size: 16;" +
                "-fx-background-color: #ffffff; " +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #116c75;" +
                "-fx-pref-width: 150;" +
                "-fx-pref-height: 50;" +
                "-fx-highlight-fill: #116c75;" +
                "-fx-highlight-text-fill: #ffffff;" +
                "-fx-border-color: #116c75;";

        //Pane for help window
        BorderPane helpPane = new BorderPane();
        Text helpText = new Text("Du trenger ikke nå hjelp for å få det her til å funk, det går bra");
        helpPane.getChildren().addAll(helpText);
        helpPane.setVisible(false);
        windowPane.getChildren().addAll(helpPane);


        //Buttons for navigating the application
        Button overviewButton = new Button("Oversikt");
        overviewButton.setStyle(buttonStyle);
        overviewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Oversikt");
            helpPane.setVisible(false);
            overviewWindow.setVisible(true);
        });
        overviewButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            overviewButton.setStyle(buttonHoverStyle);
        });
        overviewButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            overviewButton.setStyle(buttonStyle);
        });

        Button accountButton = new Button("Konto");
        accountButton.setStyle(buttonStyle);
        accountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Konto");
        });
        accountButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            accountButton.setStyle(buttonHoverStyle);
        });
        accountButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            accountButton.setStyle(buttonStyle);
        });

        Button incomeButton = new Button("Inntekter");
        incomeButton.setStyle(buttonStyle);
        incomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Inntekter");

        });
        incomeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            incomeButton.setStyle(buttonHoverStyle);
        });
        incomeButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            incomeButton.setStyle(buttonStyle);
        });

        Button expensesButton = new Button("Utgifter");
        expensesButton.setStyle(buttonStyle);
        expensesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Utgifter");
        });
        expensesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            expensesButton.setStyle(buttonHoverStyle);
        });
        expensesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            expensesButton.setStyle(buttonStyle);
        });

        Button savingsButton = new Button("Sparemål");
        savingsButton.setStyle(buttonStyle);
        savingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Sparemål");
        });
        savingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            savingsButton.setStyle(buttonHoverStyle);
        });
        savingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            savingsButton.setStyle(buttonStyle);
        });

        Button settingsButton = new Button("Innstillinger");
        settingsButton.setStyle(buttonStyle);
        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Innstillinger");
        });
        settingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            settingsButton.setStyle(buttonHoverStyle);
        });
        settingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            settingsButton.setStyle(buttonStyle);
        });

        Button helpButton = new Button("Hjelp");
        helpButton.setStyle(buttonStyle);
        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Hjelp");
            helpPane.setVisible(true);
            overviewWindow.setVisible(false);
        });
        helpButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            helpButton.setStyle(buttonHoverStyle);
        });
        helpButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            helpButton.setStyle(buttonStyle);
        });

        Button loggUtButton = new Button("Logg ut");
        loggUtButton.setStyle(buttonStyle);
        loggUtButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.exit(0);
        });
        loggUtButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            loggUtButton.setStyle(buttonHoverStyle);
        });
        loggUtButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            loggUtButton.setStyle(buttonStyle);
        });

        navigationMenu.isFillWidth();
        navigationMenu.getChildren().addAll(overviewButton, accountButton, incomeButton
        , expensesButton, savingsButton, settingsButton, helpButton, loggUtButton);
        root.setLeft(navigationMenu);
        root.setCenter(windowPane);


        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("Budsjettverktøy");
        stage.setScene(scene);
        stage.show();
    }
}
