package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;

//Устанавливаем параметры для MyGenericController и получаем контроллер для конкретной модели (с указанием репозитория)
public class MySpecialtyController extends MyGenericController<Specialty> {
    public MySpecialtyController() {
        super(Specialty.class, RepositoryFactory.getSpecialtyRepository());
    }
}
