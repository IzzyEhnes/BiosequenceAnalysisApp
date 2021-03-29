package app.address.view;

import app.address.MainApp;
import app.address.model.Data;
import app.address.model.Peptide;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.TextFlow;
import java.util.ArrayList;

public class AppOverviewController
{
    @FXML
    private TableView<Data> dataTable;

    @FXML
    private TableColumn<Data, TextFlow> foundPeptideColumn;
    @FXML
    private TableColumn<Data, String> targetPeptideColumn;
    @FXML
    private TableColumn<Data, Integer> scoreColumn;
    @FXML
    private TableColumn<Data, Integer> rowColumn;

    @FXML
    private TextField peptideField;

    private boolean searchClicked = false;
    private boolean clearTableClicked = false;

    // Reference to the main application.
    private MainApp mainApp;

    private Data record;

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public AppOverviewController()
    {
    }



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
        // Initialize the data table with the five columns.
        foundPeptideColumn.setCellValueFactory(cellData -> cellData.getValue().foundPeptideProperty());
        targetPeptideColumn.setCellValueFactory(cellData -> cellData.getValue().peptideProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty().asObject());
    }


    /**
     * Returns true if the user clicked Search, false otherwise.
     *
     * @return
     */
    public boolean isSearchClicked() {
        return searchClicked;
    }



    /**
     * Called when the user clicks Search
     */
    @FXML
    private void handleSearch()
    {
        if (!mainApp.fileOpened)
        {
            boolean okClicked = mainApp.showFileReminderDialog();
        }

        else
        {
            mainApp.tableData.clear();

            System.out.println("SEARCHING...");

            Peptide targetPeptide = new Peptide(peptideField.getText().toUpperCase());

            mainApp.matches = targetPeptide.findPotentialMatches(mainApp.getProteinList());

            for (Peptide p : mainApp.matches.keySet())
            {
                ArrayList<Integer> list = mainApp.matches.get(p);

                String LCS = p.getLongestCommonSubsequence(targetPeptide, p);

                TextFlow foundPeptide = new TextFlow();
                foundPeptide = p.colorCode(p, LCS);

                foundPeptide.setPrefHeight(foundPeptide.prefHeight(foundPeptideColumn.getWidth()));

                int score = p.getScore(LCS);

                if (score >= 2)
                {
                    mainApp.tableData.add(new Data(foundPeptide, targetPeptide.getPeptide(), list.get(0), list.get(1)));
                }
            }

            System.out.println("END SEARCH");
        }
    }



    @FXML
    private void handleClearTable()
    {
        mainApp.tableData.clear();

        clearTableClicked = true;
    }



    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        dataTable.setItems(mainApp.getData());
    }
}