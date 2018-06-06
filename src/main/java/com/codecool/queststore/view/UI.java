package com.codecool.queststore.view;

import java.io.Console;
import java.util.Arrays;
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
        displayLine(message);

        return scanner.nextInt();
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

    public void clearScreen() {
        displayLine("\033[H\033[2J");
    }

}
