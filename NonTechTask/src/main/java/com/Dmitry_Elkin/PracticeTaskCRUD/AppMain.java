package com.Dmitry_Elkin.PracticeTaskCRUD;

import com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController;

public class AppMain {
    public static  boolean terminate;

    public static void main(String[] args) {

        MainController cli = new MainController();
        while (!terminate){
            cli.upLevelMenu();
        }

    }
}
