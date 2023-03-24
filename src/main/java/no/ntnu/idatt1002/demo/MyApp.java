package no.ntnu.idatt1002.demo;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.ntnu.idatt1002.demo.data.Budget;
import no.ntnu.idatt1002.demo.data.Expense;
import no.ntnu.idatt1002.demo.data.Income;

import java.util.ArrayList;
import java.util.Arrays;

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

/*        //Temporary test data
        Type testType = new Type(3, 2023);
        testType.addExpense("Mat", 1000);
        testType.addExpense("Transport", 600);
        testType.addExpense("Bolig", 4500);
        testType.addIncome("Studielån", 8100);
        testType.addIncome("Deltidsjobb", 3000);*/

        //Temprary test data V2
        Budget userOneBudget = new Budget("OlaNordmann");
        userOneBudget.addExpense("Mat", 3500);
        userOneBudget.addExpense("Transport", 600);
        userOneBudget.addExpense("Bolig", 5000);
        userOneBudget.addExpense("Fritid", 500);
        userOneBudget.addExpense("Klær", 1000);
        userOneBudget.addExpense("Helse", 1000);
        userOneBudget.addIncome("Studielån", 8100);
        userOneBudget.addIncome("Deltidsjobb", 3000);




        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: rgba(100,148,76,0.38)");

        String underTitleStyle = (
                "-fx-font-size: 18;" +
                "-fx-font-weight: bold;");

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
//                "-fx-border-color: #116c75;" +
//                "-fx-border-width: 1px;" +
//                "-fx-border-radius: 5px;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 20px;" +
                "-fx-alignment: center;" +
                "-fx-max-height: 350px;");

        ArrayList<Expense> expenses = userOneBudget.getExpenseList();
        int expenseCount = expenses.size();
        //overviewWindow- PieChart overview of expenses
        ObservableList<PieChart.Data> pieChartExpenses = FXCollections.observableArrayList();

        for (Expense expense : expenses){
            pieChartExpenses.add(new PieChart.Data(expense.getExpenseName(), expense.getExpenseValue()));
        }


        final PieChart chart = new PieChart(pieChartExpenses);
        chart.setTitle("Dine utgifter");
        chart.setLegendVisible(false);
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

        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.observableArrayList(
                Arrays.asList("Inntekter", "Utgifter")));

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();
        //yAxis.setLabel("Sum");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Inntekter og utgifter");


        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        //series1.setName("Inntekter og utgifter");
        series1.getData().add(new XYChart.Data<>("Inntekter", userOneBudget.getTotalIncome()));
        series1.getData().add(new XYChart.Data<>("Utgifter", userOneBudget.getTotalExpense()));

        barChart.getData().add(series1);
        barChart.setTitle("Sum inntekt og utgifter");
        barChart.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 10px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");
        barChart.setLegendVisible(false);
        barChart.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        //Pane for tableViews
        BorderPane tablePane = new BorderPane();
        HBox tableHBox = new HBox();
        tablePane.setTop(tableHBox);


        //overviewWindow- TableView for viewing expenses
        TableView<Expense> expensesTableView = new TableView<>();
        ObservableList<Expense> expensesData = FXCollections.observableArrayList(userOneBudget.getExpenseList());

        TableColumn<Expense, String> nameColumn = new TableColumn<>("Navn");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("expenseName"));
        TableColumn<Expense, Double> sumColumn = new TableColumn<>("Sum (utgift)");
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("expenseValue"));
        expensesTableView.setItems(expensesData);
        sumColumn.setMinWidth(250);

        expensesTableView.getColumns().addAll(nameColumn, sumColumn);
        expensesTableView.setMaxHeight(250);
        expensesTableView.setMinWidth(350);



        //overviewWindow- TableView for viewing incomes
        TableView<Income> incomeTableView = new TableView<>();
        ObservableList<Income> incomeData = FXCollections.observableArrayList(userOneBudget.getIncomeList());

        TableColumn<Income, String> nameIncomeColumn = new TableColumn<>("Navn");
        nameIncomeColumn.setMinWidth(100);
        nameIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("incomeName"));

        TableColumn<Income, Double> sumIncomeColumn = new TableColumn<>("Sum (inntekt)");
        sumIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("incomeValue"));

        incomeTableView.setItems(incomeData);
        sumIncomeColumn.setMinWidth(250);

        incomeTableView.getColumns().addAll(nameIncomeColumn, sumIncomeColumn);
        incomeTableView.setMaxHeight(250);
        incomeTableView.setMinWidth(350);

        //overviewWindow- Text for top of overview page
        HBox titleOverviewPane = new HBox();
        Text overviewTitle = new Text("\nVelkommen til oversikten, her kan du få et kjapt overblikk over registrert informasjon!\n");
        overviewTitle.setStyle(underTitleStyle);
        titleOverviewPane.setAlignment(Pos.CENTER);
        titleOverviewPane.getChildren().add(overviewTitle);

        //overviewWindow- Text for bottom of overview page
        HBox underOverviewPane = new HBox();
        Text underOverviewText = new Text("\nDersom du ønsker å legge til en utgift eller inntekt, bruk navigasjonsmenyen til venstre.\n");
        underOverviewText.setStyle(
                "-fx-font-size: 15;" +
                "-fx-font-weight: bold;");
        underOverviewPane.getChildren().add(underOverviewText);
        underOverviewPane.setAlignment(Pos.CENTER);

        //overviewWindow- Pane for tableview
        HBox topOverviewPane = new HBox();
        topOverviewPane.setSpacing(50);
        topOverviewPane.autosize();
        topOverviewPane.setStyle("-fx-border-color: #ffffff;" +
                "-fx-border-width: 1px;" +
                "-fx-border-radius: 5px;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");
        Separator graphSeparator = new Separator();
        graphSeparator.setOrientation(Orientation.HORIZONTAL);
        tableHBox.getChildren().addAll(expensesTableView, incomeTableView);
        tableHBox.setSpacing(45);
        tablePane.setTop(tableHBox);
        tablePane.setBottom(graphSeparator);
        graphSeparator.setPadding(new Insets(30, 0, 0, 0));
        tablePane.setPadding(new Insets(10, 10, 10, 10));

        topOverviewPane.getChildren().addAll(tablePane);


        VBox bottomOverviewPane = new VBox();
        bottomOverviewPane.getChildren().addAll(graphsBox, underOverviewPane);

        //overviewWindow- Separator to differentiate between the different charts
        Separator chartSeparator = new Separator();
        chartSeparator.setOrientation(javafx.geometry.Orientation.VERTICAL);

        graphsBox.getChildren().addAll(chart, chartSeparator, barChart);
        overviewWindow.setTop(titleOverviewPane);
        overviewWindow.setCenter(topOverviewPane);
        overviewWindow.setBottom(bottomOverviewPane);
        windowPane.getChildren().addAll(overviewWindow);


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
        incomeName.setPrefWidth(250);
        incomeName.setPromptText("Navn på inntekt (eks.: Lønn, Studielån)");
        TextField incomeSum = new TextField();
        incomeSum.setPrefWidth(300);
        incomeSum.setPromptText("Sum på inntekt (eks.: 10000)");
        Button addIncomeButton = new Button("Legg til inntekt");
        addIncomeButton.setStyle(
                "-fx-background-color: #ffffff; " +
                "-fx-border-color: #116c75;" +
                "-fx-text-fill: #116c75;" +
                "-fx-pref-width: 150;" +
                "-fx-highlight-fill: #116c75;" +
                "-fx-alignment: center;");

        fieldBox.getChildren().addAll(incomeName, incomeSum, addIncomeButton);
        fieldBox.setAlignment(Pos.CENTER);

        incomeWindowElements.setCenter(fieldBox);


        HBox incomeTitleBox = new HBox();
        Text incomeTitle = new Text("\nVelkommen til inntektsiden, her kan du legge til inntekter!\n");
        incomeTitle.setStyle(underTitleStyle);
        incomeTitleBox.getChildren().add(incomeTitle);
        incomeTitleBox.setAlignment(Pos.CENTER);
        incomeWindow.setTop(incomeTitleBox);
        incomeWindow.setCenter(incomeWindowElements);
        incomeWindow.setVisible(false);
        windowPane.getChildren().addAll(incomeWindow);

        //settingsWindow -Pane for settings window
        BorderPane settingsWindow = new BorderPane();
        settingsWindow.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");

        //settingsWindow -Elements for settings window
        VBox topElementsSettings = new VBox();
        topElementsSettings.setAlignment(Pos.CENTER);

        Text settingsTopText = new Text("\nVelkommen til innstillingsiden, her kan du endre på innstillinger!\n");
        settingsTopText.setStyle(
                "-fx-font-size: 20;" +
                "-fx-font-weight: bold;");

        topElementsSettings.getChildren().addAll(settingsTopText);

        HBox settingsMiddleBox = new HBox();
        settingsMiddleBox.setSpacing(50);
        settingsMiddleBox.setAlignment(Pos.CENTER);
        Button settingsOptionsTextOne = new Button("Ønsker du å endre navn på en bruker?");
        settingsOptionsTextOne.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-color: #116c75;" +
                "-fx-font-size: 15;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");

        Button settingsOptionsTextTwo = new Button("Ønsker du å slette en bruker?");
        settingsOptionsTextTwo.setStyle("-fx-background-color: #ffffff;" +
                "-fx-border-color: #116c75;" +
                "-fx-font-size: 15;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");
        settingsMiddleBox.getChildren().addAll(settingsOptionsTextOne, settingsOptionsTextTwo);

        //settingsWindow - General settings
        settingsWindow.setTop(topElementsSettings);
        settingsWindow.setCenter(settingsMiddleBox);
        settingsWindow.setVisible(false);
        windowPane.getChildren().addAll(settingsWindow);

        //Pane for help window
        BorderPane helpWindow = new BorderPane();
        helpWindow.setStyle("-fx-background-color: #ffffff;" +
                "-fx-padding: 15px;" +
                "-fx-spacing: 10px;" +
                "-fx-alignment: center;");
        VBox helpWindowTopBox = new VBox();
        helpWindowTopBox.setAlignment(Pos.CENTER);
        Text helpWindowTitle = new Text("Her kan du få hjelp til å bruke programmet.");
        helpWindowTitle.setStyle(underTitleStyle);
        helpWindowTopBox.getChildren().add(helpWindowTitle);
        Text helpText = new Text("""
                Ofte stilte spørsmål::
                \s
                - Spørsmål 1: Hvem er dette laget for?\s
                    - Svar 1: Programmet er laget for privat bruk, med mål om å holde styr på privatøkonomien!\s
                    \s
                - Spørsmål 2:\s""");
        helpWindow.setTop(helpWindowTopBox);
        helpWindow.setCenter(helpText);

        VBox helpFeedbackBox = new VBox();
        helpFeedbackBox.setAlignment(Pos.CENTER);
        helpFeedbackBox.setPadding(new Insets(10, 10, 10, 10));
        helpFeedbackBox.setSpacing(10);
        TextField feedbackField = new TextField();
        feedbackField.setPrefHeight(200);
        feedbackField.setMaxWidth(600);
        feedbackField.setPromptText("Har du forslag til utvidet funksjon av programmet, eller har du funnet en bug? \r\rSkriv inn til oss her!");
        Button feedbackButton = new Button("Send tilbakemelding");
        feedbackButton.setStyle("-fx-font-size: 16;" +
                "-fx-background-color: #ffffff; " +
                "-fx-border-color: #116c75;" +
                "-fx-background-radius: 5px;" +
                "-fx-text-fill: #116c75;" +
                "-fx-pref-width: 150;" +
                "-fx-pref-height: 50;" +
                "-fx-highlight-fill: #116c75;" +
                "-fx-alignment: center;");
        feedbackButton.setAlignment(Pos.CENTER);
        feedbackButton.setMinWidth(250);
        feedbackButton.autosize();
        helpFeedbackBox.getChildren().addAll(feedbackField, feedbackButton);


        helpWindow.setBottom(helpFeedbackBox);
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
        Button accountButton = new Button("Konto");
        Button incomeButton = new Button("Inntekter");
        Button expensesButton = new Button("Utgifter");
        Button savingsButton = new Button("Sparemål");
        Button settingsButton = new Button("Innstillinger");
        Button helpButton = new Button("Hjelp");

        overviewButton.setStyle(buttonStyle);
        overviewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            overviewButton.setStyle(buttonHoverStyle);
            titleText.setText("Oversikt");
            helpWindow.setVisible(false);
            overviewWindow.setVisible(true);
            incomeWindow.setVisible(false);
            settingsWindow.setVisible(false);

            overviewButton.setStyle(buttonHoverStyle);
            accountButton.setStyle(buttonStyle);
            incomeButton.setStyle(buttonStyle);
            expensesButton.setStyle(buttonStyle);
            savingsButton.setStyle(buttonStyle);
            settingsButton.setStyle(buttonStyle);
            helpButton.setStyle(buttonStyle);
        });
        overviewButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> overviewButton.setStyle(buttonHoverStyle));
        overviewButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (!overviewWindow.isVisible()) {
                overviewButton.setStyle(buttonStyle);
            }

        });

        //Initializing to hoverstyle as default
        overviewButton.setStyle(buttonHoverStyle);

        accountButton.setStyle(buttonStyle);
        accountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Konto"));
        accountButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> accountButton.setStyle(buttonHoverStyle));
        accountButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> accountButton.setStyle(buttonStyle));

        incomeButton.setStyle(buttonStyle);
        incomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Inntekter");
            incomeWindow.setVisible(true);
            overviewWindow.setVisible(false);
            helpWindow.setVisible(false);
            settingsWindow.setVisible(false);

            overviewButton.setStyle(buttonStyle);
            accountButton.setStyle(buttonStyle);
            incomeButton.setStyle(buttonHoverStyle);
            expensesButton.setStyle(buttonStyle);
            savingsButton.setStyle(buttonStyle);
            settingsButton.setStyle(buttonStyle);
            helpButton.setStyle(buttonStyle);

        });
        incomeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> incomeButton.setStyle(buttonHoverStyle));
        incomeButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (!incomeWindow.isVisible()) {
                incomeButton.setStyle(buttonStyle);
            }

        });

        expensesButton.setStyle(buttonStyle);
        expensesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Utgifter"));
        expensesButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> expensesButton.setStyle(buttonHoverStyle));
        expensesButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> expensesButton.setStyle(buttonStyle));

        savingsButton.setStyle(buttonStyle);
        savingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> titleText.setText("Sparemål"));
        savingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> savingsButton.setStyle(buttonHoverStyle));
        savingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> savingsButton.setStyle(buttonStyle));

        settingsButton.setStyle(buttonStyle);
        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Innstillinger");
            overviewWindow.setVisible(false);
            incomeWindow.setVisible(false);
            helpWindow.setVisible(false);
            settingsWindow.setVisible(true);

            overviewButton.setStyle(buttonStyle);
            accountButton.setStyle(buttonStyle);
            incomeButton.setStyle(buttonStyle);
            expensesButton.setStyle(buttonStyle);
            savingsButton.setStyle(buttonStyle);
            settingsButton.setStyle(buttonHoverStyle);
            helpButton.setStyle(buttonStyle);
        });
        settingsButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> settingsButton.setStyle(buttonHoverStyle));
        settingsButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (!settingsWindow.isVisible()) {
                settingsButton.setStyle(buttonStyle);
            }

        });

        helpButton.setStyle(buttonStyle);
        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Hjelp");
            helpWindow.setVisible(true);
            overviewWindow.setVisible(false);
            incomeWindow.setVisible(false);
            settingsWindow.setVisible(false);

            overviewButton.setStyle(buttonStyle);
            accountButton.setStyle(buttonStyle);
            incomeButton.setStyle(buttonStyle);
            expensesButton.setStyle(buttonStyle);
            savingsButton.setStyle(buttonStyle);
            settingsButton.setStyle(buttonStyle);
            helpButton.setStyle(buttonHoverStyle);

        });
        helpButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> helpButton.setStyle(buttonHoverStyle));
        helpButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (!helpWindow.isVisible()) {
                helpButton.setStyle(buttonStyle);
            }

        });

        Button loggUtButton = new Button("Logg ut");
        loggUtButton.setStyle(buttonStyle);
        loggUtButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.exit(0));
        loggUtButton.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> loggUtButton.setStyle(buttonHoverStyle));
        loggUtButton.addEventHandler(MouseEvent.MOUSE_EXITED, event -> loggUtButton.setStyle(buttonStyle));

        addIncomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            userOneBudget.addIncome(incomeName.getText(), Integer.parseInt(incomeSum.getText()));

            series1.getData().removeAll(series1.getData());
            series1.getData().add(new XYChart.Data<>("Inntekter", userOneBudget.getTotalIncome()));
            series1.getData().add(new XYChart.Data<>("Utgifter", userOneBudget.getTotalExpense()));

            barChart.getData().add(series1);

            incomeSum.setText("");
            incomeName.setText("");

        });

        navigationMenu.isFillWidth();
        navigationMenu.getChildren().addAll(overviewButton, accountButton, incomeButton
        , expensesButton, savingsButton, settingsButton, helpButton, loggUtButton);
        root.setLeft(navigationMenu);
        root.setCenter(windowPane);


        Scene scene = new Scene(root, 1250, 1000);
        stage.setTitle("Budsjettverktøy");
        stage.setScene(scene);
        stage.show();
    }
}
