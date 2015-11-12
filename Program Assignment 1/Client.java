/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Daniel
 */
public class Client {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        BSTDictionary<Integer, Double> dictWordLengths;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEnter a data file: ");
            String na = scanner.nextLine();
            //                 System.out.print("\nEnter a number of letters: ");
            //             int  num = scanner.nextInt();
            //WordGenerator wordGenerator = createWordGenerator("na", 10);

            WordGenerator wordGenerator = new WordGenerator("english.txt", 10);
            //             System.out.print("\nEnter a number of letters: ");
            //             int  num = scanner.nextInt();

            //Scanner scanner = new Scanner(System.in);
            while (true)  {
                System.out.print("\nEnter a value for n (0 to quit): ");
                int n = scanner.nextInt();
                if (n == 0)
                    return;
                System.out.print("\n How many words do you want to print: ");
                int a = scanner.nextInt();

                for (int i=0; i<a; i++) {
                    System.out.println(wordGenerator.generateRandomWord(3));
                }
            }//end while

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

        //testDictionary();

    }

    //     public static void testWordFrequencies() throws FileNotFoundException {
    //         BSTDictionary<Integer, Double> dictWordLengths;
    //         dictWordLengths = WordGenerator.createDictWordLengths("Dictionary.txt");
    //         
    //         Iterator iterator = dictWordLengths.iterator();
    //         
    //         while (iterator.hasNext()) {
    //             DictEntry entry = (DictEntry) iterator.next();
    //             System.out.println(entry.key + " - " + entry.value);
    //         }
    //         
    //         //System.out.println(dictWordLengths.toString());
    //     }

    //     public static void testLetterFrequencies() throws FileNotFoundException {
    //         BSTDictionary<String, BSTDictionary<String, Double>> dict;
    //         dict = WordGenerator.createDictLetterFrequencies("english.txt", 3);
    // 
    //         //Iterator iterator = dict.iterator();
    // 
    //         //System.out.println(dict.toString());
    // 
    //         System.out.println(dict.getValue("aar"));
    //     }

    //     public static void testGenerateKey() throws FileNotFoundException {
    //         BSTDictionary<String, BSTDictionary<String, Double>> dict;
    //         dict = WordGenerator.createDictLetterFrequencies("english.txt", 3);
    // 
    //         BSTDictionary smallDict = dict.getValue("aar");
    // 
    //         int[] frequencies = new int[3];
    //         for (int i=0; i<3; i++)
    //             frequencies[i] = 0;
    // 
    //         System.out.println(smallDict.toString());
    // 
    //         System.out.println("Generating random keys...");
    // 
    //         for (int i=0; i<10000; i++) {
    //             String key = (String)WordGenerator.generateKey(smallDict);
    // 
    //             if (key.equals("d"))
    //                 frequencies[0]++;
    //             else if (key.equals("s"))
    //                 frequencies[1]++;
    //             else if (key.equals("t"))
    //                 frequencies[2]++;
    //         }//end for
    // 
    //         System.out.println("d - " + frequencies[0]);
    //         System.out.println("s - " + frequencies[1]);
    //         System.out.println("t - " + frequencies[2]);
    //     }
    //     
    //     public static void testDictionary() {
    //         BSTDictionary<Integer, String> dict = new BSTDictionary<Integer, String>();
    //         
    //         dict.add(3, "Sloth");
    //         dict.add(1, "Yes");
    //         dict.add(2, "No");
    //         dict.add(4, "Banana");
    //         dict.add(5, "WHOO");
    //         
    //         System.out.println(dict.toString());
    //         
    //         Iterator<DictEntry<Integer, String>> iterator = dict.iterator();
    //         
    //         while (iterator.hasNext()) {
    //             System.out.println(iterator.next().value);
    //         }//end while
    //         
    //         System.out.println(dict.contains(3));
    //         System.out.println(dict.contains(6));
    //         
    //         System.out.println(dict.getValue(2));
    //         System.out.println(dict.getValue(7));
    //     }
}
