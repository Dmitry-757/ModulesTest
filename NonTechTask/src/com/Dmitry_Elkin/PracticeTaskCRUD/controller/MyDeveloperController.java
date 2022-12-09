package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;

import java.util.HashSet;

import static com.Dmitry_Elkin.PracticeTaskCRUD.controller.Service.*;


//Устанавливаем параметры для MyGenericController и получаем контроллер для конкретной модели (с указанием репозитория)
public class MyDeveloperController extends MyGenericController<Developer> {
    public MyDeveloperController() {
        super(Developer.class, RepositoryFactory.getDeveloperRepository());
    }

    @Override
    protected void createNewItem() {

        String firstName = getStringParamFromConsole("first name");
        String lastName = getStringParamFromConsole("second name");
        HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skill", RepositoryFactory.getSkillRepository()));
        Specialty specialty = getGenericParamFromConsole("Specialty", RepositoryFactory.getSpecialtyRepository());
        repository.addOrUpdate(new Developer(firstName, lastName, skills, specialty));
    }

    @Override
    protected void changeItem() {

        Developer item = getGenericParamFromConsole("Developer", repository);
        if (item != null) {
            System.out.println("editing item = " + item);
            String firstName = getStringParamFromConsole("first name");
            String lastName = getStringParamFromConsole("second name");
            HashSet<Skill> skills = new HashSet<>(getGenericListFromConsole("Skills", RepositoryFactory.getSkillRepository()));
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

    @Override
    protected void printItems(Status status) {
        Service.printItems(status, repository);
    }
}
