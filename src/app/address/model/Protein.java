package app.address.model;

import java.util.ArrayList;


public class Protein
{
    private String protein;



    /**
     * The default constructor for Protein objects.
     */
    public Protein()
    {

    }


    /**
     * The parameterized constructor for the Protein class.
     * Sets this.protein to inString.
     *
     * @param inString
     */
    public Protein(String inString)
    {
        this.protein = inString;
    }



    /**
     * The setter for the member variable, protein.
     * Sets this.protein to inString.
     *
     * @param inString
     */
    public void setProtein(String inString)
    {
        this.protein = inString;
    }



    /**
     * The getter for the member variable, protein.
     *
     * @return
     */
    public String getProtein()
    {
        return protein;
    }



    /**
     * This method returns the number of Peptides that make up the calling Protein.
     * Peptides within the Protein are separated by a single whitespace.
     *
     * @return count    The number of Peptides within the Protein
     */
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



    /**
     * getPeptidesInProtein() identifies the individual Peptides found in the calling
     * Protein and adds them to an ArrayList of type Peptide, which is then returned.
     *
     * @return peptideList    An ArrayList that contains all of the Peptides found in
     *                        the calling Protein
     */
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



    /**
     * This method find the location of the target Peptide within the Protein, in relation
     * to the other Peptides in the Protein.
     *
     * @param inList    An ArrayList of type Peptide that contains Peptides
     * @param target    The Peptide that is being searched for
     * @return index    The index of the target Peptide within inList
     */
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



    /**
     * getLength() returns the length of the calling object's protein.
     *
     * @return this.protein.length()    The length of the calling object's protein
     */
    public int getLength()
    {
        return this.protein.length();
    }



    /**
     * Overrides the toString() method, and returns protein as a String value.
     *
     * @return this.protein    protein's String value
     */
    @Override
    public String toString()
    {
        return this.protein;
    }
}
