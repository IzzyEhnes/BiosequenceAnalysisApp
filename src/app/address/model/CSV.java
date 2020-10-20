package app.address.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CSV
{

    public static ArrayList<Protein> readCSV(String inFile)
    {
        Scanner fileReader = null;
        // Try opening file fileName
        try
        {
            fileReader = new Scanner(new File(inFile));
        }

        // If file cannot be opened, display error and exit program
        catch (FileNotFoundException fileError)
        {
            System.out.println(String.format("There was a problem opening file \"%s\": error = %s", inFile, fileError.getMessage()));

            System.out.println("Exiting program...");

            System.exit(1);
        }



        // Read CSV file and add proteins to an ArrayList
        ArrayList<Protein> proteinList = new ArrayList();
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

        return proteinList;
    }



    public static void writeCSV(HashMap<Protein, ArrayList<String>> inMap)
    {
        try
        {
            File csv = new File("/home/izzy/Desktop/Repos/BiosequenceAnalysis/output.csv");

            csv.createNewFile();

            FileWriter csvWriter = new FileWriter(csv);

            csvWriter.append("PROTEIN").append(',');
            csvWriter.append("PEPTIDE").append(',');
            csvWriter.append("ROW").append(',');
            csvWriter.append("BEGINNING INDEX").append(',');
            csvWriter.append("END INDEX").append('\n');

            for (Protein p : inMap.keySet())
            {
                csvWriter.append(p.getProtein()).append(',');

                ArrayList<String> list = inMap.get(p);

                for (String s : list)
                {
                    csvWriter.append(s).append(',');
                }

                csvWriter.append('\n');
            }

            csvWriter.flush();
            csvWriter.close();
        }

        catch (IOException e)
        {
            System.out.println("Error creating or editing file: ");
            e.printStackTrace();
        }
    }
}
