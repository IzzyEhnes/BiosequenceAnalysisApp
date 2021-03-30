package app.address.model;



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
     * @param inString  The String that this.protein will be set to
     */
    public Protein(String inString)
    {
        this.protein = inString;
    }



    /**
     * The setter for the member variable, protein.
     * Sets this.protein to inString.
     *
     * @param inString   The String that this.protein will be set to
     */
    public void setProtein(String inString)
    {
        this.protein = inString;
    }



    /**
     * The getter for the member variable, protein.
     *
     * @return protein   The member variable, protein
     */
    public String getProtein()
    {
        return protein;
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
