package app.address.view;

import app.address.MainApp;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileReminderDialogController
{
    private Stage dialogStage;
    private boolean okClicked = false;

    // Reference to the main application.
    private MainApp mainApp;



    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize()
    {
    }



    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage)
    {
        this.dialogStage = dialogStage;
    }



    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked()
    {
        return okClicked;
    }



    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleOK()
    {
        dialogStage.close();
    }
}
