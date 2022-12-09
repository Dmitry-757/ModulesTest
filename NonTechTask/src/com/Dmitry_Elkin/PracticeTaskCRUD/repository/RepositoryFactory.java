package com.Dmitry_Elkin.PracticeTaskCRUD.repository;


import com.Dmitry_Elkin.PracticeTaskCRUD.model.Developer;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Specialty;

public class RepositoryFactory {
    private static final GenericRepository<Skill, Long> skillRepository;
    private static final GenericRepository<Specialty, Long> specialtyRepository;
    private static final GenericRepository<Developer, Long> developerRepository;

    static {
        skillRepository = new MyGenericRepositoryImpl<>(Skill.class);
        specialtyRepository = new MyGenericRepositoryImpl<>(Specialty.class);
        developerRepository = new MyGenericRepositoryImpl<>(Developer.class);
    }

    public static GenericRepository<Skill, Long> getSkillRepository() {
        return skillRepository;
    }

    public static GenericRepository<Specialty, Long> getSpecialtyRepository() {
        return specialtyRepository;
    }

    public static GenericRepository<Developer, Long> getDeveloperRepository() {
        return developerRepository;
    }
}
