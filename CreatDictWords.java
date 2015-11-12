/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class CreatDictWords {

    public BSTDictionary<Integer, Double> lengthOfWords;
    public BSTDictionary<String, BSTDictionary<String, Double>>[] dictLetterFrequencies;

    public String filename;
    public int n;

    public  CreatDictWords(String filename, int n) throws FileNotFoundException {
        this.filename = filename;
        this.n = n;
        initialize();
    }

    public  void initialize() throws FileNotFoundException {
        lengthOfWords =createDictWordLengths(filename);
        dictLetterFrequencies = new BSTDictionary[n];

        for (int i=0; i<n; i++)
            dictLetterFrequencies[i] = createDictLetterFrequencies(filename, i+1);
    }

    public static String generateRandomWord(String fileName, int n) throws FileNotFoundException {
        BSTDictionary<Integer, Double> dictWordLengths;
        dictWordLengths = createDictWordLengths(fileName);

        BSTDictionary<String, BSTDictionary<String, Double>>[] dictLetterFrequencies;
        dictLetterFrequencies = new BSTDictionary[n];

        for (int i=0; i<n; i++) {
            dictLetterFrequencies[i] = createDictLetterFrequencies(fileName, i+1);
        }

        int length = (int)generateKey(dictWordLengths);

        String randomWord = generateRandomLetter();
        for (int i=0; i<length-1; i++) {
            String substring;
            if (i<n)
                substring = randomWord.substring(0, i+1);
            else 
                substring = randomWord.substring(i-n+1, i+1);

            String nextLetter = (String)generateKey(dictLetterFrequencies[substring.length()-1].getValue(substring));
            randomWord = randomWord + nextLetter;
        }

        return randomWord;
    }

    public static String generateRandomLetter() {
        char c = 'a';
        int rand = (int)(Math.random()*26);

        c += (char)rand;

        return String.valueOf(c);
    }
     
    public String generateRandomWord(int n) throws FileNotFoundException {
        if (n > this.n) {
            this.n = n;
            initialize();
        }

        int length = (int)generateKey(lengthOfWords);

        String randomWord = randomLetters();

        for (int i=0; i<length-1; i++) {
            String substring;
            if (i<n)
                substring = randomWord.substring(0, i+1);
            else
                substring = randomWord.substring(i-n+1, i+1);

            String nextLetter = (String)generateKey(dictLetterFrequencies[substring.length()-1].getValue(substring));

            randomWord += nextLetter;
        }// end for

        return randomWord;
    }

    public static String randomLetters() {
        char c = 'a';
        int rand = (int)(Math.random()*26);

        c += (char)rand;

        return String.valueOf(c);
    }

    public static BSTDictionary createDictWordLengths(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        BSTDictionary<Integer, Double> lengthOfWords = new BSTDictionary<Integer, Double>();

        int numOfWords = 0;
        //counts the number of words in the text file
        while (scanner.hasNextLine()) {
            numOfWords++;
            scanner.nextLine();
        }//end while

        scanner = new Scanner(new File(filename));

        while(scanner.hasNextLine()) {
            String word = scanner.nextLine();
            int length = word.length();
            //double frequency = 1 / (double)numOfWords;
            double frequency = 1;

            if (lengthOfWords.contains(length))
                frequency += lengthOfWords.getValue(length);

            lengthOfWords.add(length, frequency);
            //System.out.println(length + " - " + frequency);
        }

        return reduceToFrequencies(lengthOfWords);
    }

    public static  BSTDictionary createDictLetterFrequencies(String filename, int n) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));

        BSTDictionary<String, BSTDictionary<String, Double>> dict;
        dict = new BSTDictionary<>();

        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();

            for (int i=0; i<s.length()-n; i++) {
                String outerKey = s.substring(i, i+n);

                dict.add(outerKey, new BSTDictionary<String, Double>());
            }// end for
        }

        scanner = new Scanner(new File(filename));
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();

            for (int i=0; i<s.length()-n; i++) {
                String outerKey = s.substring(i, i+n);
                String innerKey = s.substring(i+n, i+n+1);

                //dict.add(outerKey, new BSTDictionary<String, Double>());

                double numOccurrences = 0;
                if (dict.getValue(outerKey).contains(innerKey) == false)
                    numOccurrences = 1;
                else 
                    numOccurrences = dict.getValue(outerKey).getValue(innerKey) + 1;

                dict.getValue(outerKey).add(innerKey, numOccurrences);
            }// end for
        }// end while

        Iterator<BSTDictionary.Node<String, BSTDictionary<String, Double>>> iterator;
        iterator = dict.iterator();

        while (iterator.hasNext()) {
            BSTDictionary.Node entry = iterator.next();
            BSTDictionary innerDict = (BSTDictionary<String, Double>)entry.value;
            dict.add((String)entry.key, reduceToFrequencies(innerDict));
        }

        return dict;
    }

 
    public static Object generateKey(BSTDictionary d) {
        if (d == null)
            return randomLetters();

        Iterator<BSTDictionary.Node> iterator = d.iterator();

        double rand = Math.random();

        while (iterator.hasNext()) {
            BSTDictionary.Node entry = iterator.next();

            if (rand < (double)entry.value)
                return entry.key;
            else
                rand -= (double)entry.value;
        }//end while

        return null;
    }

    /*
     * Takes a Dictionary where the values are only the number of occurences of
     * the keys, and reduces the values to percentage frequencies.
     */
    public static BSTDictionary reduceToFrequencies(BSTDictionary dict) {
        Iterator<BSTDictionary.Node<Object, Double>> iterator;
        iterator = dict.iterator();

        double total = 0;

        while (iterator.hasNext()) {
            total += iterator.next().value;
        }

        iterator = dict.iterator();

        while (iterator.hasNext()) {
            BSTDictionary.Node<Object, Double> entry = iterator.next();
            Comparable key = (Comparable) entry.key;
            double value = entry.value / total;

            dict.add(key, value);
        }

        return dict;
    }

    
    
    
    
    
    

}
