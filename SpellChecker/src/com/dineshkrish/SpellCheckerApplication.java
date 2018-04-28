package com.dineshkrish;

import java.util.Scanner;

public class SpellCheckerApplication {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // You can change your file path accordingly.
        MySpellChecker spellChecker = new MySpellChecker("dictionary/my_dictionary.txt");

        System.out.println("Enter the Sentence with Spelling Mistake..");

        // Reading Input from User
        String line = scanner.nextLine();

        System.out.println("Before Correction : "+line);

        // Method Invocation for Spelling Correction
        line = spellChecker.doCorrection(line);

        System.out.println("After Correction : "+line);

        scanner.close();

    }

}