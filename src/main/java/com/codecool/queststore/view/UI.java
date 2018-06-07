package com.codecool.queststore.view;

import java.io.Console;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private Scanner scanner = new Scanner(System.in);
    private Console cnsl;

    public String getInputString(String message) {
        displayLine(message);
        String input;
        do {
            input = scanner.nextLine();
        } while (input.isEmpty());
        return input;
    }

    public int getInputInt(String message) {
        System.out.print(message);
        boolean isCorrectValue = false;
        int inputInt = 0;
        while (!isCorrectValue) {
            try {
                scanner = new Scanner(System.in);
                inputInt = scanner.nextInt();
                isCorrectValue = true;
            } catch (InputMismatchException e) {
                System.out.println("Wrong value!");
            }
        }

        return inputInt;
    }

    public String askUserPassword() {
        cnsl = System.console();
        String password;
        char[] asterix;

        if(cnsl !=  null) {
            char[] passwordArray = cnsl.readPassword("Provide password: ");
            asterix = new char[passwordArray.length];
            Arrays.fill(asterix, '*');
            displayLine(String.valueOf(asterix));
            password = new String(passwordArray);

        } else {
            password = getInputString("Provide password: ");
        }
        return password;

    }

    public void displayLine(String lineContent) {
        System.out.println(lineContent);
    }

    public void displayArray(String[] array) {
        for (String line : array) {
            System.out.println(line);
        }
    }

    public void clearScreen() {
        displayLine("\033[H\033[2J");
    }

}
