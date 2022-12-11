package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.GenericRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.DeveloperView;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.Service;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


import static com.Dmitry_Elkin.PracticeTaskCRUD.view.Service.*;

public class DeveloperController {


    //* Singleton realisation *
    private DeveloperController() {}
    private static class SingletonHolder {
        private static final DeveloperController INSTANCE = new DeveloperController();
    }
    public static DeveloperController getInstance(){return DeveloperController.SingletonHolder.INSTANCE;}

    private final GenericRepository<Developer, Long> repository = RepositoryFactory.getDeveloperRepository();

    //************* menu ********************
    public void menu(Scanner sc) {
        boolean goBack = false;
        while (!goBack) {
                int choice = DeveloperView.getActionChoice(sc);
                switch (choice) {
                    case 1 -> createNewItem();
                    case 2 -> changeItem();
                    case 3 -> deleteItem();
                    case 4 -> unDeleteItem();
                    case 5 -> printItems(null);
                    case 6 -> printItems(Status.ACTIVE);
                    case 7 -> printItems(Status.DELETED);
                    case 0 -> goBack = true;
                    default -> System.out.println("Wrong input!");
                }
        }
    }

    private void createNewItem() {

        String firstName = getStringParamFromConsole("first name");
        String lastName = getStringParamFromConsole("second name");
        HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skill", RepositoryFactory.getSkillRepository()));
        Specialty specialty = getGenericParamFromConsole("Specialty", RepositoryFactory.getSpecialtyRepository());
        repository.addOrUpdate(new Developer(firstName, lastName, skills, specialty));
    }



    private void changeItem() {
        Developer item = getGenericParamFromConsole("Developer", repository);
        if (item != null) {
            System.out.println("editing item = " + item);
            String firstName = getStringParamFromConsole("first name");
            String lastName = getStringParamFromConsole("second name");
            Set<Skill> skills = new HashSet<>(getGenericListFromConsole("Skills", RepositoryFactory.getSkillRepository()));
            Specialty specialty = getGenericParamFromConsole("Specialty", RepositoryFactory.getSpecialtyRepository());
            item.setFirstName(firstName);
            item.setLastName(lastName);
            if (skills.size() != 0) {
                item.setSkills(skills);
            }
            if (specialty != null) {
                item.setSpecialty(specialty);
            }
            repository.addOrUpdate(item);
        }

    }

    public void printItems(Status status) {
        Service.printItems(status, repository);
    }


    private void deleteItem() {
        Developer item = getGenericParamFromConsole("Developer", repository, Status.ACTIVE);
        if (item != null) {
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }

    }

    private void unDeleteItem() {
        Developer item = getGenericParamFromConsole("Developer", repository, Status.DELETED);
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }

    //*****************************************************
}
