package app.address.model;

import app.address.model.Protein;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class Peptide
{
    private String peptide;



    public Peptide()
    {

    }



    public Peptide(String inString)
    {
        peptide = inString;
    }



    public void setPeptide(String inString)
    {
        peptide = inString;

    }



    public String getPeptide()
    {
        return peptide;
    }






    public HashMap<Protein, ArrayList<String>> findPotentialMatches(ArrayList<Protein> inList)
    {
        HashMap<Protein, ArrayList<String>> matches = new HashMap<Protein, ArrayList<String>>();

        String inPeptide = this.peptide;

        int peptideLength = peptide.length();

        String proteinSubstring = "";

        int row = 1;
        for (Protein currentProtein : inList)
        {
            row++;


            int beginningIndex = 0;
            int endIndex = 0;

            boolean match = false;
            for (int j = 0; j < currentProtein.getLength() && match == false; j++)
            {
                if (currentProtein.getProtein().charAt(j) == inPeptide.charAt(0))
                {
                    endIndex = j + peptideLength;

                    if (endIndex > currentProtein.getLength())
                    {
                        endIndex = currentProtein.getLength();
                    }

                    proteinSubstring = currentProtein.getProtein().substring(j, endIndex);

                    //System.out.println("Protein substring");
                    //System.out.println(proteinSubstring);

                    match = isMatch(peptide, proteinSubstring);

                    beginningIndex = j;
                }
            }

            if (match)
            {
                int sequenceIdentity = getPercentSequenceIdentity(peptide, proteinSubstring);

                ArrayList<String> proteinData = new ArrayList<String>();

                proteinData.add(peptide);
                proteinData.add(Integer.toString(row));
                proteinData.add(Integer.toString(beginningIndex));
                proteinData.add(Integer.toString(endIndex));
                proteinData.add(Integer.toString(sequenceIdentity));

                matches.put(currentProtein, proteinData);
            }
        }

        return matches;
    }






    public boolean isMatch(String peptide, String proteinSubstring)
    {
        if (peptide.equals(proteinSubstring))
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    public int getPercentSequenceIdentity(String peptide, String proteinSubstring)
    {
        int percentIdentity = 0;

        if (peptide.equals(proteinSubstring))
        {
            return 100;
        }

        return percentIdentity;
    }



    @Override
    public String toString()
    {
        return peptide;
    }
}