package com.codecool.queststore.view;

import com.codecool.queststore.details.*;
import com.codecool.queststore.model.Mentor;
import com.codecool.queststore.model.Student;

import java.io.Console;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class UI {
    private Scanner scanner = new Scanner(System.in);
    private Console cnsl;

    public void displayMenu(List<Privilege> privileges) {
        clearScreen();
        displayLine("What would you like to do:");
        for (int i = 0; i < privileges.size(); i++) {
            System.out.printf("\t(%d) %s\n", i, privileges.get(i).toString());
        }
    }

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

    public void displayStudents(List<Student> studentList) {
        displayLine("/----------------------------------------------------\\");
        displayLine(String.format("|%-25s| %-25s|", "NAME", "SURNAME"));
        displayLine("|----------------------------------------------------|");
        for (Student s : studentList) {
            this.displayLine(String.format("|%-25s| %-25s|", s.getName(), s.getSurName()));
        }
        this.displayLine("\\----------------------------------------------------/");
    }

    public void displayMentors(List<Mentor> mentorList) {
        displayLine("/----------------------------------------------------\\");
        displayLine(String.format("|%-25s| %-25s|", "NAME", "SURNAME"));
        displayLine("|----------------------------------------------------|");
        for (Mentor m : mentorList) {
            this.displayLine(String.format("|%-25s| %-25s|", m.getName(), m.getSurName()));
        }
        this.displayLine("\\----------------------------------------------------/");
    }
}
