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

import java.util.Iterator;
import java.util.LinkedList;

public class BSTDictionary<K extends Comparable<K>, V> {

    public static class BSTNode<K, V> {
        public K key;
        public V value;
        public BSTNode<K, V> left, right;

        public BSTNode(K key, V value, BSTNode<K, V> left, BSTNode<K, V> right) {
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public String toString() {
            return key + " - " + value;
        }

    }// end class BSTNode<K, V>

    public static class DictEntry<K, V> {
        public K key;
        public V value;

        public DictEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public BSTNode<K, V> root;

    /**
     * Inserts the specified key-value pair into the dictionary. 
     * If the key already exists, this method should replace that key’s value 
     * with the new one.
     * @param key
     * @param value 
     */
    public void add(K key, V value) {
        //special case of adding to the root
        if (root == null)
            root = new BSTNode<K, V>(key, value, null, null);
        else add(key, value, root);
    }

    /*
     * helper method that recursively adds the key and value to a tree rooted
     * at where
     */
    public void add(K key, V value, BSTNode<K, V> where) {
        int compare = key.compareTo(where.key);

        if (compare == 0)           //key already exists
            where.value = value;    //so replace the old value with the new
        else if (compare<0 && where.left==null)     //give where a new left child
            where.left = new BSTNode<K, V>(key, value, null, null);
        else if (compare>0 && where.right==null)    //give where a new right child
            where.right = new BSTNode<K, V>(key, value, null, null);
        else if (compare<0)         //if where has a left child, recursively add to that child
            add(key, value, where.left);
        else if (compare>0)         //if where has a right child, recursively add to that child
            add(key, value, where.right);
    }// end add

    /**
     * 
     * @param key
     * @return  the value associated with the specified <code>key</code>,
     *          or <code>null</code> if the key does not exist.
     */
    public V getValue(K key) {
        return getValue(key, root);
    }

    /*
     *  Recursively searches the tree for the specified key, and returns the
     *  value associated with that key
     */
    public V getValue(K key, BSTNode<K, V> where) {
        if (where == null) {        //empty tree - nothing to search!
            return null;
        } 
        else {
            int compare = key.compareTo(where.key);

            if (compare == 0)       //key found!
                return where.value;
            else if (compare < 0)   //recursively search where's left subtree
                return getValue(key, where.left);
            else                    //recursively search where's right subtree
                return getValue(key, where.right);
        }// end if-else
    }//end getValue

    /**
     * 
     * @param key
     * @return  <code>true</code> if the specified key exists in the dictionary,
     *          or <code>false</code> if it does not.
     */
    public boolean contains(K key) {
        if (getValue(key) == null)
            return false;
        else
            return true;
    }

    //ADD iterator() METHOD THAT RETURNS AN ITERATOR OVER ELEMENTS IN DICTIONARY
    public Iterator<DictEntry<K, V>> iterator() {
        LinkedList<DictEntry<K, V>> list = new LinkedList<DictEntry<K, V>>();

        inOrderTraverse(list, root);

        return list.iterator();
    }

    public void/*LinkedList<BSTNode<K, V>>*/ inOrderTraverse(LinkedList<DictEntry<K, V>> list, BSTNode<K, V> where) {
        if (where != null) {
            inOrderTraverse(list, where.left);

            list.add(new DictEntry<K, V>(where.key, where.value));

            inOrderTraverse(list, where.right);
        }   
    }// end inOrderTraverse

    // Wrapper method for toString
    @Override
    public String toString() {
        return toString(root, "");
    }

    //Recursive implementation of toString. The indent parameter keeps track of
    //how many spaces to display before each line. Each recursive call of
    //toString lengthens this parameter by one.
    public String toString(BSTNode<K, V> where, String indent) {
        if (where == null)
            return indent + "null";
        else {
            String s = indent + where.key + " - " + where.value + "\n";
            s += toString(where.left, indent + " ") + "\n";
            s += toString(where.right, indent + " ");
            return s;
        }
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

            //if (i<n)
            //dictLetterFrequencies = createDictLetterFrequencies(fileName, i+1);

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

    public static BSTDictionary createDictWordLengths(String fileName)throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));

        BSTDictionary<Integer, Double> dictWordLengths = new BSTDictionary<Integer, Double>();

        int numOfWords = 0;
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) 
        {

            //counts the number of words in the text file
            while (scanner.hasNextLine()) {
                numOfWords++;
                scanner.nextLine();
            }//end while

            scanner = new Scanner(new File(fileName));
            while(scanner.hasNextLine()) {
                String word = scanner.nextLine();
                int length = word.length();
                //double frequency = 1 / (double)numOfWords;
                double frequency = 1;

                if (dictWordLengths.contains(length))
                    frequency += dictWordLengths.getValue(length);

                dictWordLengths.add(length, frequency);
                //System.out.println(length + " - " + frequency);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
        return reduceToFrequencies(dictWordLengths);
    }

    public static BSTDictionary createDictLetterFrequencies(String fileName, int n) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        //String s;

        BSTDictionary<String, BSTDictionary<String, Double>> dict;
        dict = new BSTDictionary<>();
        try(BufferedReader br = new BufferedReader(new FileReader(fileName))) 
        {
            //             while(  scanner= br.readLine()!=null)
            //             {
            while (scanner.hasNextLine()) {
                String s = scanner.nextLine();

                for (int i=0; i<s.length()-n; i++) {
                    String outerKey = s.substring(i, i+n);

                    dict.add(outerKey, new BSTDictionary<String, Double>());
                }// end for
            }

            scanner = new Scanner(new File(fileName));
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

            Iterator<DictEntry<String, BSTDictionary<String, Double>>> iterator;
            iterator = dict.iterator();

            while (iterator.hasNext()) {
                DictEntry entry = iterator.next();
                BSTDictionary innerDict = (BSTDictionary<String, Double>)entry.value;
                dict.add((String)entry.key, reduceToFrequencies(innerDict));
            }

            //  } 
        }catch (IOException e){
            e.printStackTrace();
        }
        return dict;
    }

    public static Object generateKey(BSTDictionary d) {
        Iterator<DictEntry> iterator = d.iterator();

        double rand = Math.random();

        while (iterator.hasNext()) {
            DictEntry entry = iterator.next();

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
        Iterator<DictEntry<Object, Double>> iterator;
        iterator = dict.iterator();

        double total = 0;

        while (iterator.hasNext()) {
            total += iterator.next().value;
        }

        iterator = dict.iterator();

        while (iterator.hasNext()) {
            DictEntry<Object, Double> entry = iterator.next();
            Comparable key = (Comparable) entry.key;
            double value = entry.value / total;

            dict.add(key, value);
        }

        return dict;
    }

    
}
