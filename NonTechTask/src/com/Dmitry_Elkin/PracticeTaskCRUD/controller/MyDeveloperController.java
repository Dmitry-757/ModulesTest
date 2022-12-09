package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.MainController.sc;

public class MyDeveloperController extends MyGenericController<Developer> {
    public MyDeveloperController() {
        super(Developer.class, RepositoryFactory.getDeveloperRepository());
    }

    @Override
    protected void createNewItem() {
//        super.createNewItem();
        Pattern pattern = Pattern.compile("^[a-zA-Zа-яА-Я\s]*");
        System.out.println("Input name of item");
        String name;
        String line = sc.nextLine();
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            name = matcher.group();
            System.out.println("name of item = " + name);

            Specialty specialty = new Specialty("java developer");
            Developer item = new Developer("Vasja", "Pupkin", specialty);

            super.repository.addOrUpdate(item);
        } else {
            System.out.println("wrong input... Please, use only letters!");
        }
    }

    @Override
    protected void printItems(Status status) {
        System.out.println("current items:");
        for (Developer item : repository.getAll(status)) {
            System.out.println(item.toString());
        }
    }
}
