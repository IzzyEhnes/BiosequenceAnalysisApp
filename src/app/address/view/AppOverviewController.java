package app.address.view;

import app.address.model.Peptide;
import app.address.model.Protein;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.address.MainApp;
import app.address.model.Data;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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
    private TableColumn<Data, Integer> peptideNumColumn;

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
    public AppOverviewController() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {

        scoreColumn.setSortType(TableColumn.SortType.ASCENDING);
        dataTable.getSortOrder().add(scoreColumn);
        dataTable.sort();

        // Initialize the data table with the five columns.
        foundPeptideColumn.setCellValueFactory(cellData -> cellData.getValue().foundPeptideProperty());
        targetPeptideColumn.setCellValueFactory(cellData -> cellData.getValue().peptideProperty());
        scoreColumn.setCellValueFactory(cellData -> cellData.getValue().scoreProperty().asObject());
        rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty().asObject());
        peptideNumColumn.setCellValueFactory(cellData -> cellData.getValue().peptideNumProperty().asObject());
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

            Peptide targetPeptide = new Peptide(peptideField.getText());

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
                    mainApp.tableData.add(new Data(foundPeptide, targetPeptide.getPeptide(), list.get(0), list.get(1), list.get(2)));
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
