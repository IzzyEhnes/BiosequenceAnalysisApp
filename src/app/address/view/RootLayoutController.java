package app.address.view;

import app.address.MainApp;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import java.io.File;


public class RootLayoutController
{

    // Reference to the main application
    private MainApp mainApp;



    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp  A reference to the main application
     */
    public void setMainApp(MainApp mainApp)
    {
        this.mainApp = mainApp;
    }



    /**
     * Opens a FileChooser to let the user select an address book to load.
     */
    @FXML
    public void handleOpen()
    {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show open file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        mainApp.proteinList.clear();

        if (file != null)
        {
            mainApp.loadProteinDataFromFile(file);
        }

        //mainApp.fileOpened = true;
    }



    /**
     * Opens a FileChooser to let the user pick a file to save the data to.
     */
    @FXML
    private void handleSaveAs()
    {
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

        if (file != null)
        {
            // Make sure it has the correct extension
            if (!file.getPath().endsWith(".csv"))
            {
                file = new File(file.getPath() + ".csv");
            }

            mainApp.savePeptideDataToFile(file, mainApp.matches);
        }
    }



    /**
     * Saves the data and exits the application.
     */
    @FXML
    private void handleSaveAndExit()
    {
        handleSaveAs();
        handleExit();
    }



    /**
     * Closes the application.
     */
    @FXML
    private void handleExit()
    {
        System.exit(0);
    }
}
