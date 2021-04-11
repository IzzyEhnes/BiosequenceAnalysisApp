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
    @FXML
    private TextField minScoreField;
    @FXML
    private TextField maxScoreField;

    // Reference to the main application.
    private MainApp mainApp;

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
        targetPeptideColumn.setCellValueFactory(cellData -> cellData.getValue().targetPeptideProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty().asObject());
    }


    /**
     * Called when the user clicks Search
     */
    @FXML
    private void handleSearch()
    {
        if (!mainApp.fileOpened)
        {
            mainApp.showFileReminderDialog();
        }

        else
        {
            mainApp.tableData.clear();

            int minScore = 2;
            int maxScore = Integer.MAX_VALUE;

            if (!minScoreField.getText().trim().isEmpty())
            {
                minScore = Integer.parseInt(minScoreField.getText());
            }

            if (!maxScoreField.getText().trim().isEmpty())
            {
                maxScore = Integer.parseInt(maxScoreField.getText());
            }

            if (minScore > maxScore || minScore < 0 || maxScore < 0)
            {
                mainApp.showScoreErrorDialog();
            }

            Peptide targetPeptide = new Peptide(peptideField.getText().toUpperCase());

            mainApp.matches = targetPeptide.findPotentialMatches(mainApp.getProteinList());

            for (Peptide p : mainApp.matches.keySet())
            {
                ArrayList<Integer> list = mainApp.matches.get(p);

                String LCS = p.getLongestCommonSubsequence(targetPeptide, p);

                TextFlow foundPeptide = p.colorCode(p, LCS);

                foundPeptide.setPrefHeight(foundPeptide.prefHeight(foundPeptideColumn.getWidth()));

                int score = p.getScore(LCS);

                if (score >= minScore && score <= maxScore)
                {
                    mainApp.tableData.add(new Data(foundPeptide, targetPeptide.getPeptide(), list.get(0), list.get(1)));
                }
            }
        }
    }



    @FXML
    private void handleClearTable()
    {
        mainApp.tableData.clear();
    }



    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp   A reference to the main application
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        dataTable.setItems(mainApp.getData());
    }
}