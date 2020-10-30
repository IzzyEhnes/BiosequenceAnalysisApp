package app.address.model;

import java.util.ArrayList;

public class Protein
{
    private String protein;



    public Protein()
    {

    }



    public Protein(String inString)
    {
        protein = inString;
    }



    public void setProtein(String inString)
    {
        protein = inString;
    }



    public String getProtein()
    {
        return protein;
    }



    public int getNumberOfPeptides()
    {
        Protein inProtein = new Protein();
        inProtein = this;

        int count = 1;

        for (int i = 0; i < inProtein.getLength(); i++)
        {
            if (inProtein.protein.charAt(i) == ' ')
            {
                count++;
            }
        }

        return count;
    }



    public ArrayList<Peptide> getPeptidesInProtein()
    {
        Protein inProtein = new Protein();
        inProtein = this;

        int length = inProtein.getNumberOfPeptides();

        ArrayList<Peptide> peptideList = new ArrayList<>();

        StringBuilder peptideBuilder = new StringBuilder();

        for (int i = 0; i < inProtein.getLength(); i++)
        {
            peptideBuilder.append(inProtein.protein.charAt(i));

            if (inProtein.protein.charAt(i) == ' ' || i == inProtein.getLength() - 1)
            {
                Peptide temp = new Peptide(peptideBuilder.toString());
                peptideList.add(temp);
                peptideBuilder.setLength(0);
            }
        }

        return peptideList;
    }



    public int getPeptideIndexInProtein(ArrayList<Peptide> inList, Peptide target)
    {
        int index = 1;
        for (int i = 0; i < inList.size(); i++)
        {
            if (inList.get(i).equals(target))
            {
                index = i;
            }
        }

        return index;
    }



    public int getLength()
    {
        return protein.length();
    }



    @Override
    public String toString()
    {
        return protein;
    }
}
