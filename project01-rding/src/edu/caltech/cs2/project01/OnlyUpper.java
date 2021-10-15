package edu.caltech.cs2.project01;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Write a program to remove all non-upper-case characters (including spaces and newlines) from cryptogram.txt,
// run SubstitutionCipherSolver on the result, and put the final decrypted cryptogram in plaintext.txt.
//
public class OnlyUpper {
    public static void main(String[] args) throws FileNotFoundException {
        char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
                'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        File file = new File("cryptogram.txt");
        Scanner in = new Scanner(file);
        String output = "";
        while (in.hasNextLine()) {
            String line = in.nextLine();
            for (int i = 0; i < line.length(); i++) {
                for (char letter : alphabet) {
                    if (line.charAt(i) == letter) {
                        output += letter;
                    }
                }

            }
        }
        System.out.println(output);
    }
}
