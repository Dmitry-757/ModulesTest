package com.Dmitry_Elkin.PracticeTaskCRUD.view;

import com.Dmitry_Elkin.PracticeTaskCRUD.AppStarter;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.GenericRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Service {

    private static final Scanner sc = AppStarter.getScanner();

    //получение строкового параметра из консоли
    public static String getStringParamFromConsole(String parameterName) {
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я\s-_#]*");
        System.out.println("Input " + parameterName);
        String strParam;
        while (true) {
            String line = sc.nextLine();
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                strParam = matcher.group();
                System.out.println(parameterName + " is: " + strParam);
                return strParam;
            } else {
                System.out.println("wrong input... Please, try again!");
            }
        }
    }

    //получение с помощью консоли экземпляра модели хранящегося в указанном репозитории
    public static  <T> T getGenericParamFromConsole(String parameterName, GenericRepository<T, Long> repository, Status status) {
        if (repository.getAll(status).size() == 0){
            System.out.println("There is no items for choice!");
            return null;
        }
        System.out.println("Input " + parameterName);
        while (true) {
            System.out.println("\ncurrent items:");
            for (T item : repository.getAll(status)) {
                System.out.println(item.toString());
            }
            System.out.println("Input id of chosen item, or type 0 for end of choosing");
            if (sc.hasNextLong()) {
                long id = sc.nextLong();
                if (id == 0) break;

                sc.nextLine();
                T item = repository.getById(id);
                if (item != null) {
//                    System.out.println("choosing item is : " + item.toString());
                    System.out.println("choosing item is : " + item);
                    return item;
                } else
                    System.out.println("item by id `" + id + "` was not found...");
            } else {
                System.out.println("wrong input...");
            }
        }
        return null;
    }
    //получение с помощью консоли экземпляра модели хранящегося в указанном репозитории только со статусом ACTIVE
    public static  <T> T getGenericParamFromConsole(String parameterName, GenericRepository<T, Long> repository) {
        return getGenericParamFromConsole(parameterName, repository, Status.ACTIVE);
    }


    //получение с помощью консоли нескольких экземпляров модели хранящихся в указанном репозитории
    public static <T> List<T> getGenericListFromConsole(String parameterName, GenericRepository<T, Long> repository) {

        List<T> result = new ArrayList<>();
        System.out.println("Please, choose " + parameterName);

        if (repository.getAll(Status.ACTIVE).size() == 0){
            System.out.println("There is no Non-deleted items!");
            return result;
        }

        boolean goBack = false;
        T item;
        while (!goBack) {
            System.out.println("1 - Add item, 0 - go back");
            if (sc.hasNextInt()) {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1 -> {
                        item = getGenericParamFromConsole("parameterName", repository);
                        if (item != null) {
                            result.add(item);
                        }
                    }
                    case 0 -> goBack = true;
                    default -> System.out.println("Wrong input!");
                }
            } else {
                System.out.println("wrong input... Please, use only digits!");
                sc.nextLine();
            }
        }
        return result;
    }


    //вывод в консоль списка экземпляров модели из указанного репозитория
    public static <T> void printItems(Status status, GenericRepository<T, Long> repository) {
        System.out.println("current items : ");
        for (T item : repository.getAll(status)) {
            System.out.println(item.toString());
        }
    }


}
