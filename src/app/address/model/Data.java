package app.address.model;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import javax.swing.*;

public class Data
{
    private final ObjectProperty<TextFlow> protein;
    private final StringProperty peptide;
    private final StringProperty row;
    private final StringProperty begIndex;
    private final StringProperty endIndex;
    private final StringProperty similarity;

    public Data(TextFlow inProtein, String inPeptide, String inRow, String inBegIndex, String inEndIndex, String inSimilarity)
    {
        this.protein = new SimpleObjectProperty<TextFlow>(inProtein);
        this.peptide = new SimpleStringProperty(inPeptide);
        this.row = new SimpleStringProperty(inRow);
        this.begIndex = new SimpleStringProperty(inBegIndex);
        this.endIndex = new SimpleStringProperty(inEndIndex);
        this.similarity = new SimpleStringProperty(inSimilarity);
    }



    public TextFlow getProtein()
    {
        return protein.get();
    }

    public void setProtein(TextFlow inProtein)
    {
        this.protein.set(inProtein);
    }

    public ObjectProperty<TextFlow> proteinProperty()
    {
        return protein;
    }



    public String getPeptide()
    {
        return peptide.get();
    }

    public void setPeptide(String inPeptide)
    {
        this.peptide.set(inPeptide);
    }

    public StringProperty peptideProperty()
    {
        return peptide;
    }



    public String getRow()
    {
        return row.get();
    }

    public void setRow(String inRow)
    {
        this.row.set(inRow);
    }

    public StringProperty rowProperty()
    {
        return row;
    }



    public String getBegIndex()
    {
        return begIndex.get();
    }

    public void setBegIndex(String inBegIndex)
    {
        this.begIndex.set(inBegIndex);
    }

    public StringProperty begIndexProperty()
    {
        return begIndex;
    }



    public String getEndIndex()
    {
        return endIndex.get();
    }

    public void setEndIndex(String inEndIndex)
    {
        this.endIndex.set(inEndIndex);
    }

    public StringProperty endIndexProperty()
    {
        return endIndex;
    }



    public String getSimilarity()
    {
        return similarity.get();
    }

    public void setSimilarity(String inSimilarity)
    {
        this.endIndex.set(inSimilarity);
    }

    public StringProperty similarityProperty()
    {
        return similarity;
    }
}