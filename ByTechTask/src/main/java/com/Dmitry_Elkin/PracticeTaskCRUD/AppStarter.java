package com.Dmitry_Elkin.PracticeTaskCRUD;

import com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController;

import java.util.Scanner;


public class AppStarter {
    private static final Scanner sc = new Scanner(System.in);

    public static Scanner getScanner() {
        return sc;
    }


    public static void main(String[] args) {

        MainController mainController = new MainController();
        mainController.upLevelMenu();

    }
}
