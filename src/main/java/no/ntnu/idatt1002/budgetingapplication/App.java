package no.ntnu.idatt1002.budgetingapplication;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import no.ntnu.idatt1002.budgetingapplication.data.Budget;
import no.ntnu.idatt1002.budgetingapplication.data.BudgetItem;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Use this class to start the application
 */
public class App extends Application {

    /**
     * Main method for my application
     */
    public static void main(String[] args) throws Exception {
        launch(args);
   }

    @Override
    public void start(Stage stage) throws Exception {

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 1000  , 750);
        stage.setTitle("Budsjettverkt;y");
        stage.setScene(scene);
        stage.show();
        root.getStyleClass().addAll("root", "borderpane");



        BorderPane loginRoot = new BorderPane();
        BorderPane loginWindow = new BorderPane();
        Scene loginScene = new Scene(loginRoot, 500, 500);


        Text selectAccountText = new Text("Skriv ditt navn for å velge/opprette en bruker");
        selectAccountText.getStyleClass().add("vanliginformasjonstekst");

        VBox accountSelectionBox = new VBox();
        accountSelectionBox.getStyleClass().add("loginPageBox");
        accountSelectionBox.setId("loginPageBox");
        TextField accountSelection = new TextField();
        accountSelection.setMaxWidth(250);
        Button velgBruker = new Button("Logg inn på valgt bruker");
        velgBruker.setId("velgbrukerknapp");
        accountSelectionBox.getChildren().addAll(selectAccountText, accountSelection, velgBruker);
        accountSelectionBox.setAlignment(Pos.CENTER);
        accountSelectionBox.setPadding(new Insets(10, 10, 10, 10));
        accountSelectionBox.setSpacing(10);

        loginWindow.setCenter(accountSelectionBox);

        HBox themeButtons = new HBox();
        themeButtons.setAlignment(Pos.CENTER);
        themeButtons.setSpacing(10);
        themeButtons.setPadding(new Insets(10, 10, 10, 10));
        Button defaultThemeButton = new Button("Lys");
        Button darkThemeButton = new Button("Mørk");
        Button highContrastButton = new Button("Høy-kontrast");
        themeButtons.getChildren().addAll(defaultThemeButton, darkThemeButton, highContrastButton);


        loginRoot.setCenter(loginWindow);
        loginRoot.setBottom(themeButtons);

        //READ USERS DATA FROM FILE
        AtomicReference<String> user = new AtomicReference<>("TEST USER");

        selectAccountText.addEventHandler(KeyEvent.ANY, event -> user.set(accountSelection.getText()));

        AtomicReference<String> currentStylesheet = new AtomicReference<>("file:src/main/resources/style.css");
        loginScene.getStylesheets().removeAll();
        loginScene.getStylesheets().add(String.valueOf(currentStylesheet));
        selectUser(stage, scene);

        defaultThemeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            currentStylesheet.set("file:src/main/resources/style.css");
            loginScene.getStylesheets().clear();
            loginScene.getStylesheets().add(String.valueOf(currentStylesheet));
        });
        darkThemeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            currentStylesheet.set("file:src/main/resources/darkmode.css");
            loginScene.getStylesheets().clear();
            loginScene.getStylesheets().add(String.valueOf(currentStylesheet));
        });
        highContrastButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            currentStylesheet.set("file:src/main/resources/highcontrast.css");
            loginScene.getStylesheets().clear();
            loginScene.getStylesheets().add(String.valueOf(currentStylesheet));
        });

        accountSelection.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                user.set(accountSelection.getText());
                Scene2 userScene;
                try {
                    userScene = new Scene2(user.get());
                    userScene.getStylesheets().removeAll();
                    userScene.getStylesheets().add(String.valueOf(currentStylesheet));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(userScene);
            }
        });

        velgBruker.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            user.set(accountSelection.getText());
            Scene2 userScene;
            try {
                userScene = new Scene2(user.get());
                userScene.getStylesheets().removeAll();
                userScene.getStylesheets().add(String.valueOf(currentStylesheet));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(userScene);
        });

/*        scene.getStylesheets().add("file:src/main/resources/style.css");
        loginScene.getStylesheets().add("file:src/main/resources/style.css");*/

        stage.setTitle("Budsjettverktøy");
        stage.setScene(loginScene);
        stage.show();
    }

    private void selectUser(Stage stage, Scene mainScene) {
        stage.setScene(mainScene);
        stage.show();
    }


}

class Scene2 extends Scene {

    public Scene2(String username) throws FileNotFoundException {
        super(new BorderPane(), 1000, 750);
        BorderPane root = (BorderPane) this.getRoot();

        Budget userBudget = new Budget(username);

        String user = userBudget.getUsername();
        Scanner scanner = new Scanner(new File(user + "Budget.txt"));
        ArrayList<String> fileData = new ArrayList<>();
        while (scanner.hasNextLine()) {
            fileData.add(scanner.nextLine());
        }
        scanner.close();
        for (String line : fileData) {
            if (line.startsWith("Income: ")){
                String income = line.substring(8);
                String value = fileData.get(fileData.indexOf(line) + 1).substring(7);
                userBudget.addIncomeNotToFile(income, Double.parseDouble(value));
            }else if (line.startsWith("Expense: ")){
                String expense = line.substring(9);
                String value = fileData.get(fileData.indexOf(line) + 1).substring(7);
                userBudget.addExpenseNotToFile(expense, Double.parseDouble(value));
            }
        }

        //HBox for current page title
        HBox titleBox = new HBox();
        Text titleText = new Text();
        titleText.setText("Oversikt");
        titleText.setFill(Color.WHITE);
        titleText.setUnderline(true);

        titleBox.setAlignment(javafx.geometry.Pos.CENTER);
        titleBox.getChildren().add(titleText);

        BorderPane topBoxPane = new BorderPane();
        topBoxPane.setId("radtoppen");
        topBoxPane.setPrefHeight(30);
        topBoxPane.setPadding(new Insets(10, 10, 10, 10));

        Text topBoxText = new Text();
        topBoxText.setText("Velkommen, " + userBudget.getUsername().substring(0, 1).toUpperCase() + userBudget.getUsername().substring(1));
        topBoxText.setFill(Color.BLACK);
        topBoxText.getStyleClass().add("brukernavn");
        topBoxText.setId("brukernavn");
        topBoxPane.setRight(topBoxText);

        root.setTop(topBoxPane);

        //StackPane for the different windows (overview, expenses, income, settings)
        StackPane windowPane = new StackPane();


        //overviewWindow- TilePane for content of overview page
        BorderPane overviewWindow = new BorderPane();
        overviewWindow.getStyleClass().add("borderpane");

        //overviewWindow- HBox to contain different graphs
        HBox graphsBox = new HBox();
        graphsBox.getStyleClass().add("graphsbox");

        ArrayList<BudgetItem> expenses = userBudget.getExpenseList();
        //overviewWindow- PieChart overview of expenses
        AtomicReference<ObservableList<PieChart.Data>> pieChartExpenses = new AtomicReference<>(FXCollections.observableArrayList());

        for (BudgetItem expense : expenses){
            pieChartExpenses.get().add(new PieChart.Data(expense.getBudgetItemName(), expense.getBudgetItemValue()));
        }


        final PieChart chart = new PieChart(pieChartExpenses.get());
        chart.getStyleClass().add("piechart");
        chart.setTitle("Dine utgifter");
        chart.setLegendVisible(false);
        chart.setMaxHeight(250);

        //Defining the x axis
        CategoryAxis xAxis = new CategoryAxis();

        xAxis.setCategories(FXCollections.observableArrayList(
                Arrays.asList("Inntekter", "Utgifter")));

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis();


        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.getStyleClass().add("barchart");
        barChart.setTitle("Inntekter og utgifter");
        barChart.setMaxHeight(250);


        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        //series1.setName("Inntekter og utgifter");
        series1.getData().add(new XYChart.Data<>("Inntekter", userBudget.getTotalIncome()));
        series1.getData().add(new XYChart.Data<>("Utgifter", userBudget.getTotalExpense()));




        barChart.getData().add(series1);
        barChart.setTitle("Sum inntekt og utgifter");
        barChart.setLegendVisible(false);
        barChart.getStyleClass().add("bar-chart");
//        barChart.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        AtomicReference<Node> n = new AtomicReference<>(barChart.lookup(".data0.chart-bar"));
        n.get().setStyle("-fx-bar-fill: #398564");
        n.set(barChart.lookup(".data1.chart-bar"));
        n.get().setStyle("-fx-bar-fill: #e36700");

        //Pane for tableViews
        BorderPane tablePane = new BorderPane();
        HBox tableHBox = new HBox();
        tablePane.setTop(tableHBox);


        //overviewWindow- TableView for viewing expenses
        TableView<BudgetItem> expensesTableView = new TableView<>();
        expensesTableView.getStyleClass().add("tableview");
        AtomicReference<ObservableList<BudgetItem>> expensesData = new AtomicReference<>(FXCollections.observableArrayList(userBudget.getExpenseList()));

        TableColumn<BudgetItem, String> nameColumn = new TableColumn<>("Navn");
        nameColumn.getStyleClass().add("tablecolumn");
        nameColumn.setMinWidth(100);
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemName"));
        TableColumn<BudgetItem, Double> sumColumn = new TableColumn<>("Sum (utgift)");
        sumColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemValue"));
        expensesTableView.setItems(expensesData.get());
        sumColumn.setMinWidth(281);

        expensesTableView.getColumns().addAll(nameColumn, sumColumn);
        expensesTableView.setMaxHeight(200);
        expensesTableView.setPrefWidth(400);



        //overviewWindow- TableView for viewing incomes
        TableView<BudgetItem> incomeTableView = new TableView<>();
        AtomicReference<ObservableList<BudgetItem>> incomeData = new AtomicReference<>(FXCollections.observableArrayList(userBudget.getIncomeList()));

        TableColumn<BudgetItem, String> nameIncomeColumn = new TableColumn<>("Navn");
        nameIncomeColumn.setMinWidth(100);
        nameIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemName"));

        TableColumn<BudgetItem, Double> sumIncomeColumn = new TableColumn<>("Sum (inntekt)");
        sumIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemValue"));

        incomeTableView.setItems(incomeData.get());
        sumIncomeColumn.setMinWidth(250);

        incomeTableView.getColumns().addAll(nameIncomeColumn, sumIncomeColumn);
        incomeTableView.setMaxHeight(200);
        incomeTableView.setMinWidth(350);


        //overviewWindow- Text for top of overview page
        HBox titleOverviewPane = new HBox();
        titleOverviewPane.setId("titleOverviewPane");
        Text overviewTitle = new Text("\n Oversikten: her kan du få et kjapt overblikk over registrert informasjon!\n");
        overviewTitle.getStyleClass().add("vanliginformasjonstekst");
        titleOverviewPane.setAlignment(Pos.CENTER);
        titleOverviewPane.getChildren().add(overviewTitle);

        //overviewWindow- Text for bottom of overview page
        HBox underOverviewPane = new HBox();
        Text underOverviewText = new Text("\nDersom du ønsker å legge til en utgift eller inntekt, bruk navigasjonsmenyen til venstre.\n");
        underOverviewText.getStyleClass().add("vanliginformasjonstekst");
        underOverviewText.setId("overviewtekstnederst");
        underOverviewPane.getChildren().add(underOverviewText);
        underOverviewPane.setAlignment(Pos.CENTER);

        //overviewWindow- Pane for tableview
        HBox topOverviewPane = new HBox();
        topOverviewPane.getStyleClass().add("overviewtableviewbox");
        Separator graphSeparator = new Separator();
        graphSeparator.setOrientation(Orientation.HORIZONTAL);
        tableHBox.getChildren().addAll(incomeTableView, expensesTableView);
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
        incomeWindow.getStyleClass().add("borderpane");

        //Elements for income window

        //incomeWindow- TableView for viewing incomes
        TableView<BudgetItem> incomePageTableView = new TableView<>();
        AtomicReference<ObservableList<BudgetItem>> incomePageData = new AtomicReference<>(FXCollections.observableArrayList(userBudget.getIncomeList()));

        TableColumn<BudgetItem, String> nameIncomePageColumn = new TableColumn<>("Navn");
        nameIncomePageColumn.setMinWidth(100);
        nameIncomePageColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemName"));

        TableColumn<BudgetItem, Double> sumIncomePageColumn = new TableColumn<>("Sum (inntekt)");
        sumIncomePageColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemValue"));

        incomePageTableView.setItems(incomePageData.get());
        sumIncomePageColumn.setMinWidth(698);

        incomePageTableView.getColumns().addAll(nameIncomePageColumn, sumIncomePageColumn);
        incomePageTableView.setMaxHeight(200);
        incomePageTableView.setMinWidth(350);


        Button removeIncomeButton = new Button("Fjern markert inntekt");
        removeIncomeButton.setMinWidth(800);
        removeIncomeButton.setOnAction(e -> {
            BudgetItem selectedItem = incomePageTableView.getSelectionModel().getSelectedItem();
            incomePageTableView.getItems().remove(selectedItem);
            incomeTableView.getItems().remove(selectedItem);
            userBudget.removeIncome(selectedItem.getBudgetItemName());

            //UPDATING CHARTS
            updateBarChart(userBudget, barChart, series1);
            Node b = barChart.lookup(".data0.chart-bar");
            b.setStyle("-fx-bar-fill: #398564");
            b = barChart.lookup(".data1.chart-bar");
            b.setStyle("-fx-bar-fill: #e36700");

        });

        VBox tableViewBox = new VBox();
        tableViewBox.setPadding(new Insets(50, 0, 0, 0));
        tableViewBox.setSpacing(10);
        tableViewBox.getChildren().addAll(incomePageTableView, removeIncomeButton);

        BorderPane incomeWindowElements = new BorderPane();
        incomeWindowElements.getStyleClass().add("borderpane");

        HBox fieldBox = new HBox();
        fieldBox.setId("fieldbox");
        fieldBox.setPadding(new Insets(10, 10, 10, 10));
        fieldBox.setSpacing(10);
        TextField incomeName = new TextField();
        incomeName.setPrefWidth(250);
        incomeName.setPromptText("Navn på inntekt (eks.: Lønn/Studielån)");
        TextField incomeSum = new TextField();
        incomeSum.setPrefWidth(300);
        incomeSum.setPromptText("Sum på inntekt (eks.: 10000)");
        Button addIncomeButton = new Button("Legg til inntekt");

        fieldBox.getChildren().addAll(incomeName, incomeSum, addIncomeButton);
        fieldBox.setAlignment(Pos.CENTER);

        incomeWindowElements.setTop(tableViewBox);
        incomeWindowElements.setCenter(fieldBox);


        HBox incomeTitleBox = new HBox();
        incomeTitleBox.setId("incomeTitleBox");
        Text incomeTitle = new Text("\nInntekter: her kan du legge til inntekter!\n");
        incomeTitle.getStyleClass().add("vanliginformasjonstekst");
        incomeTitleBox.getChildren().add(incomeTitle);
        incomeTitleBox.setAlignment(Pos.CENTER);
        incomeWindow.setTop(incomeTitleBox);
        incomeWindow.setCenter(incomeWindowElements);
        incomeWindow.setVisible(false);
        windowPane.getChildren().addAll(incomeWindow);

        //Pane for expenses window
        BorderPane expensesWindow = new BorderPane();
        expensesWindow.getStyleClass().add("borderpane");

        //Elements for expenses window

        //expensesWindow- TableView for viewing incomes
        TableView<BudgetItem> expensesPageTableView = new TableView<>();
        AtomicReference<ObservableList<BudgetItem>> expensesPageData = new AtomicReference<>(FXCollections.observableArrayList(userBudget.getExpenseList()));

        TableColumn<BudgetItem, String> nameExpensesPageColumn = new TableColumn<>("Navn");
        nameExpensesPageColumn.setMinWidth(100);
        nameExpensesPageColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemName"));

        TableColumn<BudgetItem, Double> sumExpensesPageColumn = new TableColumn<>("Sum (utgift)");
        sumExpensesPageColumn.setCellValueFactory(new PropertyValueFactory<>("budgetItemValue"));

        expensesPageTableView.setItems(expensesData.get());
        sumExpensesPageColumn.setMinWidth(698);

        expensesPageTableView.getColumns().addAll(nameExpensesPageColumn, sumExpensesPageColumn);
        expensesPageTableView.setMaxHeight(200);
        expensesPageTableView.setMinWidth(350);

        Button removeExpenseButton = new Button ("Fjern markert utgift");
        removeExpenseButton.setMinWidth(800);
        removeExpenseButton.setOnAction(e -> {
            BudgetItem selectedItem = expensesPageTableView.getSelectionModel().getSelectedItem();
            expensesPageTableView.getItems().remove(selectedItem);
            expensesTableView.getItems().remove(selectedItem);
            userBudget.removeExpense(selectedItem.getBudgetItemName());
            //UPDATING CHARTS
            updateBarChart(userBudget, barChart, series1);
            pieChartExpenses.set(FXCollections.observableArrayList());
            for (BudgetItem expense : expenses){pieChartExpenses.get().add(new PieChart.Data(expense.getBudgetItemName(), expense.getBudgetItemValue()));}
            chart.setData(pieChartExpenses.get());
            Node b = barChart.lookup(".data0.chart-bar");
            b.setStyle("-fx-bar-fill: #398564");
            b = barChart.lookup(".data1.chart-bar");
            b.setStyle("-fx-bar-fill: #e36700");


        });

        VBox tableViewBox2 = new VBox();
        tableViewBox2.setPadding(new Insets(50, 0, 0, 0));
        tableViewBox2.setSpacing(10);
        tableViewBox2.getChildren().addAll(expensesPageTableView, removeExpenseButton);

        BorderPane expensesWindowElements = new BorderPane();
        expensesWindowElements.getStyleClass().add("borderpane");

        HBox fieldBox2 = new HBox();
        fieldBox2.setId("fieldbox");
        fieldBox2.setPadding(new Insets(10, 10, 10, 10));
        fieldBox2.setSpacing(10);
        TextField expensesName = new TextField();
        expensesName.setPrefWidth(250);
        expensesName.setPromptText("Navn på utgift (eks.: Hobby/Strøm)");
        TextField expensesSum = new TextField();
        expensesSum.setPrefWidth(300);
        expensesSum.setPromptText("Sum på utgift (eks.: 10000)");
        Button addExpensesButton = new Button("Legg til utgift");

        fieldBox2.getChildren().addAll(expensesName, expensesSum, addExpensesButton);
        fieldBox2.setAlignment(Pos.CENTER);

        expensesWindowElements.setTop(tableViewBox2);
        expensesWindowElements.setCenter(fieldBox2);


        HBox expensesTitleBox = new HBox();
        expensesTitleBox.setId("incomeTitleBox");
        Text expensesTitle = new Text("\nUtgifter: her kan du legge til utgifter!\n");
        expensesTitle.getStyleClass().add("vanliginformasjonstekst");
        expensesTitleBox.getChildren().add(expensesTitle);
        expensesTitleBox.setAlignment(Pos.CENTER);
        expensesWindow.setTop(expensesTitleBox);
        expensesWindow.setCenter(expensesWindowElements);
        expensesWindow.setVisible(false);
        windowPane.getChildren().addAll(expensesWindow);

        //settingsWindow -Pane for settings window
        BorderPane settingsWindow = new BorderPane();
        settingsWindow.getStyleClass().add("borderpane");

        //settingsWindow -Elements for settings window
        VBox topElementsSettings = new VBox();
        topElementsSettings.setAlignment(Pos.CENTER);

        Text settingsTopText = new Text("\nInnstillinger: her kan du endre på innstillinger!\n");
        settingsTopText.getStyleClass().add("vanliginformasjonstekst");

        topElementsSettings.getChildren().addAll(settingsTopText);

        HBox settingsMiddleBox = new HBox();
        settingsMiddleBox.setSpacing(50);
        settingsMiddleBox.setAlignment(Pos.CENTER);
        Button settingsOptionsTextOne = new Button("Ønsker du å endre navn på en bruker?");
        Button settingsOptionsTextTwo = new Button("Ønsker du å slette en bruker?");
        settingsMiddleBox.getChildren().addAll(settingsOptionsTextOne, settingsOptionsTextTwo);


        /*HBox settingsLowerBox = new HBox();
        settingsLowerBox.setSpacing(50);
        settingsLowerBox.setPadding(new Insets(10, 10, 50, 10));
        settingsLowerBox.setAlignment(Pos.CENTER);
        Button defaultThemeButton = new Button("Lys modus");
        Button darkThemeButton = new Button("Mørk modus");
        Button highContrastButton = new Button("Høy-kontrast modus");
        settingsLowerBox.getChildren().addAll(defaultThemeButton, darkThemeButton, highContrastButton);
        defaultThemeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            scene.getStylesheets().clear();
            scene.getStylesheets().add("file:src/main/resources/style.css");
        settingsWindow.setBottom(settingsLowerBox);
        });*/

        //settingsWindow - General settings
        settingsWindow.setTop(topElementsSettings);
        settingsWindow.setCenter(settingsMiddleBox);
        settingsWindow.setVisible(false);
        windowPane.getChildren().addAll(settingsWindow);

        //savingsWindow - Pane for savings window

        BorderPane savingsWindow = new BorderPane();
        savingsWindow.getStyleClass().add("borderpane");

        //savingsWindow - Elements for savings window
        VBox savingsWindowTopBox = new VBox();
        savingsWindowTopBox.setAlignment(Pos.CENTER);
        Text savingsWindowTitle = new Text("Her kan du definere sparemål.");
        savingsWindowTopBox.getChildren().add(savingsWindowTitle);

        VBox savingsWindowMiddleBox = new VBox();
        savingsWindowMiddleBox.setAlignment(Pos.CENTER);

        HBox exampleSavingsGoal = new HBox();
        ProgressBar savingsProgressBar = new ProgressBar();
        savingsProgressBar.setProgress(0.75);
        savingsProgressBar.setPrefWidth(500);
        exampleSavingsGoal.setAlignment(Pos.CENTER);
        exampleSavingsGoal.getChildren().addAll(new Text("Ny snøskuter: "),savingsProgressBar, new Text(" 75% - 22500/30000"));

        HBox exampleSavingsGoal2 = new HBox();
        ProgressBar savingsProgressBar2 = new ProgressBar();
        savingsProgressBar2.setProgress(0.25);
        savingsProgressBar2.setPrefWidth(500);
        exampleSavingsGoal2.setAlignment(Pos.CENTER);
        exampleSavingsGoal2.getChildren().addAll(new Text("Ny bil: "),savingsProgressBar2, new Text(" 25% - 7500/30000"));

        savingsWindowMiddleBox.getChildren().addAll(exampleSavingsGoal, exampleSavingsGoal2);

        HBox savingsWindowBottomBox = new HBox();
        savingsWindowBottomBox.setSpacing(10);
        savingsWindowBottomBox.setAlignment(Pos.CENTER);
        savingsWindowBottomBox.setPadding(new Insets(10, 10, 200, 10));
        TextField savingsName = new TextField();
        savingsName.setPrefWidth(250);
        savingsName.setPromptText("Navn på sparemål (eks.: Ny snøskuter)");
        TextField savingsSum = new TextField();
        savingsSum.setPrefWidth(300);
        savingsSum.setPromptText("Sum på sparemål (eks.: 30000)");
        Button addSavingsButton = new Button("Legg til sparemål");
        savingsWindowBottomBox.getChildren().addAll(savingsName, savingsSum, addSavingsButton);

        savingsWindow.setTop(savingsWindowTopBox);
        savingsWindow.setCenter(savingsWindowMiddleBox);
        savingsWindow.setBottom(savingsWindowBottomBox);
        savingsWindow.setVisible(false);
        windowPane.getChildren().addAll(savingsWindow);

        //Pane for help window
        BorderPane helpWindow = new BorderPane();
        helpWindow.getStyleClass().add("borderpane");
        VBox helpWindowTopBox = new VBox();
        helpWindowTopBox.setAlignment(Pos.CENTER);
        Text helpWindowTitle = new Text("Her kan du få hjelp til å bruke programmet.");
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
        feedbackField.setPrefHeight(150);
        feedbackField.setMaxWidth(600);
        feedbackField.setPromptText("Har du forslag til utvidet funksjon av programmet, eller har du funnet en bug? \r\rSkriv inn til oss her!");
        Button feedbackButton = new Button("Send tilbakemelding");
        feedbackButton.setAlignment(Pos.CENTER);
        feedbackButton.setMinWidth(250);
        feedbackButton.autosize();
        helpFeedbackBox.getChildren().addAll(feedbackField, feedbackButton);

        feedbackButton.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            String feedback = feedbackField.getText();
            String encodedFeedback;
            try {
                encodedFeedback = URLEncoder.encode(feedback, "UTF-8").replaceAll("\\+", "%20");
            } catch (UnsupportedEncodingException ex) {
                throw new RuntimeException(ex);
            }

            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.MAIL)) {
                    try {
                        URI mailto =  new URI("mailto:emillaa@stud.ntnu.no?subject=Tilbakemelding-budsjettprogram&body=" + encodedFeedback);
                        desktop.mail(mailto);
                    } catch (URISyntaxException | IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        helpWindow.setBottom(helpFeedbackBox);
        helpWindow.setVisible(false);
        windowPane.getChildren().addAll(helpWindow);


        //VBox for navigation menu on the left side of the window
        VBox navigationMenu = new VBox();
        navigationMenu.setId("navigationMenu");
        navigationMenu.setSpacing(5);

        //Buttons for navigating the application
        Button overviewButton = new Button("Oversikt");
        Button incomeButton = new Button("Inntekter");
        Button expensesButton = new Button("Utgifter");
        Button settingsButton = new Button("Innstillinger");
        Button helpButton = new Button("Hjelp");

        overviewButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Oversikt");
            helpWindow.setVisible(false);
            overviewWindow.setVisible(true);
            incomeWindow.setVisible(false);
            expensesWindow.setVisible(false);
            settingsWindow.setVisible(false);
            savingsWindow.setVisible(false);
        });

        expensesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Utgifter");
            incomeWindow.setVisible(false);
            expensesWindow.setVisible(true);
            overviewWindow.setVisible(false);
            helpWindow.setVisible(false);
            settingsWindow.setVisible(false);
            savingsWindow.setVisible(false);
        });

        settingsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Innstillinger");
            overviewWindow.setVisible(false);
            incomeWindow.setVisible(false);
            expensesWindow.setVisible(false);
            helpWindow.setVisible(false);
            settingsWindow.setVisible(true);
            savingsWindow.setVisible(false);
        });

        helpButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            titleText.setText("Hjelp");
            helpWindow.setVisible(true);
            overviewWindow.setVisible(false);
            incomeWindow.setVisible(false);
            expensesWindow.setVisible(false);
            settingsWindow.setVisible(false);
            savingsWindow.setVisible(false);
        });

        Stage logOutAlert = new Stage();
        logOutAlert.setTitle("Logg ut");


        Label logOutTitle = new Label("Er du sikker på at du ønsker å logge ut?");

        Button confirmLogOut = new Button("Logg ut");
        confirmLogOut.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                System.exit(0);
            }
        });


        VBox container = new VBox(logOutTitle, confirmLogOut);

        container.setSpacing(15);
        container.setPadding(new Insets(25));
        container.setAlignment(Pos.CENTER);
        Scene logoutScene = new Scene(container);

        logOutAlert.setScene(logoutScene);

        Button loggUtButton = new Button("Logg ut");
        loggUtButton.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                ObservableList<String> stylesheets = super.getStylesheets();
                String currentStylesheet = stylesheets.get(stylesheets.size() - 1);
                logoutScene.getStylesheets().add(currentStylesheet);
                logOutAlert.show();
            }
        });

        addIncomeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            userBudget.addIncome(incomeName.getText(), Integer.parseInt(incomeSum.getText()));


            updateBarChart(userBudget, barChart, series1);

            incomeData.set(FXCollections.observableArrayList(userBudget.getIncomeList()));
            incomeTableView.setItems(incomeData.get());
            incomePageData.set(FXCollections.observableArrayList(userBudget.getIncomeList()));
            incomePageTableView.setItems(incomePageData.get());


            incomeSum.setText("");
            incomeName.setText("");

            n.set(barChart.lookup(".data0.chart-bar"));
            n.get().setStyle("-fx-bar-fill: #398564");
            n.set(barChart.lookup(".data1.chart-bar"));
            n.get().setStyle("-fx-bar-fill: #e36700");

        });

        addExpensesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {

            userBudget.addExpense(expensesName.getText(), Integer.parseInt(expensesSum.getText()));


            //PIECHART UPDATE
            pieChartExpenses.set(FXCollections.observableArrayList());

            for (BudgetItem expense : expenses){
                pieChartExpenses.get().add(new PieChart.Data(expense.getBudgetItemName(), expense.getBudgetItemValue()));
            }
            chart.setData(pieChartExpenses.get());

            expensesPageData.set(FXCollections.observableArrayList(userBudget.getExpenseList()));
            expensesPageTableView.setItems(expensesPageData.get());
            expensesPageData.set(FXCollections.observableArrayList(userBudget.getExpenseList()));
            expensesPageTableView.setItems(expensesPageData.get());

            expensesData.set(FXCollections.observableArrayList(userBudget.getExpenseList()));
            expensesTableView.setItems(expensesData.get());

            updateBarChart(userBudget, barChart, series1);


            expensesSum.setText("");
            expensesName.setText("");

            Node b = barChart.lookup(".data0.chart-bar");
            b.setStyle("-fx-bar-fill: #398564");
            b = barChart.lookup(".data1.chart-bar");
            b.setStyle("-fx-bar-fill: #e36700");

        });


        navigationMenu.isFillWidth();
        navigationMenu.getChildren().addAll(overviewButton, incomeButton
                , expensesButton, settingsButton, helpButton, loggUtButton);
        root.setLeft(navigationMenu);
        root.setCenter(windowPane);
    }
    private void updateBarChart(Budget userOneBudget, BarChart<String, Number> barChart, XYChart.Series<String, Number> series1) {
        barChart.getData().remove(series1);
        series1.getData().clear();
        series1.getData().add(new XYChart.Data<>("Inntekter", userOneBudget.getTotalIncome()));
        series1.getData().add(new XYChart.Data<>("Utgifter", userOneBudget.getTotalExpense()));
        barChart.getData().add(series1);
    }
}
