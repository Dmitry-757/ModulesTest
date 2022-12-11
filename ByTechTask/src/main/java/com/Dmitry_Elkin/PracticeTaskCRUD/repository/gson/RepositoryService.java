package com.Dmitry_Elkin.PracticeTaskCRUD.repository.gson;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class RepositoryService {
    public static long generateNewId(Path lIdfile){
        long lastId = 0;
        if (Files.exists(lIdfile)) {
            try {
                lastId = Long.parseLong(Files.readString(Path.of("skill.lastId")));
                return lastId;
            } catch (IOException e) {
                System.out.println("oops! there is some io exception in GsonSkillRepositoryImpl in meth generateNewId "+e.getMessage());
            }
        } else {
            System.out.println("file "+lIdfile.getFileName()+" was not found! May be first start?");
        }
        return lastId;
    }

}
