package app.address;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.prefs.Preferences;
import app.address.model.Data;
import app.address.model.Peptide;
import app.address.model.Protein;
import app.address.view.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class MainApp extends Application
{
    public boolean fileOpened = false;
    public Stage primaryStage;
    private BorderPane rootLayout;

    public ObservableList<Data> tableData = FXCollections.observableArrayList();
    public ArrayList<Protein> proteinList = new ArrayList<>();
    public HashMap<Peptide, ArrayList<Integer>> matches = new HashMap<>();



    /**
     * The default constructor.
     */
    public MainApp()
    {

    }



    /**
     * The getter for tableData.
     *
     * @return tableData    The ObservableList that contains row and column data
     */
    public ObservableList<Data> getData()
    {
        return tableData;
    }



    /**
     * The getter for proteinList.
     *
     * @return proteinList    An ArrayList of type Protein that contains all of the Proteins found in the uploaded CSV
     */
    public ArrayList<Protein> getProteinList()
    {
        return proteinList;
    }



    /**
     * Sets the primary stage and starts the application.
     *
     * @param primaryStage    The primary stage of the application
     */
    @Override
    public void start(Stage primaryStage)
    {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Biosequence Analyzer");

        // Set icon of the application
        this.primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/Media/app_logo.png")));

        initRootLayout();

        showAppOverview();
    }



    /**
     * Initializes the root layout.
     */
    public void initRootLayout()
    {
        try
        {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Shows the app overview inside the root layout.
     */
    public void showAppOverview()
    {
        try
        {
            // Load app overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AppOverview.fxml"));
            AnchorPane personOverview = loader.load();

            // Set app overview into the center of root layout.
            rootLayout.setCenter(personOverview);

            // Give the controller access to the main app.
            AppOverviewController controller = loader.getController();
            controller.setMainApp(this);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }
    }



    /**
     * Returns the main stage.
     *
     * @return primaryStage    The main stage
     */
    public Stage getPrimaryStage()
    {
        return primaryStage;
    }



    /**
     * The main method.
     *
     * @param args    Command line argument(s)
     */
    public static void main(String[] args)
    {
        launch(args);
    }



    /**
     * This method displays a dialog window alerting the user that they've attempted to perform a search without first
     * uploading a data file and that their search has failed.
     *
     * @return true if the the dialog is showing and false otherwise
     */
    public boolean showFileReminderDialog()
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileReminderDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("File Error");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            FileReminderDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        }

        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * This method displays a dialog window alerting the user that they've attempted to perform a search without first
     * uploading a data file and that their search has failed.
     *
     * @return true if the the dialog is showing and false otherwise
     */
    public boolean showScoreErrorDialog()
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/ScoreErrorDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Score Boundary Error");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            ScoreErrorDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        }

        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * This method displays a dialog window containing how-to information.
     *
     * @return true if the the dialog is showing and false otherwise
     */
    public boolean showHowToGuideDialog()
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/HowToGuideDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("How To Guide");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            HowToGuideDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        }

        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * This method displays a dialog window containing about information.
     *
     * @return true if the the dialog is showing and false otherwise
     */
    public boolean showAboutDialog()
    {
        try
        {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AboutDialog.fxml"));
            AnchorPane page = loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("About");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            AboutDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();

        }

        catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return the filepath specified by the user, or null if none was specified
     */
    public File getProteinFilePath()
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);

        if (filePath != null)
        {
            return new File(filePath);
        }

        else
        {
            return null;
        }
    }



    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setProteinFilePath(File file)
    {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);

        if (file != null)
        {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("Biosequence Analyzer - " + file.getName());

            fileOpened = true;
        }

        else
        {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("Biosequence Analyzer");
        }
    }



    /**
     * Loads Protein data from the specified file.
     *
     * @param inFile    The file uploaded by the user
     */
    public void loadProteinDataFromFile(File inFile)
    {
        Scanner fileReader = null;

        try
        {
            // Try opening file fileName
            fileReader = new Scanner(inFile);
        }

        catch (Exception e)
        { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + inFile.getPath());

            alert.showAndWait();
        }

        // Read CSV file and add proteins to an ArrayList
        while (true)
        {
            assert fileReader != null;
            if (!fileReader.hasNextLine()) break;
            String line = fileReader.nextLine().trim();

            // If the row is column names or is empty, skip it
            if (line.length() != 0 && line.charAt(0) != '#')
            {
                Protein p = new Protein(line.toUpperCase());
                proteinList.add(p);
            }
        }

        // Save the file path to the registry.
        setProteinFilePath(inFile);
    }



    /**
     * Saves the current data to the specified file.
     *
     * @param csv    The CSV file that data will be written to
     * @param inMap    The HashMap containing the Peptide data
     */
    public void savePeptideDataToFile(File csv, HashMap<Peptide, ArrayList<Integer>> inMap)
    {
        try
        {
            csv.createNewFile();

            FileWriter csvWriter = new FileWriter(csv);

            csvWriter.append("FOUND PEPTIDE").append(',');
            csvWriter.append("TARGET PEPTIDE").append(',');
            csvWriter.append("SCORE").append(',');
            csvWriter.append("ROW").append(',').append('\n');

            for (Data d : tableData)
            {
                // Extracting a String from the TextFlow representing the found peptide
                StringBuilder foundPeptide = new StringBuilder();
                d.getFoundPeptide().getChildren().stream()
                        .filter(t -> Text.class.equals(t.getClass()))
                        .forEach(t -> foundPeptide.append(((Text) t).getText()));

                csvWriter.append(foundPeptide.toString()).append(',');
                csvWriter.append(d.getTargetPeptide()).append(',');
                csvWriter.append(String.valueOf(d.getScore())).append(',');
                csvWriter.append(String.valueOf(d.getRow())).append('\n');
            }

            csvWriter.flush();
            csvWriter.close();
        }

        // catches ANY exception
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + csv.getPath());

            alert.showAndWait();
        }
    }
}