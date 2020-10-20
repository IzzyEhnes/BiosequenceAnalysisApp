package app.address.view;

import app.address.model.Peptide;
import app.address.model.Protein;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import app.address.MainApp;
import app.address.model.Data;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class AppOverviewController
{
    @FXML
    private TableView<Data> dataTable;

    @FXML
    private TableColumn<Data, TextFlow> proteinColumn;
    @FXML
    private TableColumn<Data, String> peptideColumn;
    @FXML
    private TableColumn<Data, String> rowColumn;
    @FXML
    private TableColumn<Data, String> begIndexColumn;
    @FXML
    private TableColumn<Data, String> endIndexColumn;
    @FXML
    private TableColumn<Data, String> similarityColumn;


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
    private void initialize() {
        // Initialize the data table with the five columns.
        proteinColumn.setCellValueFactory(cellData -> cellData.getValue().proteinProperty());
        peptideColumn.setCellValueFactory(cellData -> cellData.getValue().peptideProperty());
        rowColumn.setCellValueFactory(cellData -> cellData.getValue().rowProperty());
        begIndexColumn.setCellValueFactory(cellData -> cellData.getValue().begIndexProperty());
        endIndexColumn.setCellValueFactory(cellData -> cellData.getValue().endIndexProperty());
        similarityColumn.setCellValueFactory(cellData -> cellData.getValue().similarityProperty());
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
        mainApp.tableData.clear();

        System.out.println("SEARCHING...");

        Peptide targetPeptide = new Peptide(peptideField.getText());

        mainApp.matches = targetPeptide.findPotentialMatches(mainApp.getProteinList());

        for (Protein p : mainApp.matches.keySet())
        {
            ArrayList<String> list = mainApp.matches.get(p);

            Text a = new Text("Izzy");
            Text b = new Text("Seamus");

            a.setFill(Color.BLUE);
            b.setFill(Color.GREEN);

            TextFlow c = new TextFlow(a,b);


            c.setPrefHeight(c.prefHeight(proteinColumn.getWidth()));


            /*
            Text protein = new Text();
            Text temp = new Text();

            for (int i = 0; i < p.getLength(); i++)
            {
                if (i < Integer.valueOf(list.get(2)) || i > Integer.valueOf(list.get(3)))
                {
                    protein.setText(protein.getText() + String.valueOf(p.toString().charAt(i)));
                }


                else if (i > Integer.valueOf(list.get(2)) && i < Integer.valueOf(list.get(3)))
                {
                    temp.setText(String.valueOf(p.toString().charAt(i)));

                    temp.setFill(Color.RED);

                    protein.setText(protein.getText() + temp);
                }

            }

             */


            mainApp.tableData.add(new Data(c, targetPeptide.getPeptide(), list.get(1), list.get(2), list.get(3), list.get(4)));
        }

        System.out.println("END SEARCH");


        searchClicked = true;
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
