package app.address;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.prefs.Preferences;

import app.address.model.Data;
import app.address.model.Peptide;
import app.address.model.Protein;
import app.address.view.AppOverviewController;
import app.address.view.FileReminderDialogController;
import app.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application
{

    public boolean fileOpened = false;
    public Stage primaryStage;
    private BorderPane rootLayout;

    public ObservableList<Data> tableData = FXCollections.observableArrayList();

    public ArrayList<Protein> proteinList = new ArrayList<>();
    public HashMap<Peptide, ArrayList<Integer>> matches = new HashMap<Peptide, ArrayList<Integer>>();

    public MainApp()
    {

    }


    public ObservableList<Data> getData()
    {
        return tableData;
    }


    public ArrayList<Protein> getProteinList()
    {
        return proteinList;
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BiosequenceAnalysisApp");

        initRootLayout();

        showAppOverview();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

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
     * Shows the person overview inside the root layout.
     */
    public void showAppOverview()
    {
        try
        {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AppOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
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
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }



    public boolean showFileReminderDialog()
    {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/FileReminderDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

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
     * Returns the person file preference, i.e. the file that was last opened.
     * The preference is read from the OS specific registry. If no such
     * preference can be found, null is returned.
     *
     * @return
     */
    public File getProteinFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }

    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     *
     * @param file the file or null to remove the path
     */
    public void setProteinFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("BiosequenceAnalysisApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("BiosequenceAnalysisApp");
        }
    }


    /**
     * Loads protein data from the specified file.
     *
     * @param inFile
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
        while (fileReader.hasNextLine())
        {
            String line = fileReader.nextLine().trim();

            // If the row is column names or is empty, skip it
            if (line.charAt(0) == '#' || line.length() == 0)
            {
                continue;
            }

            else
            {
                Protein p = new Protein(line);
                proteinList.add(p);
            }
        }
        // Save the file path to the registry.
        setProteinFilePath(inFile);

        System.out.println("UPLOADED");
    }





    /**
     * Saves the current person data to the specified file.
     *
     * @param csv
     * @param inMap
     */
    public void savePersonDataToFile(File csv, HashMap<Peptide, ArrayList<Integer>> inMap)
    {
        try {

            csv.createNewFile();

            FileWriter csvWriter = new FileWriter(csv);

            csvWriter.append("PROTEIN").append(',');
            csvWriter.append("PEPTIDE").append(',');
            csvWriter.append("ROW").append(',');
            csvWriter.append("BEGINNING INDEX").append(',');
            csvWriter.append("END INDEX").append('\n');

            for (Peptide p : inMap.keySet())
            {
                csvWriter.append(p.getPeptide()).append(',');

                ArrayList<Integer> list = inMap.get(p);

                for (Integer s : list)
                {
                    csvWriter.append(Character.highSurrogate(s)).append(',');
                }

                csvWriter.append('\n');
            }

            csvWriter.flush();
            csvWriter.close();
        }

        catch (Exception e)
        { // catches ANY exception
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + csv.getPath());

            alert.showAndWait();
        }
    }
}