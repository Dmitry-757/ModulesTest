package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.GenericRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.Service;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.SkillView;

import java.util.Scanner;

import static com.Dmitry_Elkin.PracticeTaskCRUD.view.Service.getGenericParamFromConsole;
import static com.Dmitry_Elkin.PracticeTaskCRUD.view.Service.getStringParamFromConsole;


public class SkillController {


    //* Singleton realisation *
    private SkillController() {
    }

    private static class SingletonHolder {
        private static final SkillController INSTANCE = new SkillController();
    }

    public static SkillController getInstance() {
        return SingletonHolder.INSTANCE;
    }
    //********

    //использую фабрику для предотвращения создания множества экземпляров SkillRepositoryImpl
    private final GenericRepository<Skill, Long> repository = RepositoryFactory.getSkillRepository();

    //************* menu ********************
    public void menu(Scanner sc) {
        boolean goBack = false;
        while (!goBack) {

            int choice = SkillView.getActionChoice(sc);
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
        String name = getStringParamFromConsole("first name");
        repository.addOrUpdate(new Skill(name));
    }

    private void changeItem() {

        Skill item = getGenericParamFromConsole("Skill", repository);
        if (item != null) {
            System.out.println("editing item = " + item);
            String newName = getStringParamFromConsole("name");
            item.setName(newName);
            repository.addOrUpdate(item);
        }

    }

    private void printItems(Status status) {
        Service.printItems(status, repository);
    }


    private void deleteItem() {
        Skill item = getGenericParamFromConsole("Skill", repository, Status.ACTIVE);
        if (item != null) {
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }

    }

    private void unDeleteItem() {
        Skill item = getGenericParamFromConsole("Skill", repository, Status.DELETED);
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }

    //*****************************************************
}
