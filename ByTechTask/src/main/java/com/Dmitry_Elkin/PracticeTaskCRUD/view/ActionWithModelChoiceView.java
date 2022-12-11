package com.Dmitry_Elkin.PracticeTaskCRUD.view;

import java.util.Scanner;

public class ActionWithModelChoiceView {
    public static int getActionChoice(Scanner sc) {

        System.out.println("1 - New item, 2 - change item, 3 - Delete item, 4 - UnDelete item, " +
                "5 - print all items, 6 - print Active items, 7 - print Deleted items, 0 - go back");
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
