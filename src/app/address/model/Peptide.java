package app.address.model;

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



    /**
     * The default constructor for the Peptide class.
     */
    public Peptide()
    {

    }



    /**
     * The parameterized constructor for the Peptide class.
     *
     * @param inString    The calling Peptide's peptide is set to inString
     */
    public Peptide(String inString)
    {
        this.peptide = inString;
    }



    /**
     * The setter method for the peptide member variable.
     *
     * @param inString    this.peptide is set to inString
     */
    public void setPeptide(String inString)
    {
        this.peptide = inString;

    }



    /**
     * The getter method for the peptide class.
     *
     * @return this.peptide    The calling object's peptide, of type String
     */
    public String getPeptide()
    {
        return this.peptide;
    }



    /**
     * The length method of the Peptide class that returns the length
     * of the calling object's peptide.
     *
     * @return this.peptide.length()    The length of the calling object's peptide
     */
    public int length()
    {
        return this.peptide.length();
    }



    /**
     * Given an ArrayList of Proteins, this method finds potential "matches" between Peptides found in the
     * Proteins contained in the ArrayList and the calling Peptide. Whether the calling Peptide and a
     * Peptide found within a Protein in inList is a "match" or not depends on whether they share a longest
     * common subsequence of length >= 2 (@see getLongestCommonSubsequence and match methods).
     *
     * If a match is found, it is given a score based on its similarity to the target Peptide (@see getScore method).
     * If the score is >= 3, the Peptide's score, the location of the Peptide within the Protein, and the row
     * the Protein the Peptide was found in was located in the original CSV is added to the ArrayList "proteinData"
     * associated with the current Peptide. The found Peptide and its ArrayList are then added to the HashMap "matches"
     * as a key and value, respectively.
     *
     * @param inList    An ArrayList that contains Proteins found in the uploaded CSV
     * @return matches    A HashMap, with keys being of type Peptide and values of type ArrayList<Integer>.
     *                    The elements of the ArrayList include the Peptide's score, the location of the
     *                    Peptide within the Protein, and the row the Protein the Peptide was found in was
     *                    located in the original CSV
     */
    public HashMap<Peptide, ArrayList<Integer>> findPotentialMatches(ArrayList<Protein> inList)
    {
        HashMap<Peptide, ArrayList<Integer>> matches = new HashMap<Peptide, ArrayList<Integer>>();

        Peptide targetPeptide = this;

        ArrayList<Peptide> peptideList = new ArrayList<>();

        int row = 0;

        for (Protein protein : inList)
        {
            row++;

            StringBuilder peptideBuilder = new StringBuilder();

            int currentIndex = 0;
            boolean match = false;

            while (currentIndex != protein.getLength())
            {
                peptideBuilder.append(protein.getProtein().charAt(currentIndex));
                Peptide currentPeptide = new Peptide(peptideBuilder.toString());

                match = isMatch(targetPeptide, currentPeptide);

                if (match)
                {
                    String LCS = currentPeptide.getLongestCommonSubsequence(targetPeptide, currentPeptide);

                    currentPeptide.colorCode(currentPeptide, LCS);

                    int score = currentPeptide.getScore(LCS);

                    if (score >= 2) {
                        int peptideNum = protein.getPeptideIndexInProtein(peptideList, currentPeptide);

                        ArrayList<Integer> proteinData = new ArrayList<Integer>();

                        proteinData.add(score);
                        proteinData.add(row);
                        proteinData.add(peptideNum);

                        matches.put(currentPeptide, proteinData);
                    }

                    peptideBuilder.setLength(0);
                }

                currentIndex++;
            }
        }

        return matches;
    }



    /**
     * The isMatch method determines whether the length of the largest common subsequence shared by the two incoming
     * Peptides is greater than two. "True" is returned if the LCS is > 2, and "false" is returned otherwise.
     *
     * @param targetPeptide    The Peptide the user is searching for
     * @param inPeptide    A Peptide found in a Protein contained in the data file
     * @return    "true" if the length of the LCS is > 2 and "false" if it is not
     */
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



    /**
     * This method find the longest common subsequence present in both incoming Peptides. Unlike substrings,
     * subsequences are not required to occupy consecutive positions within the original sequences.
     *
     * The main algorithm has been adapted from a program written by Pramod Kumar, whose original code can be found
     * at https://www.geeksforgeeks.org/printing-longest-common-subsequence/.
     *
     * @param targetPeptide    The Peptide the user is searching for
     * @param inPeptide    A Peptide found in a Protein contained in the data file
     * @return sb.toString()    The longest common subsequence shared by targetPeptide and inPeptide
     */
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



    /**
     * Finds the largest of two integers.
     *
     * @param a    An incoming integer
     * @param b    An incoming integer
     * @return    a if a > b and b if b > a
     */
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



    /**
     * This method finds the location of the LCS in the calling Peptide and sets the LCSBeginningIndex to the index of
     * the first character of the LCS within the Peptide.
     *
     * @param inLCS    The longest common subsequence of the calling Peptide and target Peptide
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



    /**
     * The colorCode method "colors" the incoming Peptide based on the target Peptide and their shared LCS. Characters
     * in the Peptide corresponding to the LCS will be colored green, and character(s) between the beginning and ending
     * LCS indices that are not part of the LCS will be colored red. All characters before and after the beginning and
     * ending LCS indices will be colored black.
     *
     * The amount of red and green characters present in the Peptide are counted and stored as the Peptide member
     * variables redCount and greenCount, respectively. These values will be used in determining  the similarity score
     * (@see score method).
     *
     * @param inPeptide    The incoming Peptide that is to be color coded
     * @param LCS    The LCS shared by inPeptide and the target Peptide
     * @return textFlow    The Peptide, returned as a color-coded TextFlow object
     */
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

        //textFlow.getChildren().remove(0, inPeptide.LCSBeginningIndex);
        //textFlow.getChildren().remove(indexOfLastGreen, inPeptide.length());


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



    /**
     * This method assigns a score to the similarity of the target Peptide and calling Peptide using their LCS. Every
     * matching character (green) is +2 points, and every non-matching (red) character is -1 points. Missing characters
     * (letters present in the target Peptide but not the LCS) are -1 point each.
     *
     *
     * @param inLCS    The longest common subsequence shared by the target Peptide and calling Peptide
     * @return score    An integer that represents the similarity between the calling and target Peptides
     */
    public int getScore(String inLCS)
    {
        int redCount = this.redCount;
        int greenCount = this.greenCount;
        int missing = inLCS.length() - greenCount;
        int score = 0;

        score = (2 * greenCount) - missing - redCount;

        return score;
    }



    /**
     * This method determines whether the calling Peptide and inPeptide have the same peptide String value.
     *
     * @param inPeptide   The Peptide that is to be compared with the calling Peptide
     * @return true if this.peptide and inPeptide.peptide are equal, and false otherwise
     */
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



    /**
     * This method overrides the toString method and returns the calling Peptide's peptide, a String value.
     *
     * @return this.peptide    The calling Peptide's peptide
     */
    @Override
    public String toString()
    {
        return this.peptide;
    }
}