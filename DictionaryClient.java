/**A client program that allows the user to enter a data file, a value for n (in part 3 above), and a desired 
 * number of words to generate. Your program should then generate and display that number of words.
 * instructor: Ernest McCracken
 * PA1 Binary Search Tree Dictionary 
 * @version (3/24/2014) 
 */

import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.*;

public class DictionaryClient {

    public static void main(String[] args) {
        int words=0;
        int num=0;
        BSTDictionary<Integer, Double> dictWordLengths;
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nType the Exact data file below of your choice:\nenglish.txt\nlatin.txt\n");
            System.out.println();
            String textFile = scanner.nextLine();

           CreatDictWords  wordGenerator = new CreatDictWords (textFile, 10);
         


            while (true)  {
                try
                {

                    System.out.print("\nEnter the number of letters for n (0 to quit): ");
                    num = scanner.nextInt();

                } catch (InputMismatchException ex) {
                    System.out.println("Error Mis-Match input by the user");
                    return;
                }

                if (num==0)
                {
                    System.exit(0);
                    return;
                }
                else if(num<=0)
                {

                    System.out.println("\nError: try again!:\nEnter the number of letters for n ");
                    num = scanner.nextInt(); 
                }
                try
                {

                    System.out.print("\n How many words do you want to print: ");
                    words = scanner.nextInt(); 
                } catch (InputMismatchException ex) {
                    System.out.println("Error Mis-Match input by the user");
                    return;
                }

                while(words < 0)
                {

                    System.out.print("\n Error, Try Again. How many words do you want to print: ");
                    words = scanner.nextInt(); 

                }

                for (int i=0; i<words; i++) {
                    System.out.println(wordGenerator.generateRandomWord(3));
                }
            }//end while

        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }

    }

}
