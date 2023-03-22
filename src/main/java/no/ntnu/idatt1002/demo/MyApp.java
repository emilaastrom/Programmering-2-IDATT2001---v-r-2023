package no.ntnu.idatt1002.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.ntnu.idatt1002.demo.data.Type;

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

        //Temporary test data
        Type testType = new Type(3, 2023);
        testType.addExpense("Mat", 1000);
        testType.addExpense("Transport", 600);
        testType.addExpense("Bolig", 4500);
        testType.addIncome("Studielån", 8100);
        testType.addIncome("Deltidsjobb", 3000);

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
        titleText.setFill(Color.WHITE);
        titleText.setUnderline(true);

        titleBox.setAlignment(javafx.geometry.Pos.CENTER);
        titleBox.setStyle("-fx-background-color: rgba(79,110,57,0.7);" + "-fx-padding: 10;");
        titleBox.getChildren().add(titleText);
        root.setTop(titleBox);

        //StackPane for the different windows (overview, expenses, income, settings)
        StackPane windowPane = new StackPane();


        //overviewWindow- TilePane for content of overview page
        BorderPane overviewWindow = new BorderPane();
        overviewWindow.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");

        //overviewWindow- HBox to contain different graphs
        HBox graphsBox = new HBox();
        graphsBox.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-color: #116c75;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 20px;" +
                "-fx-alignment: center;" +
                "-fx-max-height: 350px;");

        //overviewWindow- PieChart overview of expenses
        ObservableList<PieChart.Data> pieChartExpenses =
                FXCollections.observableArrayList(
                        new PieChart.Data("Mat", 4000),
                        new PieChart.Data("Bolig", 5000),
                        new PieChart.Data("Transport", 900),
                        new PieChart.Data("Hobby", 400),
                        new PieChart.Data("Klær", 300));
        final PieChart chart = new PieChart(pieChartExpenses);
        chart.setTitle("Dine utgifter");
        /*chart.getData().addListener((javafx.collections.ListChangeListener.Change<? extends PieChart.Data> c) -> {
            while (c.next()) {
                for (PieChart.Data data : c.getAddedSubList()) {
                    data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent e) -> {
                        System.out.println("Clicked on " + data.getName());
                    });
                }
            }
        });
         */

        //overviewWindow- BarChart for income/expenses chart
        BarChart<?, ?> barChart = new BarChart<>(new CategoryAxis(), new NumberAxis());
        XYChart.Series series = new XYChart.Series<String, Double>();
        series.getData().add(new XYChart.Data<>("Inntekter", testType.getTotalIncome()));
        series.getData().add(new XYChart.Data<>("Utgifter", testType.getTotalExpense()));
        barChart.getData().addAll(series);
        barChart.setTitle("Sum inntekt og utgifter");
        barChart.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 10px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");

        //overviewWindow- TableView for viewing expenses/income
        /*TableView<String> expensesIncomeTableView = new TableView<>();

        TableColumn<String, String> typeColumn = new TableColumn<>("Type");
//        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn<String, String> nameColumn = new TableColumn<>("Navn");
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<String, String> sumColumn = new TableColumn<>("Sum");
//        sumColumn.setCellValueFactory(new PropertyValueFactory<>("sum"));

        expensesIncomeTableView.getColumns().add(typeColumn);
        expensesIncomeTableView.getColumns().add(nameColumn);
        expensesIncomeTableView.getColumns().add(sumColumn);

        TableCell<String, String> typeCellOne = new TableCell<>();
        typeCellOne.setText("Testcell");
        expensesIncomeTableView.setMaxHeight(250);

        //overviewWindow- Data for the TableView (expenses/income)
        ObservableList<String> expensesIncomeData = FXCollections.observableArrayList(
                new String("Mat 4500"),
                new String("Transport 900"),
                new String("Hobby 400"),
                new String("Klær 300")
        );
        expensesIncomeTableView.setItems(expensesIncomeData);*/

        //overviewWindow- Text for top of overview page
        Text overviewTitle = new Text("\nVelkommen til oversikten, her kan du få et kjapt overblikk over registrert informasjon!\n");
        overviewTitle.setStyle(
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;");

        //overviewWindow- Text for bottom of overview page
        Text underOverviewText = new Text("\nDersom du ønsker å legge til en utgift eller inntekt, bruk navigasjonsmenyen til venstre.\n");
        underOverviewText.setStyle(
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;");

        //overviewWindow- Pane for the bottom of the overview page
        VBox bottomOverviewPane = new VBox();
        bottomOverviewPane.getChildren().addAll(underOverviewText);

        //overviewWindow- Separator to differentiate between the different charts
        Separator chartSeparator = new Separator();
        chartSeparator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        graphsBox.getChildren().addAll(chart, chartSeparator, barChart);
        overviewWindow.setTop(overviewTitle);
        overviewWindow.setCenter(bottomOverviewPane);
        overviewWindow.setBottom(graphsBox);
        windowPane.getChildren().add(overviewWindow);

        //Pane for income window
        BorderPane incomeWindow = new BorderPane();
        incomeWindow.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");


        //Elements for income window
        BorderPane incomeWindowElements = new BorderPane();

        HBox fieldBox = new HBox();
        fieldBox.setSpacing(10);
        TextField incomeName = new TextField();
        incomeName.prefWidth(200);
        incomeName.minWidth(300);
        incomeName.setPromptText("Navn på inntekt (eks.: Lønn, Studielån)");
        TextField incomeSum = new TextField();
        incomeSum.prefWidth(200);
        incomeSum.minWidth(200);
        incomeSum.setPromptText("Sum på inntekt (eks.: 10000)");
        Button addIncomeButton = new Button("Legg til inntekt");

        fieldBox.getChildren().addAll(incomeName, incomeSum, addIncomeButton);
        fieldBox.setAlignment(Pos.CENTER);

        incomeWindowElements.setCenter(fieldBox);


        HBox incomeTitleBox = new HBox();
        Text incomeTitle = new Text("\nVelkommen til inntektsiden, her kan du legge til inntekter!\n");
        incomeTitle.setStyle("-fx-alignment: center;" +
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;");
        incomeTitleBox.getChildren().add(incomeTitle);
        incomeTitleBox.setAlignment(Pos.CENTER);
        incomeWindow.setTop(incomeTitleBox);
        incomeWindow.setCenter(incomeWindowElements);
        incomeWindow.setVisible(false);
        windowPane.getChildren().addAll(incomeWindow);

        //Pane for help window
        BorderPane helpWindow = new BorderPane();
        helpWindow.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");
        Text helpText = new Text("""
                Her kan du få hjelp til å bruke programmet.

                Brukerinstruks:
                - Steg 1:\s
                - Steg 2:\s""");
        helpWindow.setCenter(helpText);
        helpWindow.setVisible(false);
        windowPane.getChildren().addAll(helpWindow);


        //VBox for navigation menu on the left side of the window
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

        //Buttons for navigating the application
        Button overviewButton = new Button("Oversikt");
        overviewButton.setStyle(buttonStyle);
        overviewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Oversikt");
            helpWindow.setVisible(false);
            overviewWindow.setVisible(true);
            incomeWindow.setVisible(false);
        });
        overviewButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> overviewButton.setStyle(buttonHoverStyle));
        overviewButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> overviewButton.setStyle(buttonStyle));

        Button accountButton = new Button("Konto");
        accountButton.setStyle(buttonStyle);
        accountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Konto"));
        accountButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> accountButton.setStyle(buttonHoverStyle));
        accountButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> accountButton.setStyle(buttonStyle));

        Button incomeButton = new Button("Inntekter");
        incomeButton.setStyle(buttonStyle);
        incomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Inntekter");
            incomeWindow.setVisible(true);
            overviewWindow.setVisible(false);
            helpWindow.setVisible(false);
        });
        incomeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> incomeButton.setStyle(buttonHoverStyle));
        incomeButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> incomeButton.setStyle(buttonStyle));

        Button expensesButton = new Button("Utgifter");
        expensesButton.setStyle(buttonStyle);
        expensesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Utgifter"));
        expensesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> expensesButton.setStyle(buttonHoverStyle));
        expensesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> expensesButton.setStyle(buttonStyle));

        Button savingsButton = new Button("Sparemål");
        savingsButton.setStyle(buttonStyle);
        savingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Sparemål"));
        savingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> savingsButton.setStyle(buttonHoverStyle));
        savingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> savingsButton.setStyle(buttonStyle));

        Button settingsButton = new Button("Innstillinger");
        settingsButton.setStyle(buttonStyle);
        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Innstillinger"));
        settingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> settingsButton.setStyle(buttonHoverStyle));
        settingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> settingsButton.setStyle(buttonStyle));

        Button helpButton = new Button("Hjelp");
        helpButton.setStyle(buttonStyle);
        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Hjelp");
            helpWindow.setVisible(true);
            overviewWindow.setVisible(false);
            incomeWindow.setVisible(false);
        });
        helpButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> helpButton.setStyle(buttonHoverStyle));
        helpButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> helpButton.setStyle(buttonStyle));

        Button loggUtButton = new Button("Logg ut");
        loggUtButton.setStyle(buttonStyle);
        loggUtButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
        loggUtButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> loggUtButton.setStyle(buttonHoverStyle));
        loggUtButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> loggUtButton.setStyle(buttonStyle));

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
