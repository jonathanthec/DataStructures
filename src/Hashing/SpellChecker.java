package Hashing;


import java.util.ArrayList;
import java.io.*;

public class SpellChecker {
    private HashTable dictionary;
    private char[] alphabet;
    private ArrayList<String> suggestedWords;
    private BufferedReader dicFile;

    public SpellChecker() {
        dictionary = new HashTable(1);
        alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        suggestedWords = new ArrayList<String>();
    }

    public void readDictionary(Reader dictFileReader) {
        dicFile = new BufferedReader(dictFileReader);
        String line;
        try {
            while ((line = dicFile.readLine()) != null) {
                dictionary.insert(line);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    public String[] checkWord(String word) {
        if (dictionary.contains(word)) return null;
        WordChecker1(word);
        WordChecker2(word);
        WordChecker3(word);
        WordChecker4(word);
        WordChecker5(word);
        String[] output = suggestedWords.toArray(new String[suggestedWords.size()]);
        suggestedWords = new ArrayList<String>();
        return output;
    }

    // Check if a letter is misspelled (i.e. love >>> lyve)
    private void WordChecker1(String word) {
        String suggestedCheck;
        for(int i=0; i<word.length(); i++) {
            for(char c:alphabet){
                suggestedCheck = word.substring(0,i)+c+word.substring(i+1);
                if (hasSuggestion(suggestedCheck)) {
                    suggestedWords.add(suggestedCheck);
                }
            }
        }
    }

    // Check if a letter has been accidentally inserted (i.e. love >>> lotve)
    private void WordChecker2(String word) {
        String suggestedCheck;
        for (int i=0; i<word.length()-1; i++) {
            suggestedCheck = word.substring(0,i) + word.substring(i+1);
            if (hasSuggestion(suggestedCheck)) {
                suggestedWords.add(suggestedCheck);
            }
        }
    }

    // Check if you accidentally missed a letter (i.e. love >>> lov)
    private void WordChecker3(String word) {
        String suggestedCheck;
        for(int i=0; i<word.length()+1; i++) {
            for(char c:alphabet) {
                suggestedCheck = word.substring(0,i) + c + word.substring(i);
                if (hasSuggestion(suggestedCheck)) {
                    suggestedWords.add(suggestedCheck);
                }
            }
        }
    }

    // Check for transposed pair (i.e. love >>> loev)
    private void WordChecker4(String word) {
        String suggestedCheck;
        for(int i=0; i<word.length()-1; i++) {
            suggestedCheck = word.substring(0,i) + word.substring(i+1,i+2) + word.substring(i,i+1) + word.substring(i+2);
            if (hasSuggestion(suggestedCheck)) {
                suggestedWords.add(suggestedCheck);
            }
        }
    }

    // Accidental space insertion
    private void WordChecker5(String word) {
        String suggestedCheck1, suggestedCheck2;
        for(int i=1; i<word.length(); i++) {
            suggestedCheck1 = word.substring(0,i);
            suggestedCheck2 = word.substring(i);
            if (dictionary.contains(suggestedCheck1)&&dictionary.contains(suggestedCheck2)) {
                suggestedWords.add(suggestedCheck1+" "+suggestedCheck2);
            }
        }
    }

    private boolean hasSuggestion(String suggestion) {
        return dictionary.contains(suggestion)&&(!suggestedWords.contains(suggestion));
    }

}