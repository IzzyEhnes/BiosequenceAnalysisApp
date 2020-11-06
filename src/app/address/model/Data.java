package app.address.model;

import javafx.beans.property.*;
import javafx.scene.text.TextFlow;

public class Data
{
    private final ObjectProperty<TextFlow> foundPeptide;
    private final StringProperty targetPeptide;
    private final IntegerProperty score;
    private final IntegerProperty row;
    private final IntegerProperty peptideNum;


    /**
     * Constructor for the Data class.
     *
     * @param inFoundPeptide    A Peptide found in a Protein in the uploaded data file
     * @param inTargetPeptide    The Peptide the user is searching for
     * @param inScore    The score representing the similarity between the target Peptide and the found peptide
     * @param inRow    The row in the uploaded data file that the Protein the Peptide was found in is located
     * @param inPeptideNum    The index of the Peptide's location in the Protein
     */
    public Data(TextFlow inFoundPeptide, String inTargetPeptide, Integer inScore, Integer inRow, Integer inPeptideNum)
    {
        this.foundPeptide = new SimpleObjectProperty<TextFlow>(inFoundPeptide);
        this.targetPeptide = new SimpleStringProperty(inTargetPeptide);
        this.score = new SimpleIntegerProperty(inScore);
        this.row = new SimpleIntegerProperty(inRow);
        this.peptideNum = new SimpleIntegerProperty(inPeptideNum);
    }



    public TextFlow getFoundPeptide()
    {
        return foundPeptide.get();
    }

    public void setFoundPeptide(TextFlow inProtein)
    {
        this.foundPeptide.set(inProtein);
    }

    public ObjectProperty<TextFlow> foundPeptideProperty()
    {
        return foundPeptide;
    }



    public String getPeptide()
    {
        return targetPeptide.get();
    }

    public void setPeptide(String inPeptide)
    {
        this.targetPeptide.set(inPeptide);
    }

    public StringProperty peptideProperty()
    {
        return targetPeptide;
    }



    public Integer getScore()
    {
        return score.get();
    }

    public void setScore(Integer inScore)
    {
        this.row.set(inScore);
    }

    public IntegerProperty scoreProperty()
    {
        return score;
    }



    public Integer getRow()
    {
        return row.get();
    }

    public void setRow(Integer inRow)
    {
        this.row.set(inRow);
    }

    public IntegerProperty rowProperty()
    {
        return row;
    }



    public Integer getPeptideNum()
    {
        return peptideNum.get();
    }

    public void setSimilarity(Integer inPeptideNum)
    {
        this.peptideNum.set(inPeptideNum);
    }

    public IntegerProperty peptideNumProperty()
    {
        return peptideNum;
    }
}