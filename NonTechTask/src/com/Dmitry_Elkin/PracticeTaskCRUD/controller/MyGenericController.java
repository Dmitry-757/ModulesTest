package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.BaseModelsMethsI;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.GenericRepository;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController.sc;

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
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я\s]*");
        System.out.println("Input name of item");
        String name;
        String line = sc.nextLine();
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            name = matcher.group();
            System.out.println("name of item = " + name);

            T item = null;
            try {
                Class clazz = Class.forName(typeParameterClass.getName());
                Class[] myClassParams = {String.class};//not for Developer!!!!

                item = (T) clazz.getConstructor(myClassParams).newInstance(name);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException |
                     InvocationTargetException | NoSuchMethodException e) {
//                throw new RuntimeException(e);
                System.out.println("error during creating new instance of class : " + e.getMessage());
                return;
            }

            repository.addOrUpdate(item);
        } else {
            System.out.println("wrong input... Please, use only letters!");
        }
    }

    protected void changeItem() {
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я\s]*");
        printItems(Status.ACTIVE);// do not to disturb the dead
        System.out.println("Input id of changing item");
        if (sc.hasNextLong()) {
            long id = sc.nextLong();
            sc.nextLine();
            System.out.println("your choice = " + id);
            T item = repository.getById(id);
            if (item != null) {
                System.out.println("editing item = " + item.toString());

                System.out.println("Input new name of item");
                //sc.nextLine();
                String line = sc.nextLine();
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String newName = matcher.group();
                    item.setName(newName);
                    repository.addOrUpdate(item);
                    System.out.println("new name of item = " + newName);
                }
            } else
                System.out.println("item by id `" + id + "` is not found");
        } else {
            System.out.println("wrong input...");
        }
    }

    protected void printItems(Status status) {
        System.out.println("current items:");
        for (T item : repository.getAll(status)) {
            System.out.println(item.toString());
        }
    }


    protected void deleteItem() {
        System.out.println("Choose item from:");
        printItems(Status.ACTIVE);
        if (repository.getAll(Status.ACTIVE).size() == 0) {
            System.out.println("There is no Non-deleted items");
            return;
        }

        System.out.println("Input id of deleting item");
        if (sc.hasNextLong()) {
            long id = sc.nextLong();
            sc.nextLine();
            T item = repository.getById(id);
            if (item != null) {
                System.out.println("deleting item is : " + item.toString());
                repository.delete(item);
            } else
                System.out.println("item by id `" + id + "` was not found...");
        } else {
            System.out.println("wrong input...");
        }
    }

    protected void unDeleteItem() {
        System.out.println("Choose item from:");
        printItems(Status.DELETED);
        if (repository.getAll(Status.DELETED).size() == 0) {
            System.out.println("There is no deleted items");
            return;
        }

        System.out.println("Input id of UnDeleting item");
        if (sc.hasNextLong()) {
            long id = sc.nextLong();
            sc.nextLine();
            T item = repository.getById(id);
            if (item != null) {
                System.out.println("UnDeleting item is : " + item.toString());
                repository.unDelete(item);
            } else
                System.out.println("item by id `" + id + "` was not found...");
        } else {
            System.out.println("wrong input...");
        }
    }

    //*****************************************************
}
