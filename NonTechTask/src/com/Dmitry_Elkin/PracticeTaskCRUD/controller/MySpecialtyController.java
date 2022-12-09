package com.Dmitry_Elkin.PracticeTaskCRUD.controller;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;
import com.Dmitry_Elkin.PracticeTaskCRUD.repository.RepositoryFactory;


public class MySpecialtyController extends MyGenericController<Specialty> {
    public MySpecialtyController() {
        super(Specialty.class, RepositoryFactory.getSpecialtyRepository());
    }
}
