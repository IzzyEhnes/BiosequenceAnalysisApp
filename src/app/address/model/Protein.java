package app.address.model;

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
