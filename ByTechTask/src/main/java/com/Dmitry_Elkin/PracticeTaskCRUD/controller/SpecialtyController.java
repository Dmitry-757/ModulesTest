package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.GenericRepository;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.Service;
import com.Dmitry_Elkin.PracticeTaskCRUD.view.SpecialtyView;

import java.util.Scanner;

import static com.Dmitry_Elkin.PracticeTaskCRUD.view.Service.getGenericParamFromConsole;
import static com.Dmitry_Elkin.PracticeTaskCRUD.view.Service.getStringParamFromConsole;

public class SpecialtyController {

    //* Singleton realisation *
    private SpecialtyController() {}
    private static class SingletonHolder {
        private static final SpecialtyController INSTANCE = new SpecialtyController();
    }
    public static SpecialtyController getInstance(){return SpecialtyController.SingletonHolder.INSTANCE;}
    //********

    private final GenericRepository<Specialty, Long> repository = RepositoryFactory.getSpecialtyRepository();

    //************* menu ********************
    public void menu(Scanner sc) {
        boolean goBack = false;
        while (!goBack) {
                int choice = SpecialtyView.getActionChoice(sc);
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
        repository.addOrUpdate(new Specialty(name));
    }

    private void changeItem() {

        Specialty item = getGenericParamFromConsole("Specialty", repository);
        if (item != null) {
//            System.out.println("editing item = " + item.toString());
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
        Specialty item = getGenericParamFromConsole("Specialty", repository, Status.ACTIVE);
        if (item != null) {
//            System.out.println("deleting item is : " + item.toString());
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }
    }

    private void unDeleteItem() {
        Specialty item = getGenericParamFromConsole("Specialty", repository, Status.DELETED);
        if (item != null) {
//            System.out.println("UnDeleting item is : " + item.toString());
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }

    //*****************************************************
}
