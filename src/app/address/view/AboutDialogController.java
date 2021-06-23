package app.address.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;



public class AboutDialogController
{
    private Stage dialogStage;
    private boolean okClicked = false;



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
