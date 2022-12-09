package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.*;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.GenericRepository;
import java.lang.reflect.InvocationTargetException;

import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController.sc;
import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.*;

public class MyGenericController<T extends BaseModelsMethsI> {

    final Class<T> typeParameterClass;
    protected final GenericRepository<T, Long> repository;

    public MyGenericController(Class<T> typeParameterClass, GenericRepository<T, Long> repository) {
        this.repository = repository;
        this.typeParameterClass = typeParameterClass;
    }

    //************* menu ********************
    public void menu() {
        boolean goBack = false;
        while (!goBack) {
            System.out.println("1 - New item, 2 - change item, 3 - Delete item, 4 - UnDelete item, " +
                    "5 - print all items, 6 - print Active items, 7 - print Deleted items, 0 - go back");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
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
            } else {
                System.out.println("wrong input... Please, use only digits!");
                sc.nextLine();
            }

        }
    }

    protected void createNewItem() {

        String name = getStringParamFromConsole("name");

        T item = null;
        //*** ONLY FOR MODELS WITH CONSTRUCTOR WITH ONE STRING PARAMETER!!! ***
        try {
            Class clazz = Class.forName(typeParameterClass.getName());
            Class[] myClassParams = {String.class};//ONLY FOR MODELS WITH CONSTRUCTOR WITH ONE STRING PARAMETER!!!

            item = (T) clazz.getConstructor(myClassParams).newInstance(name);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                 InvocationTargetException | NoSuchMethodException e) {
            System.out.println("error during creating new instance of class : " + e.getMessage());
            return;
        }

        repository.addOrUpdate(item);
    }

    protected void changeItem() {
        T item = getGenericParamFromConsole("Item", repository);
        if (item != null) {
            System.out.println("editing item = " + item);

            String newName = getStringParamFromConsole("first name");
            item.setName(newName);

            repository.addOrUpdate(item);
            System.out.println("After edit item is : "+item);
        }

    }

    protected void printItems(Status status) {
        System.out.println("current items:");
        for (T item : repository.getAll(status)) {
            System.out.println(item.toString());
        }
    }


    protected void deleteItem() {

        T item = getGenericParamFromConsole("Item", repository, Status.ACTIVE);
        if (item != null) {
            System.out.println("deleting item is : " + item);
            repository.delete(item);
        }
    }

    protected void unDeleteItem() {

        T item = getGenericParamFromConsole("Item", repository, Status.DELETED);
        if (item != null) {
            System.out.println("UnDeleting item is : " + item);
            repository.unDelete(item);
        }
    }

    //*****************************************************
}
