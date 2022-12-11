package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.AppStarter;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.MainMenuView;

import java.util.Scanner;

public class MainController {

    private final SkillController skillController = SkillController.getInstance();
    private final SpecialtyController specialtyController =  SpecialtyController.getInstance();
    private final DeveloperController developerController = DeveloperController.getInstance();

    public void upLevelMenu() {
        Scanner sc = AppStarter.getScanner();
        int choice = -1;
        while (choice != 0) {
            choice = MainMenuView.getChoice(sc);
            switch (choice) {
                case 1 -> skillController.menu(sc);

                case 2 -> specialtyController.menu(sc);

                case 3 -> developerController.menu(sc);

                case 0 -> System.out.println("bye!");
//                case 0 -> System.exit(0);
                default -> System.out.println("Wrong input!");
            }
        }

    }

}
