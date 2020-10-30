package app.address.model;

import app.address.model.Protein;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.ArrayList;
import java.util.HashMap;

public class Peptide
{
    private String peptide;
    private int LCSBeginningIndex;
    private int LCSEndIndex;
    private int greenCount;
    private int redCount;



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



    public int length()
    {
        return this.peptide.length();
    }



    public HashMap<Peptide, ArrayList<Integer>> findPotentialMatches(ArrayList<Protein> inList)
    {
        HashMap<Peptide, ArrayList<Integer>> matches = new HashMap<Peptide, ArrayList<Integer>>();

        Peptide targetPeptide = this;

        ArrayList<Peptide> peptideList = new ArrayList<>();

        int row = 0;
        for (Protein protein : inList)
        {
            row++;

            peptideList = protein.getPeptidesInProtein();

            int beginningIndex = 0;
            int endIndex = 0;

            boolean match = false;

            for (Peptide currentPeptide : peptideList)
            {
                match = isMatch(targetPeptide, currentPeptide);

                if (match)
                {
                    String LCS = currentPeptide.getLongestCommonSubsequence(targetPeptide, currentPeptide);

                    TextFlow foundPeptide = new TextFlow();
                    foundPeptide = currentPeptide.colorCode(currentPeptide, LCS);

                    int score = currentPeptide.getScore(LCS);

                    if (score >= 2) {
                        int peptideNum = protein.getPeptideIndexInProtein(peptideList, currentPeptide);

                        ArrayList<Integer> proteinData = new ArrayList<Integer>();

                        proteinData.add(score);
                        proteinData.add(row);
                        proteinData.add(peptideNum);

                        matches.put(currentPeptide, proteinData);
                    }
                }
            }
        }

        return matches;
    }



    public boolean isMatch(Peptide targetPeptide, Peptide inPeptide)
    {
        String LCS = getLongestCommonSubsequence(targetPeptide, inPeptide);

        if (LCS.length() > 2)
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    public String getLongestCommonSubsequence(Peptide targetPeptide, Peptide inPeptide)
    {
        int m = targetPeptide.length();
        int n = inPeptide.length();

        int[][] L = new int[m + 1][n + 1];

        for (int i = 0; i <= m; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (i == 0 || j == 0)
                {
                    L[i][j] = 0;
                }

                else if (targetPeptide.peptide.charAt(i - 1) == inPeptide.peptide.charAt(j - 1))
                {
                    L[i][j] = L[i - 1][j - 1] + 1;
                    LCSEndIndex = j - 1;
                }

                else
                {
                    L[i][j] = findMax(L[i - 1][j], L[i][j - 1]);
                }
            }
        }

        int index = L[m][n];
        int temp = index;

        char[] LCS = new char[index + 1];

        int i = m;
        int j = n;
        while (i > 0 && j > 0)
        {
            if (targetPeptide.peptide.charAt(i - 1) == inPeptide.peptide.charAt(j - 1))
            {
                LCS[index - 1] = targetPeptide.peptide.charAt(i - 1);

                i--;
                j--;
                index--;
            }

            else if (L[i - 1][j] > L[i][j - 1])
            {
                i--;
            }

            else
            {
                j--;
            }
        }

        StringBuilder sb = new StringBuilder();

        for (int k = 0; k <= temp; k++)
        {
            sb.append(LCS[k]);
        }

        return sb.toString();
    }



    public int findMax(int a, int b)
    {
        if (a > b)
        {
            return a;
        }

        else
        {
            return b;
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



    public int isSimilar(char target, char inChar)
    {
        switch (target)
        {
            case 'A':
                if (inChar == 'A')
                {
                    return 2;
                }

                break;
        }

        return 0;
    }


/*
    public void setLCSBeginningIndex(Peptide inPeptide, String inLCS)
    {
        char beginningChar = inLCS.charAt(0);
        char endChar = inLCS.charAt(inLCS.length() - 2);

        System.out.println("beginningChar: " + beginningChar);
        System.out.println("endChar: " + endChar);

        int LCDStart = 0;
        int LCDIndex = 0;
        boolean afterStart = false;
        for (int i = 0; i < inPeptide.peptide.length(); i++)
        {
            if (inPeptide.peptide.charAt(i) == beginningChar && !afterStart)
            {
                LCDStart = i;
                LCDIndex++;
            }
        }
    }

 */



    public void setLCSBeginningIndex(String inLCS)
    {
        int LCSLength = inLCS.length();

        int index = LCSLength - 2;
        for (int i = this.LCSEndIndex; i > 0; i--)
        {
            if (this.peptide.charAt(i) == inLCS.charAt(index))
            {
                index--;
            }

            if (index == -1)
            {
                this.LCSBeginningIndex = i;
                break;
            }
        }
    }



    public TextFlow colorCode(Peptide inPeptide, String LCS)
    {
        TextFlow textFlow = new TextFlow();

        System.out.println("inPeptide: " + inPeptide);
        System.out.println("inPeptide.LCSEndIndex: " + inPeptide.LCSEndIndex);

        inPeptide.setLCSBeginningIndex(LCS);

        System.out.println("inPeptide.LCSBeginningIndex: " + inPeptide.LCSBeginningIndex);

        int indexOfLastGreen = 0;
        int indexOfLastRed = 0;
        int LCSCount = 0;
        for (int i = 0; i < inPeptide.length(); i++)
        {
            Text currentChar = new Text(String.valueOf(inPeptide.peptide.charAt(i)));

            if (i < inPeptide.LCSBeginningIndex || i > inPeptide.LCSEndIndex)
            {
                currentChar.setFill(Color.BLACK);
            }

            else
            {
                if (inPeptide.peptide.charAt(i) == LCS.charAt(LCSCount))
                {
                    currentChar.setFill(Color.GREEN);
                    indexOfLastGreen = i;
                    LCSCount++;
                    inPeptide.greenCount++;
                }

                else
                {
                    currentChar.setFill(Color.RED);
                    indexOfLastRed = i;
                    inPeptide.redCount++;
                }
            }

            textFlow.getChildren().add(currentChar);
        }

        System.out.println("indexOfLastRed: " + indexOfLastRed);
        System.out.println("indexOfLastGreen: " + indexOfLastGreen);

        textFlow.getChildren().remove(indexOfLastGreen + 1, inPeptide.length());

        for (int i = indexOfLastGreen + 1; i < inPeptide.length(); i++)
        {
            Text currentChar = new Text(String.valueOf(inPeptide.peptide.charAt(i)));
            currentChar.setFill(Color.BLACK);
            textFlow.getChildren().add(currentChar);

            if (i > indexOfLastGreen && i < indexOfLastRed + 1)
            {
                redCount--;
            }
        }

        System.out.println("greenCount: " + inPeptide.greenCount);
        System.out.println("redCount: " + inPeptide.redCount);
        System.out.println("Score: " + inPeptide.getScore(LCS));

        System.out.println();

        return textFlow;
    }



    public int getScore(String inLCS)
    {
        int redCount = this.redCount;
        int greenCount = this.greenCount;
        int missing = inLCS.length() - greenCount;
        int score = 0;

        score = (2 * greenCount) - missing - redCount;

        return score;
    }



    public boolean equals(Peptide inPeptide)
    {
        if (this.peptide.equals(inPeptide.peptide))
        {
            return true;
        }

        else
        {
            return false;
        }
    }



    @Override
    public String toString()
    {
        return peptide;
    }
}