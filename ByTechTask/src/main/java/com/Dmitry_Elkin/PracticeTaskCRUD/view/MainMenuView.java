package com.Dmitry_Elkin.PracticeTaskCRUD.view;

import java.util.Scanner;

public class MainMenuView {
    public static int getChoice(Scanner sc) {
        System.out.println("1 - work with Skills, " +
                " 2 - work with Specialties, " +
                " 3 - work with Developers," +
                " 0 - exit");
        if (sc.hasNextInt()) {
            int choice = sc.nextInt();
            sc.nextLine();
            return choice;
        } else {
            System.out.println("wrong input... Please, use only digits!");
            sc.nextLine();
        }
        return -1;
    }

}
