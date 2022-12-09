package com.Dmitry_Elkin.PracticeTaskCRUD.repository;

import com.Dmitry_Elkin.PracticeTaskCRUD.model.Skill;
import com.Dmitry_Elkin.PracticeTaskCRUD.model.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GsonSkillRepositoryImpl implements SkillRepository {
//    private static final String fileName = "skills.json";
//    private static final String tmpFileName = "skills.tmp";
//    private static final Path file = Paths.get(fileName);
//    private static final Path tmpFile = Path.of(tmpFileName);

    final Class<Skill> typeParameterClass = Skill.class;
    private final String fileName;
    private final String tmpFileName;
    private final Path file;
    private final Path tmpFile;
    private final Path lastIdfile;

    private final Gson gson = new GsonBuilder()
//            .setPrettyPrinting() //formats json-file to well done form
            .create();

    public GsonSkillRepositoryImpl() {

        this.fileName = typeParameterClass.getSimpleName().toLowerCase() + ".json";
        this.tmpFileName = typeParameterClass.getSimpleName().toLowerCase() + ".tmp";
        this.file = Paths.get(fileName);
        this.tmpFile = Path.of(tmpFileName);
        this.lastIdfile = Path.of(typeParameterClass.getSimpleName().toLowerCase() + ".lastId");
    }


    @Override
    public List<Skill> getAll(Status status) {
        List<Skill> itemList = new LinkedList<>();
        try {
            if (!Files.exists(file)){
                System.out.println("file "+fileName+" not exist!");
                return itemList;
            }
            List<String> lines = Files.readAllLines(file);
            for (String jsonStr : lines) {
                Skill item = new Gson().fromJson(jsonStr, Skill.class);
                if (status == null) {
                    itemList.add(item);
                } else if (item.getStatus() == status) {
                    itemList.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("some io exception in module GsonSkillRepositoryImpl in meth getAll: "+e.getMessage());
            //throw new RuntimeException(e);
        }
        return itemList;
    }

    //чтоб не переписывать код, где вызывается метод без параметров
    @Override
    public List<Skill> getAll() {
        return getAll(null);
    }



    @Override
    public Skill getById(Long id) {
        if (!Files.exists(file)) {
            System.out.println("file "+fileName+" not exist!");
            return null;
        }
        String jsonStr;
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                jsonStr = sc.nextLine();
                Skill skill = new Gson().fromJson(jsonStr, Skill.class);
                if (skill.getId() == id) {
                    return skill;
                }
            }
        } catch (IOException e) {
            System.out.println("some io exception in module GsonSkillRepositoryImpl in meth getById: "+e.getMessage());

        }

        return null;
    }

    @Override
    public void addOrUpdate(Skill item) {
        //*** add ***
        if (item.getId() <= 0) {
            item.setNewId();
            add(item);
        } else {
            //*** update ***
            update(item);
        }
    }


    public void add(Skill item) {
        try {
            if (Files.exists(file)) {
                Files.write(file, List.of(gson.toJson(item)), StandardOpenOption.APPEND);
            } else {
                Files.write(file, List.of(gson.toJson(item)), StandardOpenOption.CREATE);
            }
            //записываем lastId
            long lastId = Skill.getLastId();
            Files.writeString(lastIdfile, "" + lastId, Charset.defaultCharset(), StandardOpenOption.CREATE);

        } catch (IOException e) {
            System.out.println("some io exception in module GsonSkillRepositoryImpl in meth add: "+e.getMessage());
        }
    }

    public void update(Skill item) {
        if (!Files.exists(file)) {
            System.out.println("file "+fileName+" not exist!");
            return;
        }

        //построчно переписываем файл, изменяя только строку содержащую отредактированный объект
        try (
                BufferedReader in = new BufferedReader(new FileReader(fileName));
                BufferedWriter out = new BufferedWriter(new FileWriter(tmpFileName))
        ) {
            String jsonStr;
            Skill skill;
            while ((jsonStr = in.readLine()) != null) {
                skill = new Gson().fromJson(jsonStr, Skill.class);

                if (skill.getId() == item.getId()) {
                    jsonStr = gson.toJson(item);
                }

                out.write(jsonStr);
                out.newLine();
            }
        } catch (FileNotFoundException e) {
            System.out.println("some FileNotFoundException exception in module GsonSkillRepositoryImpl in meth update: "+e.getMessage());

        } catch (IOException e) {
            System.out.println("some IOException exception in module GsonSkillRepositoryImpl in meth update: "+e.getMessage());
        }
        try {
            Files.move(tmpFile, file, REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("some IOException exception in module GsonSkillRepositoryImpl in meth update: "+e.getMessage());
        }

    }

    @Override
    public void delete(Skill item) {
        item.setDeleted();
        update(item);
    }

    public void unDelete(Skill item) {
        item.setUnDeleted();
        update(item);
    }

}
