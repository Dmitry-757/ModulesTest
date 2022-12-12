package com.Dmitry_Elkin.PracticeTaskCRUD.controller;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;


//Устанавливаем параметры для MyGenericController и получаем контроллер для конкретной модели (с указанием репозитория)

public class MySkillController extends MyGenericController<Skill> {
    public MySkillController() {
        super(Skill.class, com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory.getSkillRepository());
    }
}
